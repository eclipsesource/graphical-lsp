/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.api.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.sprotty.SEdge;
import org.eclipse.sprotty.SModelElement;

public class SModelIndex {
	private SModelElement parent;
	private final Map<String, SModelElement> idToElement;
	private final Map<String, Set<SModelElement>> typeToElements;
	private final Map<SModelElement, SModelElement> childToParent;
	private final Map<SModelElement, Set<SEdge>> incomingEdges;
	private final Map<SModelElement, Set<SEdge>> outgoingEdges;

	/**
	 * Build an index from the given parent element. All content of the element is
	 * included recursively.
	 */
	public SModelIndex(SModelElement parent) {
		this.parent = parent;
		idToElement = new HashMap<>();
		typeToElements = new HashMap<>();
		childToParent = new HashMap<>();
		outgoingEdges = new HashMap<>();
		incomingEdges = new HashMap<>();
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
			if (element.getChildren() != null && element.getChildren().contains(child)) {
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
		if (element instanceof SEdge) {
			indexSourceAndTarget((SEdge) element);
		}
		if (element.getChildren() != null) {
			for (SModelElement child : element.getChildren()) {
				addToIndex(child);
			}
		}
	}

	private void indexSourceAndTarget(SEdge edge) {
		String sourceId = edge.getSourceId();
		SModelElement source = get(sourceId);
		outgoingEdges.computeIfAbsent(source, s -> new HashSet<>()).add(edge);

		String targetId = edge.getTargetId();
		SModelElement target = get(targetId);
		incomingEdges.computeIfAbsent(target, t -> new HashSet<>()).add(edge);
	}

	public SModelElement getParent(SModelElement element) {
		if (!childToParent.containsKey(element)) {
			childToParent.put(element, findParent(element));
		}
		return childToParent.get(element);
	}

	/**
	 * Returns the first element of type clazz starting from the element with the
	 * given id and walking up the parent hierarchy.
	 * 
	 * @param elementId id of the element to start the search from
	 * @param clazz     class of which the found element should be an instance
	 * @return an optional with the element of type clazz or an empty optional
	 */
	public <T extends SModelElement> Optional<T> findElement(String elementId, Class<T> clazz) {
		return findElement(get(elementId), clazz);
	}

	/**
	 * Returns the first element of type clazz starting from the given element and
	 * walking up the parent hierarchy.
	 * 
	 * @param element element to start the search from
	 * @param clazz   class of which the found element should be an instance
	 * @return an optional with the element of type clazz or an empty optional
	 */
	public <T extends SModelElement> Optional<T> findElement(SModelElement element, Class<T> clazz) {
		if (element == null) {
			return Optional.empty();
		}
		if (clazz.isInstance(element)) {
			return Optional.of(clazz.cast(element));
		}
		SModelElement parent = getParent(element);
		return parent != null ? findElement(parent, clazz) : Optional.empty();
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

	public Set<SModelElement> getAll(Collection<String> elementIds) {
		return elementIds.stream().map(this::get).collect(Collectors.toSet());
	}

	public Set<SModelElement> getAllByType(String type) {
		return typeToElements.get(type);
	}

	public int getTypeCount(String type) {
		return typeToElements.computeIfAbsent(type, t -> new HashSet<>()).size();
	}

	public Collection<SEdge> getIncomingEdges(SModelElement node) {
		return incomingEdges.computeIfAbsent(node, n -> new HashSet<>());
	}

	public Collection<SEdge> getOutgoingEdges(SModelElement node) {
		return outgoingEdges.computeIfAbsent(node, n -> new HashSet<>());
	}

	public void removeFromIndex(SModelElement element) {
		idToElement.remove(element.getId());
		typeToElements.get(element.getType()).remove(element);
		childToParent.remove(element);
	}

	public <T extends SModelElement> Set<T> getAllByClass(Class<T> type) {
		return findAll(this.parent, type);
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

	public static <T extends SModelElement> Set<T> findAll(SModelElement parent, Class<T> type) {
		return getStream(parent).flatMap(SModelIndex::getStream).filter(type::isInstance).map(type::cast)
				.collect(Collectors.toSet());
	}

	private static Stream<SModelElement> getStream(SModelElement element) {
		if (element == null) {
			return Stream.empty();
		}
		if (element.getChildren() == null) {
			return Stream.of(element);
		}
		return Stream.concat(Stream.of(element), element.getChildren().stream());
	}
}
