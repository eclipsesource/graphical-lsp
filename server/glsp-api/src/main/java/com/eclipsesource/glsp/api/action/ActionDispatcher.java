package com.eclipsesource.glsp.api.action;

import java.util.Optional;

public interface ActionDispatcher {

	Optional<Action> dispatch(String clientId, Action action);

	public static class NullImpl implements ActionDispatcher {

		@Override
		public Optional<Action> dispatch(String clientId, Action action) {
			return Optional.empty();
		}
	}
}
