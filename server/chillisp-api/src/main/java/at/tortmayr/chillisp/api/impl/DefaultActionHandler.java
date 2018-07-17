/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.chillisp.api.impl;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import at.tortmayr.chillisp.api.IActionHandler;
import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IGraphicalModelExpansionListener;
import at.tortmayr.chillisp.api.IGraphicalModelSelectionListener;
import at.tortmayr.chillisp.api.IGraphicalModelState;
import at.tortmayr.chillisp.api.IModelElementOpenListener;
import at.tortmayr.chillisp.api.IModelFactory;
import at.tortmayr.chillisp.api.IPopupModelFactory;
import at.tortmayr.chillisp.api.IToolConfiguration;
import at.tortmayr.chillisp.api.LayoutUtil;
import at.tortmayr.chillisp.api.actions.CenterAction;
import at.tortmayr.chillisp.api.actions.ChangeBoundsAction;
import at.tortmayr.chillisp.api.actions.CollapseExpandAction;
import at.tortmayr.chillisp.api.actions.CollapseExpandAllAction;
import at.tortmayr.chillisp.api.actions.ComputedBoundsAction;
import at.tortmayr.chillisp.api.actions.ExecuteNodeCreationToolAction;
import at.tortmayr.chillisp.api.actions.ExecuteToolAction;
import at.tortmayr.chillisp.api.actions.FitToScreenAction;
import at.tortmayr.chillisp.api.actions.MoveAction;
import at.tortmayr.chillisp.api.actions.OpenAction;
import at.tortmayr.chillisp.api.actions.RequestBoundsAction;
import at.tortmayr.chillisp.api.actions.RequestBoundsChangeHintsAction;
import at.tortmayr.chillisp.api.actions.RequestExportSvgAction;
import at.tortmayr.chillisp.api.actions.RequestLayersAction;
import at.tortmayr.chillisp.api.actions.RequestModelAction;
import at.tortmayr.chillisp.api.actions.RequestMoveHintsAction;
import at.tortmayr.chillisp.api.actions.RequestPopupModelAction;
import at.tortmayr.chillisp.api.actions.RequestToolsAction;
import at.tortmayr.chillisp.api.actions.SaveModelAction;
import at.tortmayr.chillisp.api.actions.SelectAction;
import at.tortmayr.chillisp.api.actions.SelectAllAction;
import at.tortmayr.chillisp.api.actions.SetBoundsAction;
import at.tortmayr.chillisp.api.actions.SetModelAction;
import at.tortmayr.chillisp.api.actions.SetPopupModelAction;
import at.tortmayr.chillisp.api.actions.SetToolsAction;
import at.tortmayr.chillisp.api.actions.ToogleLayerAction;
import at.tortmayr.chillisp.api.actions.UpdateModelAction;
import at.tortmayr.chillisp.api.type.Tool;
import io.typefox.sprotty.api.ILayoutEngine;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelIndex;
import io.typefox.sprotty.api.SModelRoot;

public class DefaultActionHandler implements IActionHandler {

	private IGraphicalLanguageServer server;

	@Inject
	private IGraphicalModelState modelState;
	@Inject
	private IGraphicalModelSelectionListener selectionListener;
	@Inject
	private IGraphicalModelExpansionListener expansionListener;
	@Inject
	private IModelElementOpenListener modelElementOpenListener;
	@Inject
	private IToolConfiguration toolConfiguration;
	@Inject
	private IModelFactory modelFactory;
	@Inject
	private IPopupModelFactory popupModelFactory;
	@Inject
	private ILayoutEngine layoutEngine;
	private Object modelLock = new Object();
	private int revision = 0;
	private String lastSubmittedModelType;

	public DefaultActionHandler() {
	}

	protected void submitModel(SModelRoot newRoot, boolean update) {

		if (getModelState().needsClientLayout()) {
			server.dispatch(new RequestBoundsAction(newRoot));
		} else {
			doSubmitModel(newRoot, update);
		}
		getModelState().setCurrentModel(newRoot);
	}

	private void doSubmitModel(SModelRoot newRoot, boolean update) {
		
		if (layoutEngine != null) {
			layoutEngine.layout(newRoot);
		}

		synchronized (modelLock) {
			if (newRoot.getRevision() == revision) {
				String modelType = newRoot.getType();
				if (update && modelType != null && modelType.equals(lastSubmittedModelType)) {
					server.dispatch(new UpdateModelAction(newRoot, null, true));
				} else {
					server.dispatch(new SetModelAction(newRoot));
				}
				lastSubmittedModelType = modelType;
			}
		}
	}

	@Override
	public void handle(RequestModelAction action) {
		Map<String, String> options = action.getOptions();
		if (options != null) {

			String needsClientLayout = options.get("needsClientLayout");
			if (needsClientLayout != null && !needsClientLayout.isEmpty()) {
				getModelState().setNeedsClientLayout(Boolean.parseBoolean(needsClientLayout));
			}
			SModelRoot model = modelFactory.loadModel(server, action);
			getModelState().setCurrentModel(model);
			getModelState().setOptions(options);
			if (model != null) {
				submitModel(model, false);
			}
		}

	}

	@Override
	public void handle(CenterAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");
	}

	@Override
	public void handle(CollapseExpandAction action) {
		Set<String> expandedElements = getModelState().getExpandedElements();
		if (action.getCollapseIds() != null) {
			expandedElements.removeAll(Arrays.asList(action.getCollapseIds()));
		}
		if (action.getExpandIds() != null) {
			expandedElements.addAll(Arrays.asList(action.getExpandIds()));
		}

		if (expansionListener != null) {
			expansionListener.expansionChanged(action, server);
		}

	}

	@Override
	public void handle(CollapseExpandAllAction action) {
		Set<String> expandedElements = getModelState().getExpandedElements();
		expandedElements.clear();
		if (action.isExpand()) {
			new SModelIndex(getModelState().getCurrentModel()).allIds().forEach(id -> expandedElements.add(id));
		}
		if (expansionListener != null) {
			expansionListener.expansionChanged(action, server);
		}

	}

	@Override
	public void handle(ComputedBoundsAction action) {
		synchronized (modelLock) {
			SModelRoot model = getModelState().getCurrentModel();
			if (model != null && model.getRevision() == action.getRevision()) {
				LayoutUtil.applyBounds(model, action);
				doSubmitModel(model, true);
			}
		}

	}

	@Override
	public void handle(ExecuteNodeCreationToolAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");

	}

	@Override
	public void handle(ExecuteToolAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");

	}

	@Override
	public void handle(RequestBoundsChangeHintsAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");

	}

	@Override
	public void handle(ChangeBoundsAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");

	}

	@Override
	public void handle(RequestMoveHintsAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");
	}

	@Override
	public void handle(MoveAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");
	}

	@Override
	public void handle(FitToScreenAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");
	}

	@Override
	public void handle(OpenAction action) {
		if (modelElementOpenListener != null) {
			modelElementOpenListener.elementOpened(action, server);
		}
	}

	@Override
	public void handle(RequestExportSvgAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");
	}

	@Override
	public void handle(RequestLayersAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");
	}

	@Override
	public void handle(RequestPopupModelAction action) {
		SModelRoot model = getModelState().getCurrentModel();
		SModelElement element = SModelIndex.find(model, action.getElementId());
		if (popupModelFactory != null) {
			SModelRoot popupModel = popupModelFactory.createPopuModel(element, action, server);
			if (popupModel != null) {
				server.dispatch(new SetPopupModelAction(popupModel, action.getBounds()));
			}
		}
	}

	@Override
	public void handle(RequestToolsAction action) {
		if (toolConfiguration != null) {
			Tool[] tools = toolConfiguration.getTools(action, server);
			if (tools != null) {
				server.dispatch(new SetToolsAction(tools));
			}
		}
	}

	@Override
	public void handle(SelectAction action) {
		Set<String> selectedElements = getModelState().getSelectedElements();
		if (action.getDeselectedElementsIDs() != null) {
			selectedElements.removeAll(Arrays.asList(action.getDeselectedElementsIDs()));
		}
		if (action.getSelectedElementsIDs() != null) {
			selectedElements.addAll(Arrays.asList(action.getSelectedElementsIDs()));
		}
		if (selectionListener != null) {
			selectionListener.selectionChanged(action, server);
		}
	}

	@Override
	public void handle(SelectAllAction action) {
		Set<String> selectedElements = getModelState().getSelectedElements();
		if (action.isSelect()) {
			new SModelIndex(getModelState().getCurrentModel()).allIds().forEach(id -> selectedElements.add(id));
		} else
			selectedElements.clear();
		if (selectionListener != null) {
			selectionListener.selectionChanged(action, server);
		}
	}

	@Override
	public void handle(ToogleLayerAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");
	}

	@Override
	public void handle(SetBoundsAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");
	}

	@Override
	public void setLanguageServer(IGraphicalLanguageServer server) {
		this.server = server;

	}

	@Override
	public IGraphicalModelState getModelState() {
		assert (modelState != null);
		return modelState;
	}

	@Override
	public void handle(SaveModelAction action) {
		if (action !=null && modelFactory instanceof FileBasedModelFactory) {
			((FileBasedModelFactory)modelFactory).saveModel(getModelState().getCurrentModel());
		}	
	}
}
