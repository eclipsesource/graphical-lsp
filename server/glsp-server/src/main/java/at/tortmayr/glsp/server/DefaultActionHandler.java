/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
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
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import at.tortmayr.glsp.api.action.Action;
import at.tortmayr.glsp.api.action.ActionHandler;
import at.tortmayr.glsp.api.action.ActionMessage;
import at.tortmayr.glsp.api.action.ExecuteOperationAction;
import at.tortmayr.glsp.api.action.kind.CenterAction;
import at.tortmayr.glsp.api.action.kind.ChangeBoundsAction;
import at.tortmayr.glsp.api.action.kind.CollapseExpandAction;
import at.tortmayr.glsp.api.action.kind.CollapseExpandAllAction;
import at.tortmayr.glsp.api.action.kind.ComputedBoundsAction;
import at.tortmayr.glsp.api.action.kind.CreateConnectionOperationAction;
import at.tortmayr.glsp.api.action.kind.CreateNodeOperationAction;
import at.tortmayr.glsp.api.action.kind.DeleteElementOperationAction;
import at.tortmayr.glsp.api.action.kind.FitToScreenAction;
import at.tortmayr.glsp.api.action.kind.MoveOperationAction;
import at.tortmayr.glsp.api.action.kind.OpenAction;
import at.tortmayr.glsp.api.action.kind.RequestBoundsAction;
import at.tortmayr.glsp.api.action.kind.RequestBoundsChangeHintsAction;
import at.tortmayr.glsp.api.action.kind.RequestExportSvgAction;
import at.tortmayr.glsp.api.action.kind.RequestLayersAction;
import at.tortmayr.glsp.api.action.kind.RequestModelAction;
import at.tortmayr.glsp.api.action.kind.RequestMoveHintsAction;
import at.tortmayr.glsp.api.action.kind.RequestOperationsAction;
import at.tortmayr.glsp.api.action.kind.RequestPopupModelAction;
import at.tortmayr.glsp.api.action.kind.SaveModelAction;
import at.tortmayr.glsp.api.action.kind.SelectAction;
import at.tortmayr.glsp.api.action.kind.SelectAllAction;
import at.tortmayr.glsp.api.action.kind.SetBoundsAction;
import at.tortmayr.glsp.api.action.kind.SetModelAction;
import at.tortmayr.glsp.api.action.kind.SetOperationsAction;
import at.tortmayr.glsp.api.action.kind.SetPopupModelAction;
import at.tortmayr.glsp.api.action.kind.ToogleLayerAction;
import at.tortmayr.glsp.api.action.kind.UpdateModelAction;
import at.tortmayr.glsp.api.factory.FileBasedModelFactory;
import at.tortmayr.glsp.api.factory.GraphicalModelState;
import at.tortmayr.glsp.api.factory.ModelFactory;
import at.tortmayr.glsp.api.factory.PopupModelFactory;
import at.tortmayr.glsp.api.jsonrpc.GraphicalLanguageClient;
import at.tortmayr.glsp.api.listener.GraphicalModelExpansionListener;
import at.tortmayr.glsp.api.listener.GraphicalModelSelectionListener;
import at.tortmayr.glsp.api.listener.ModelElementOpenListener;
import at.tortmayr.glsp.api.operations.Operation;
import at.tortmayr.glsp.api.operations.OperationConfiguration;
import at.tortmayr.glsp.api.operations.OperationHandler;
import at.tortmayr.glsp.api.operations.OperationHandlerProvider;
import at.tortmayr.glsp.api.utils.InitalRequestOptions;
import at.tortmayr.glsp.api.utils.LayoutUtil;
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
	private OperationConfiguration operationConfiguration;
	@Inject
	private OperationHandlerProvider operationHandlers;

	@Inject
	private ModelFactory modelFactory;
	@Inject
	private PopupModelFactory popupModelFactory;
	@Inject
	private ILayoutEngine layoutEngine;

	private GraphicalLanguageClient client;
	private String clientId;

	private Object modelLock = new Object();
	private String lastSubmittedModelType;
	private int revision = 0;

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
	public void setGraphicalLanguageClient(GraphicalLanguageClient client) {
		this.client = client;
	}

	@Override
	public void setClientId(String clientId) {
		this.clientId = clientId;

	}

	@Override
	public GraphicalModelState getModelState() {
		assert (modelState != null);
		return modelState;
	}

	@Override
	public void handle(RequestModelAction action) {
		Map<String, String> options = action.getOptions();
		if (options == null) {
			return;
		}

		String needsClientLayout = options.get(InitalRequestOptions.NEEDS_CLIENT_LAYOUT);
		if (needsClientLayout != null && !needsClientLayout.isEmpty()) {
			getModelState().setNeedsClientLayout(Boolean.parseBoolean(needsClientLayout));
		}

		SModelRoot model = modelFactory.loadModel(action);
		getModelState().setCurrentModel(model);
		getModelState().setOptions(options);
		if (model != null) {
			lastSubmittedModelType = model.getType();
			submitModel(model, false);
		} else {
			lastSubmittedModelType = null;
		}
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
	public void handle(OpenAction action) {
		if (modelElementOpenListener != null) {
			modelElementOpenListener.elementOpened(action);
		}
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
	public void handle(RequestOperationsAction action) {
		Optional<Operation[]> operations = Optional.ofNullable(operationConfiguration)
				.map(config -> config.getOperations(action));
		sendResponse(new SetOperationsAction(operations.orElse(new Operation[0])));
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
	public void handle(SaveModelAction action) {
		if (action != null && modelFactory instanceof FileBasedModelFactory) {
			((FileBasedModelFactory) modelFactory).saveModel(getModelState().getCurrentModel());
		}
	}
	
	@Override
	public void handle(CreateNodeOperationAction action) {
		doHandle(action);
	}
	
	@Override
	public void handle(CreateConnectionOperationAction action) {
		doHandle(action);
	}

	@Override
	public void handle(DeleteElementOperationAction action) {
		doHandle(action);
	}

	@Override
	public void handle(MoveOperationAction action) {
		doHandle(action);
	}

	public void doHandle(ExecuteOperationAction action) {
		Optional<OperationHandler> handler = Optional.ofNullable(operationHandlers)
				.flatMap(handlers -> handlers.getOperationHandler(action));
		if (handler.isPresent()) {
			Optional<SModelRoot> modelRoot = handler.get().execute(action, getModelState());
			lastSubmittedModelType = modelRoot.map(SModelRoot::getType).orElse(null);
			modelRoot.ifPresent(r -> submitModel(r, false));
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
	public void handle(FitToScreenAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");
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
	public void handle(CenterAction action) {
		throw new UnsupportedOperationException("Method not yet implemented");
	}

}
