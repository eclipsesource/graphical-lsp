package com.eclipsesource.glsp.ecore;

import java.util.Optional;

import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.AbstractActionHandler;
import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.model.ModelState;
import com.eclipsesource.glsp.ecore.model.EcoreGraph;
import com.eclipsesource.glsp.server.actionhandler.ComputedBoundsActionHandler;

public class EcoreComputedBoundsActionHandler extends ComputedBoundsActionHandler {

	@Override
	public Optional<Action> execute(Action action, ModelState modelState) {
		SModelRoot root= modelState.getCurrentModel();
		if (root instanceof EcoreGraph) {
			((EcoreGraph)root).setNeedsInitialLayout(false);
		}
		return super.execute(action, modelState);
	}

}
