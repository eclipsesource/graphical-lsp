package com.eclipsesource.glsp.server.model;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.model.ModelStateProvider;
import com.google.inject.Singleton;

@Singleton
public class DefaultModelStateProvider implements ModelStateProvider {
	private Map<String, GraphicalModelState> clientModelStates;

	public DefaultModelStateProvider() {
		clientModelStates = new ConcurrentHashMap<>();
	}

	@Override
	public Optional<GraphicalModelState> getModelState(String clientId) {
		return Optional.ofNullable(clientModelStates.get(clientId));
	}

	@Override
	public GraphicalModelState create(String clientId) {
		GraphicalModelState modelState = new ModelStateImpl();
		clientModelStates.put(clientId, modelState);
		return modelState;
	}

	@Override
	public void remove(String clientId) {
		clientModelStates.remove(clientId);

	}
}
