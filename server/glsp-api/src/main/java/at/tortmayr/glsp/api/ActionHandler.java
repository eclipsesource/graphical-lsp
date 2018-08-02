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
package at.tortmayr.glsp.api;

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
import at.tortmayr.glsp.api.action.ToogleLayerAction;

public interface ActionHandler {
	void setGraphicalLanguageClient(GraphicalLanguageClient client);

	void setClientId(String clientId);

	GraphicalModelState getModelState();

	void handle(RequestModelAction action);

	void handle(CenterAction action);

	void handle(CollapseExpandAction action);

	void handle(CollapseExpandAllAction action);

	void handle(ComputedBoundsAction action);

	void handle(ExecuteNodeCreationToolAction action);

	void handle(ExecuteToolAction action);

	void handle(RequestBoundsChangeHintsAction action);

	void handle(ChangeBoundsAction action);

	void handle(RequestMoveHintsAction action);

	void handle(MoveAction action);

	void handle(FitToScreenAction action);

	void handle(OpenAction action);

	void handle(RequestExportSvgAction action);

	void handle(RequestLayersAction action);

	void handle(RequestPopupModelAction action);

	void handle(SetBoundsAction action);

	void handle(RequestToolsAction action);

	void handle(SelectAction action);

	void handle(SelectAllAction action);

	void handle(ToogleLayerAction action);

	void handle(SaveModelAction action);

}
