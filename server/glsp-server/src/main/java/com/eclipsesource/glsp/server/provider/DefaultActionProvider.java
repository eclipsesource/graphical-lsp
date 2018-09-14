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
package com.eclipsesource.glsp.server.provider;

import java.util.HashSet;
import java.util.Set;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.CenterAction;
import com.eclipsesource.glsp.api.action.kind.ChangeBoundsAction;
import com.eclipsesource.glsp.api.action.kind.CollapseExpandAction;
import com.eclipsesource.glsp.api.action.kind.CollapseExpandAllAction;
import com.eclipsesource.glsp.api.action.kind.ComputedBoundsAction;
import com.eclipsesource.glsp.api.action.kind.CreateConnectionOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.action.kind.DeleteElementOperationAction;
import com.eclipsesource.glsp.api.action.kind.ExportSVGAction;
import com.eclipsesource.glsp.api.action.kind.FitToScreenAction;
import com.eclipsesource.glsp.api.action.kind.MoveOperationAction;
import com.eclipsesource.glsp.api.action.kind.OpenAction;
import com.eclipsesource.glsp.api.action.kind.RequestBoundsAction;
import com.eclipsesource.glsp.api.action.kind.RequestBoundsChangeHintsAction;
import com.eclipsesource.glsp.api.action.kind.RequestExportSvgAction;
import com.eclipsesource.glsp.api.action.kind.RequestLayersAction;
import com.eclipsesource.glsp.api.action.kind.RequestModelAction;
import com.eclipsesource.glsp.api.action.kind.RequestMoveHintsAction;
import com.eclipsesource.glsp.api.action.kind.RequestOperationsAction;
import com.eclipsesource.glsp.api.action.kind.RequestPopupModelAction;
import com.eclipsesource.glsp.api.action.kind.SaveModelAction;
import com.eclipsesource.glsp.api.action.kind.SelectAction;
import com.eclipsesource.glsp.api.action.kind.SelectAllAction;
import com.eclipsesource.glsp.api.action.kind.ServerStatusAction;
import com.eclipsesource.glsp.api.action.kind.SetBoundsAction;
import com.eclipsesource.glsp.api.action.kind.SetLayersAction;
import com.eclipsesource.glsp.api.action.kind.SetModelAction;
import com.eclipsesource.glsp.api.action.kind.SetMoveHintsAction;
import com.eclipsesource.glsp.api.action.kind.SetOperationsAction;
import com.eclipsesource.glsp.api.action.kind.SetPopupModelAction;
import com.eclipsesource.glsp.api.action.kind.ToogleLayerAction;
import com.eclipsesource.glsp.api.action.kind.UpdateModelAction;
import com.eclipsesource.glsp.api.provider.ActionProvider;

public class DefaultActionProvider implements ActionProvider {
	Set<Action> defaultActions;

	public DefaultActionProvider() {
		defaultActions = new HashSet<>();
		addDefaultActions();
	}

	private void addDefaultActions() {
		defaultActions.add(new CenterAction());
		defaultActions.add(new ChangeBoundsAction());
		defaultActions.add(new CollapseExpandAction());
		defaultActions.add(new CollapseExpandAllAction());
		defaultActions.add(new ComputedBoundsAction());
		defaultActions.add(new CreateConnectionOperationAction());
		defaultActions.add(new CreateNodeOperationAction());
		defaultActions.add(new DeleteElementOperationAction());
		defaultActions.add(new ExportSVGAction());
		defaultActions.add(new FitToScreenAction());
		defaultActions.add(new MoveOperationAction());
		defaultActions.add(new OpenAction());
		defaultActions.add(new RequestBoundsAction());
		defaultActions.add(new RequestBoundsChangeHintsAction());
		defaultActions.add(new RequestExportSvgAction());
		defaultActions.add(new RequestLayersAction());
		defaultActions.add(new RequestModelAction());
		defaultActions.add(new RequestMoveHintsAction());
		defaultActions.add(new RequestOperationsAction());
		defaultActions.add(new RequestPopupModelAction());
		defaultActions.add(new SaveModelAction());
		defaultActions.add(new SelectAction());
		defaultActions.add(new SelectAllAction());
		defaultActions.add(new ServerStatusAction());
		defaultActions.add(new SetBoundsAction());
		defaultActions.add(new SetLayersAction());
		defaultActions.add(new SetModelAction());
		defaultActions.add(new SetMoveHintsAction());
		defaultActions.add(new SetOperationsAction());
		defaultActions.add(new SetPopupModelAction());
		defaultActions.add(new ToogleLayerAction());
		defaultActions.add(new UpdateModelAction());

	}

	@Override
	public Set<Action> getActions() {
		return defaultActions;
	}

}
