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
package at.tortmayr.glsp.server;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import at.tortmayr.glsp.api.ActionHandler;
import at.tortmayr.glsp.api.ActionMessage;
import at.tortmayr.glsp.api.FileBasedModelFactory;
import at.tortmayr.glsp.api.GraphicalLanguageClient;
import at.tortmayr.glsp.api.GraphicalModelExpansionListener;
import at.tortmayr.glsp.api.GraphicalModelSelectionListener;
import at.tortmayr.glsp.api.GraphicalModelState;
import at.tortmayr.glsp.api.LayoutUtil;
import at.tortmayr.glsp.api.ModelElementOpenListener;
import at.tortmayr.glsp.api.ModelFactory;
import at.tortmayr.glsp.api.Options;
import at.tortmayr.glsp.api.PopupModelFactory;
import at.tortmayr.glsp.api.ToolConfiguration;
import at.tortmayr.glsp.api.action.Action;
import at.tortmayr.glsp.api.action.CenterAction;
import at.tortmayr.glsp.api.action.ChangeBoundsAction;
import at.tortmayr.glsp.api.action.CollapseExpandAction;
import at.tortmayr.glsp.api.action.CollapseExpandAllAction;
import at.tortmayr.glsp.api.action.ComputedBoundsAction;
import at.tortmayr.glsp.api.action.ExecuteNodeCreationToolAction;
import at.tortmayr.glsp.api.action.ExecuteToolAction;
import at.tortmayr.glsp.api.action.FitToScreenAction;
import at.tortmayr.glsp.api.action.MoveAction;
import at.tortmayr.glsp.api.action.OpenAction;
import at.tortmayr.glsp.api.action.RequestBoundsAction;
import at.tortmayr.glsp.api.action.RequestBoundsChangeHintsAction;
import at.tortmayr.glsp.api.action.RequestExportSvgAction;
import at.tortmayr.glsp.api.action.RequestLayersAction;
import at.tortmayr.glsp.api.action.RequestModelAction;
import at.tortmayr.glsp.api.action.RequestMoveHintsAction;
import at.tortmayr.glsp.api.action.RequestPopupModelAction;
import at.tortmayr.glsp.api.action.RequestToolsAction;
import at.tortmayr.glsp.api.action.SaveModelAction;
import at.tortmayr.glsp.api.action.SelectAction;
import at.tortmayr.glsp.api.action.SelectAllAction;
import at.tortmayr.glsp.api.action.SetBoundsAction;
import at.tortmayr.glsp.api.action.SetModelAction;
import at.tortmayr.glsp.api.action.SetPopupModelAction;
import at.tortmayr.glsp.api.action.SetToolsAction;
import at.tortmayr.glsp.api.action.ToogleLayerAction;
import at.tortmayr.glsp.api.action.UpdateModelAction;
import at.tortmayr.glsp.api.utils.Tool;
import io.typefox.sprotty.api.ILayoutEngine;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelIndex;
import io.typefox.sprotty.api.SModelRoot;

public class DefaultActionHandler implements ActionHandler {

	@Inject
	private GraphicalModelState modelState;
	@Inject
	private GraphicalModelSelectionListener selectionListener;
	@Inject
	private GraphicalModelExpansionListener expansionListener;
	@Inject
	private ModelElementOpenListener modelElementOpenListener;
	@Inject
	private ToolConfiguration toolConfiguration;
	@Inject
	private ModelFactory modelFactory;
	@Inject
	private PopupModelFactory popupModelFactory;
	@Inject
	private ILayoutEngine layoutEngine;
	private Object modelLock = new Object();
	private int revision = 0;
	private String lastSubmittedModelType;

	private GraphicalLanguageClient client;
	private String clientId;

	public DefaultActionHandler() {
	}

	private void sendResponse(Action action) {
		if (client != null && clientId != null) {
			ActionMessage message = new ActionMessage(clientId, action);
			client.process(message);
		}

	}

	protected void submitModel(SModelRoot newRoot, boolean update) {

		if (getModelState().needsClientLayout()) {
			sendResponse(new RequestBoundsAction(newRoot));
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
					sendResponse(new UpdateModelAction(newRoot, null, true));
				} else {
					sendResponse(new SetModelAction(newRoot));
				}
				lastSubmittedModelType = modelType;

			}
		}
	}

	@Override
	public void handle(RequestModelAction action) {
		Map<String, String> options = action.getOptions();
		if (options != null) {

			String needsClientLayout = options.get(Options.NEEDS_CLIENT_LAYOUT);
			if (needsClientLayout != null && !needsClientLayout.isEmpty()) {
				getModelState().setNeedsClientLayout(Boolean.parseBoolean(needsClientLayout));
			}
			SModelRoot model = modelFactory.loadModel(action);
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
			expansionListener.expansionChanged(action);
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
			expansionListener.expansionChanged(action);
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
			modelElementOpenListener.elementOpened(action);
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
			SModelRoot popupModel = popupModelFactory.createPopuModel(element, action);
			if (popupModel != null) {
				sendResponse(new SetPopupModelAction(popupModel, action.getBounds()));
			}
		}
	}

	@Override
	public void handle(RequestToolsAction action) {
		if (toolConfiguration != null) {
			Tool[] tools = toolConfiguration.getTools(action);
			if (tools != null) {
				sendResponse(new SetToolsAction(tools));
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
			selectionListener.selectionChanged(action);
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
			selectionListener.selectionChanged(action);
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
	public GraphicalModelState getModelState() {
		assert (modelState != null);
		return modelState;
	}

	@Override
	public void handle(SaveModelAction action) {
		if (action != null && modelFactory instanceof FileBasedModelFactory) {
			((FileBasedModelFactory) modelFactory).saveModel(getModelState().getCurrentModel());
		}
	}

	@Override
	public void setGraphicalLanguageClient(GraphicalLanguageClient client) {
		this.client = client;
	}

	@Override
	public void setClientId(String clientId) {
		this.clientId = clientId;

	}
}
