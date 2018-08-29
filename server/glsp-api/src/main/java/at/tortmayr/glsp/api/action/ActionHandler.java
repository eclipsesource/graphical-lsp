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
package at.tortmayr.glsp.api.action;

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
import at.tortmayr.glsp.api.action.kind.ToogleLayerAction;
import at.tortmayr.glsp.api.factory.GraphicalModelState;
import at.tortmayr.glsp.api.jsonrpc.GraphicalLanguageClient;

public interface ActionHandler {

	void setGraphicalLanguageClient(GraphicalLanguageClient client);

	void setClientId(String clientId);

	GraphicalModelState getModelState();

	void handle(RequestModelAction action);

	void handle(RequestOperationsAction action);

	void handle(ComputedBoundsAction action);

	void handle(CreateConnectionOperationAction action);

	void handle(CreateNodeOperationAction action);

	void handle(DeleteElementOperationAction action);

	void handle(MoveOperationAction action);

	void handle(RequestBoundsChangeHintsAction action);

	void handle(CenterAction action);

	void handle(CollapseExpandAction action);

	void handle(CollapseExpandAllAction action);

	void handle(ChangeBoundsAction action);

	void handle(RequestMoveHintsAction action);

	void handle(FitToScreenAction action);

	void handle(OpenAction action);

	void handle(RequestExportSvgAction action);

	void handle(RequestLayersAction action);

	void handle(RequestPopupModelAction action);

	void handle(SetBoundsAction action);

	void handle(SelectAction action);

	void handle(SelectAllAction action);

	void handle(ToogleLayerAction action);

	void handle(SaveModelAction action);

}
