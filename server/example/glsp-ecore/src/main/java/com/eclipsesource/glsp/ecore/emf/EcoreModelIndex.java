package com.eclipsesource.glsp.ecore.emf;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;

import com.eclipsesource.glsp.ecore.util.SModelIdUtils;

public class EcoreModelIndex {

	private final Map<String, EObject> idToElement;

	public EcoreModelIndex(EObject root) {
		idToElement = new HashMap<>();
		root.eAllContents().forEachRemaining(e -> {
			Optional<String> id = SModelIdUtils.toSModelId(e);
			if (id.isPresent()) {
				addToIndex(id.get(), e);
			}
		});
	}

	public Optional<EObject> get(String smodelId) {
		return Optional.ofNullable(idToElement.get(smodelId));
	}

	@SuppressWarnings("unchecked")
	public <T extends EObject> Optional<T> get(String smodelId, Class<T> clazz) {
		Optional<EObject> value = get(smodelId);
		if (value.isPresent()) {
			if (clazz.isInstance(value.get())) {
				return Optional.of((T) value.get());
			}
		}

		return Optional.empty();
	}

	public void addToIndex(EObject object) {
		SModelIdUtils.toSModelId(object).ifPresent(id->idToElement.put(id,object));
	}
	public void addToIndex(String id, EObject object) {
		idToElement.put(id, object);
	}

}
