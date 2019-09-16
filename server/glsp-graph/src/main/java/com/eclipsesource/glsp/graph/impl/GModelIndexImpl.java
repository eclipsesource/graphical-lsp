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
package com.eclipsesource.glsp.graph.impl;

import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GEDGE__SOURCE;
import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GEDGE__TARGET;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;

import com.eclipsesource.glsp.graph.GEdge;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GModelIndex;
import com.google.common.base.Preconditions;

public class GModelIndexImpl extends ECrossReferenceAdapter implements GModelIndex {

	private final Map<String, GModelElement> idToElement = new HashMap<>();
	private final Map<EClass, Set<GModelElement>> typeToElements = new HashMap<>();
	private GModelElement root;

	public GModelIndexImpl(EObject target) {
		Preconditions.checkArgument(target instanceof GModelElement);
		this.root = (GModelElement) target;
		target.eAdapters().add(this);
		addIfGModelElement(target);
	}

	@Override
	protected void addAdapter(Notifier notifier) {
		super.addAdapter(notifier);
		addIfGModelElement(notifier);
	}

	protected void addIfGModelElement(Notifier notifier) {
		if (notifier instanceof GModelElement) {
			notifyAdd((GModelElement) notifier);
		}
	}

	@Override
	protected void removeAdapter(Notifier notifier) {
		super.removeAdapter(notifier);
		if (notifier instanceof GModelElement) {
			notifyRemove((GModelElement) notifier);
		}
	}

	protected void notifyAdd(GModelElement element) {
		if (idToElement.put(element.getId(), element) == null) {
			getTypeSet(element.eClass()).add(element);
			for (GModelElement child : element.getChildren()) {
				notifyAdd(child);
			}
		}
	}

	protected void notifyRemove(GModelElement element) {
		if (idToElement.remove(element.getId()) != null) {
			getTypeSet(element.eClass()).remove(element);
			for (GModelElement child : element.getChildren()) {
				notifyRemove(child);
			}
		}
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return GModelIndexImpl.class.equals(type);
	}

	@Override
	protected void handleContainment(Notification notification) {
		super.handleContainment(notification);
		switch (notification.getEventType()) {
		case Notification.REMOVE: {
			Notifier oldValue = (Notifier) notification.getOldValue();
			removeAdapter(oldValue);
			break;
		}
		case Notification.REMOVE_MANY: {
			for (Object oldValue : (Collection<?>) notification.getOldValue()) {
				removeAdapter((Notifier) oldValue);
			}
			break;
		}
		}
	}

	@Override
	public Optional<GModelElement> get(String elementId) {
		return Optional.ofNullable(idToElement.get(elementId));
	}

	@Override
	public Set<GModelElement> getAll(Collection<String> elementIds) {
		return elementIds.stream().map(this::get).map(Optional::get).collect(Collectors.toSet());
	}

	@Override
	public Collection<GEdge> getIncomingEdges(GModelElement node) {
		return getEdgesWithIncomingReference(node, GEDGE__TARGET);
	}

	@Override
	public Collection<GEdge> getOutgoingEdges(GModelElement node) {
		return getEdgesWithIncomingReference(node, GEDGE__SOURCE);
	}

	protected List<GEdge> getEdgesWithIncomingReference(GModelElement node, EReference feature) {
		return this.getNonNavigableInverseReferences(node).stream()
				.filter(s -> feature.equals(s.getEStructuralFeature())).filter(s -> s.getEObject() instanceof GEdge)
				.map(s -> (GEdge) s.getEObject()).collect(Collectors.toList());
	}

	@Override
	public Set<String> allIds() {
		return idToElement.keySet();
	}

	@Override
	public int getCounter(EClass eClass, Function<Integer, String> idProvider) {
		int i = getTypeCount(eClass);
		while (true) {
			String id = idProvider.apply(i);
			if (!get(id).isPresent()) {
				break;
			}
			i++;
		}
		return i;
	}

	private Set<GModelElement> getTypeSet(EClass eClass) {
		return typeToElements.computeIfAbsent(eClass, t -> new HashSet<>());
	}

	@Override
	public int getTypeCount(EClass eClass) {
		return getTypeSet(eClass).size();
	}

	@Override
	public GModelElement getRoot() {
		return root;
	}

}
