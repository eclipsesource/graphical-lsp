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
package at.tortmayr.glsp.api.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.typefox.sprotty.api.SModelElement;

public class SModelIndex {
	private final Map<String, SModelElement> idToElement;
	private final Map<String, Set<SModelElement>> typeToElements;
	private final Map<SModelElement, SModelElement> childToParent;

	/**
	 * Build an index from the given parent element. All content of the element is
	 * included recursively.
	 */
	public SModelIndex(SModelElement parent) {
		idToElement = new HashMap<>();
		typeToElements = new HashMap<>();
		childToParent = new HashMap<>();
		addToIndex(parent);
	}

	private void indexType(SModelElement element) {
		String type = element.getType();
		Set<SModelElement> elements = typeToElements.get(type);
		if (elements == null) {
			elements = new HashSet<>();
			typeToElements.put(type, elements);
		}
		elements.add(element);
	}

	private void indexId(SModelElement element) {
		idToElement.put(element.getId(), element);
	}
	
	private void indexChildren(SModelElement element) {
		if (element.getChildren() != null) {
			for (SModelElement child : element.getChildren()) {
				childToParent.put(child, element);
			}
		}
	}

	private SModelElement findParent(SModelElement child) {
		for (SModelElement element : idToElement.values()) {
			if (element.getChildren().contains(child)) {
				return element;
			}
		}
		return null;
	}
	
	public void addToIndex(SModelElement element, SModelElement parent) {
		addToIndex(element);
		childToParent.put(element, parent);
	}

	private void addToIndex(SModelElement element) {
		indexId(element);
		indexType(element);
		indexChildren(element);
		if (element.getChildren() != null) {
			for (SModelElement child : element.getChildren()) {
				addToIndex(child);
			}
		}
	}
	
	public SModelElement getParent(SModelElement element) {
		if (! childToParent.containsKey(element)) {
			childToParent.put(element, findParent(element));
		}
		return childToParent.get(element);
	}

	/**
	 * @return all IDs
	 */
	public Iterable<String> allIds() {
		return idToElement.keySet();
	}

	public SModelElement get(String elementId) {
		return idToElement.get(elementId);
	}

	public Set<SModelElement> getAllByType(String type) {
		return typeToElements.get(type);
	}

	public int getTypeCount(String type) {
		return typeToElements.computeIfAbsent(type, t -> new HashSet<>()).size();
	}

	/**
	 * Find a single model element without building an index first. If you need to
	 * find multiple elements, creating an {@link SModelIndex} instance is more
	 * effective.
	 */
	public static SModelElement find(SModelElement parent, String elementId) {
		if (elementId != null) {
			if (elementId.equals(parent.getId()))
				return parent;
			if (parent.getChildren() != null) {
				for (SModelElement child : parent.getChildren()) {
					SModelElement result = find(child, elementId);
					if (result != null)
						return result;
				}
			}
		}
		return null;
	}

}
