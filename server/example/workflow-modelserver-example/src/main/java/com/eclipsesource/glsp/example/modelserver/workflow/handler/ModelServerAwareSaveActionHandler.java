package com.eclipsesource.glsp.example.modelserver.workflow.handler;

import java.io.IOException;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.SaveModelAction;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ModelServerAwareModelState;
import com.eclipsesource.glsp.server.actionhandler.AbstractActionHandler;

public class ModelServerAwareSaveActionHandler extends AbstractActionHandler {

	private static final Logger LOG = Logger.getLogger(ModelServerAwareSaveActionHandler.class);

	@Override
	public boolean handles(Action action) {
		return action instanceof SaveModelAction;
	}

	@Override
	protected Optional<Action> execute(Action action, GraphicalModelState modelState) {
		if (action instanceof SaveModelAction) {
			SaveModelAction saveAction = (SaveModelAction) action;
			if (saveAction != null) {
				saveModelState(modelState);
			}
		}
		return Optional.empty();
	}

	private void saveModelState(GraphicalModelState modelState) {
		try {
			ModelServerAwareModelState.getModelAccess(modelState).save();
		} catch (IOException e) {
			LOG.error("Error saving the model", e);
		}
	}

}
