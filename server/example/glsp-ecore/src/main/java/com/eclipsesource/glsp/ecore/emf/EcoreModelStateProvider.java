package com.eclipsesource.glsp.ecore.emf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;

import com.google.inject.Singleton;

@Singleton
public class EcoreModelStateProvider {
	private Map<String, EcoreModelState> clientModelStates;

	public EcoreModelStateProvider() {
		clientModelStates = new HashMap<String, EcoreModelState>();
	}

	public void registerModel(String clientId, EObject model) {
		clientModelStates.put(clientId, new EcoreModelState(model));
	}

	public Optional<EcoreModelState> getModelState(String clientId) {
		return Optional.ofNullable(clientModelStates.get(clientId));
	}

}
