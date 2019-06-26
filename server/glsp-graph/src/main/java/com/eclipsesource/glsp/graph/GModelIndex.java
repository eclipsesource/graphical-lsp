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
package com.eclipsesource.glsp.graph;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.eclipsesource.glsp.graph.impl.GModelIndexImpl;

public interface GModelIndex {

	public static GModelIndex get(GModelElement element) {
		EObject root = EcoreUtil.getRootContainer(element);
		GModelIndex existingIndex = (GModelIndexImpl) EcoreUtil.getExistingAdapter(root, GModelIndexImpl.class);
		return Optional.ofNullable(existingIndex).orElseGet(() -> (create(element)));
	}

	public static GModelIndex create(GModelElement element) {
		return new GModelIndexImpl(EcoreUtil.getRootContainer(element));
	}

	public static void remove(GModelElement element) {
		EObject root = EcoreUtil.getRootContainer(element);
		GModelIndexImpl existingIndex = (GModelIndexImpl) EcoreUtil.getExistingAdapter(root, GModelIndexImpl.class);
		if (existingIndex == null) {
			return;
		}
		existingIndex.unsetTarget(root);
	}

	Optional<GModelElement> get(String elementId);

	Set<GModelElement> getAll(Collection<String> elementIds);

	Collection<GEdge> getIncomingEdges(GModelElement node);

	Collection<GEdge> getOutgoingEdges(GModelElement node);

	Set<String> allIds();

	GModelElement getRoot();

	int getCounter(EClass eClass, Function<Integer, String> idProvider);

	/**
	 * Returns the first element of type clazz starting from the element with the
	 * given id and walking up the parent hierarchy.
	 * 
	 * @param elementId id of the element to start the search from
	 * @param clazz     class of which the found element should be an instance
	 * @return an optional with the element of type clazz or an empty optional
	 */
	default <T extends GModelElement> Optional<T> findElementByClass(String elementId, Class<T> clazz) {
		Optional<GModelElement> element = get(elementId);
		if (element.isPresent()) {
			return findElementByClass(element.get(), clazz);
		}
		return Optional.empty();
	}

	/**
	 * Returns the first element of type clazz starting from the given element and
	 * walking up the parent hierarchy.
	 * 
	 * @param element element to start the search from
	 * @param clazz   class of which the found element should be an instance
	 * @return an optional with the element of type clazz or an empty optional
	 */
	default public <T extends GModelElement> Optional<T> findElementByClass(GModelElement element, Class<T> clazz) {
		if (element == null) {
			return Optional.empty();
		}
		if (clazz.isInstance(element)) {
			return Optional.of(clazz.cast(element));
		}

		GModelElement parent = element.getParent();
		return parent != null ? findElementByClass(parent, clazz) : Optional.empty();
	}

	/**
	 * Returns the first element matching the predicate starting element with the
	 * given id and walking up the parent hierarchy.
	 * 
	 * @param element   element to start the search from
	 * @param predicate predicate which the element should match
	 * @return an optional with the first element matching the predicate or an empty
	 *         optional
	 */
	default public Optional<GModelElement> findElement(String elementId, Predicate<GModelElement> predicate) {
		Optional<GModelElement> element = get(elementId);
		if (element.isPresent()) {
			return findElement(element.get(), predicate);
		}
		return Optional.empty();
	}

	/**
	 * Returns the first element matching the predicate starting from the given
	 * element and walking up the parent hierarchy.
	 * 
	 * @param element   element to start the search from
	 * @param predicate predicate which the element should match
	 * @return an optional with the first element matching the predicate or an empty
	 *         optional
	 */
	default public Optional<GModelElement> findElement(GModelElement element, Predicate<GModelElement> predicate) {
		if (element == null) {
			return Optional.empty();
		}
		if (predicate.test(element)) {
			return Optional.of(element);
		}

		GModelElement parent = element.getParent();
		return parent != null ? findElement(parent, predicate) : Optional.empty();
	}

	default public <T extends GModelElement> Set<T> getAllByClass(Class<T> clazz) {
		return findAll((GModelElement) this.getRoot(), clazz);
	}

	default public <T extends GModelElement> Set<T> findAll(GModelElement parent, Class<T> type) {
		return getStream(parent).flatMap(this::getStream).filter(type::isInstance).map(type::cast)
				.collect(Collectors.toSet());
	}

	default public Stream<GModelElement> getStream(GModelElement element) {
		if (element == null) {
			return Stream.empty();
		}
		if (element.getChildren() == null) {
			return Stream.of(element);
		}
		return Stream.concat(Stream.of(element), element.getChildren().stream());
	}

}
