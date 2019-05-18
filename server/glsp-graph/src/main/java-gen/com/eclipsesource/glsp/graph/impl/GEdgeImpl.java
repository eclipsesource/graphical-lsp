/**
 * Copyright (c) 2019 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package com.eclipsesource.glsp.graph.impl;

import com.eclipsesource.glsp.graph.GEdge;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.graph.GraphPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>GEdge</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GEdgeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GEdgeImpl#getCssClasses <em>Css Classes</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GEdgeImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GEdgeImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GEdgeImpl#getTrace <em>Trace</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GEdgeImpl#getSourceId <em>Source Id</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GEdgeImpl#getTargetId <em>Target Id</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GEdgeImpl#getRoutingPoints <em>Routing Points</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GEdgeImpl#getSource <em>Source</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GEdgeImpl#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GEdgeImpl extends MinimalEObjectImpl.Container implements GEdge {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCssClasses() <em>Css Classes</em>}' attribute list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCssClasses()
	 * @generated
	 * @ordered
	 */
	protected EList<String> cssClasses;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<GModelElement> children;

	/**
	 * The default value of the '{@link #getTrace() <em>Trace</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTrace()
	 * @generated
	 * @ordered
	 */
	protected static final String TRACE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTrace() <em>Trace</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getTrace()
	 * @generated
	 * @ordered
	 */
	protected String trace = TRACE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSourceId() <em>Source Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSourceId()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_ID_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getTargetId() <em>Target Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTargetId()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRoutingPoints() <em>Routing Points</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getRoutingPoints()
	 * @generated
	 * @ordered
	 */
	protected EList<GPoint> routingPoints;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected GModelElement source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected GModelElement target;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected GEdgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GraphPackage.Literals.GEDGE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getCssClasses() {
		if (cssClasses == null) {
			cssClasses = new EDataTypeUniqueEList<String>(String.class, this, GraphPackage.GEDGE__CSS_CLASSES);
		}
		return cssClasses;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GModelElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<GModelElement>(GModelElement.class, this,
					GraphPackage.GEDGE__CHILDREN, GraphPackage.GMODEL_ELEMENT__PARENT);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GModelElement getParent() {
		if (eContainerFeatureID() != GraphPackage.GEDGE__PARENT)
			return null;
		return (GModelElement) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(GModelElement newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newParent, GraphPackage.GEDGE__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParent(GModelElement newParent) {
		if (newParent != eInternalContainer()
				|| (eContainerFeatureID() != GraphPackage.GEDGE__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject) newParent).eInverseAdd(this, GraphPackage.GMODEL_ELEMENT__CHILDREN,
						GModelElement.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTrace() {
		return trace;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTrace(String newTrace) {
		String oldTrace = trace;
		trace = newTrace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE__TRACE, oldTrace, trace));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getSourceId() {
		if (this.getSource() != null) {
			return this.getSource().getId();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public String getTargetId() {
		if (this.getTarget() != null) {
			return this.getTarget().getId();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GPoint> getRoutingPoints() {
		if (routingPoints == null) {
			routingPoints = new EObjectContainmentEList<GPoint>(GPoint.class, this, GraphPackage.GEDGE__ROUTING_POINTS);
		}
		return routingPoints;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GModelElement getSource() {
		if (source != null && source.eIsProxy()) {
			InternalEObject oldSource = (InternalEObject) source;
			source = (GModelElement) eResolveProxy(oldSource);
			if (source != oldSource) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GraphPackage.GEDGE__SOURCE, oldSource,
							source));
			}
		}
		return source;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public GModelElement basicGetSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSource(GModelElement newSource) {
		GModelElement oldSource = source;
		source = newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GModelElement getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject) target;
			target = (GModelElement) eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GraphPackage.GEDGE__TARGET, oldTarget,
							target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public GModelElement basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTarget(GModelElement newTarget) {
		GModelElement oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GEDGE__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GraphPackage.GEDGE__CHILDREN:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getChildren()).basicAdd(otherEnd, msgs);
		case GraphPackage.GEDGE__PARENT:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetParent((GModelElement) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GraphPackage.GEDGE__CHILDREN:
			return ((InternalEList<?>) getChildren()).basicRemove(otherEnd, msgs);
		case GraphPackage.GEDGE__PARENT:
			return basicSetParent(null, msgs);
		case GraphPackage.GEDGE__ROUTING_POINTS:
			return ((InternalEList<?>) getRoutingPoints()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case GraphPackage.GEDGE__PARENT:
			return eInternalContainer().eInverseRemove(this, GraphPackage.GMODEL_ELEMENT__CHILDREN, GModelElement.class,
					msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GraphPackage.GEDGE__ID:
			return getId();
		case GraphPackage.GEDGE__CSS_CLASSES:
			return getCssClasses();
		case GraphPackage.GEDGE__CHILDREN:
			return getChildren();
		case GraphPackage.GEDGE__PARENT:
			return getParent();
		case GraphPackage.GEDGE__TRACE:
			return getTrace();
		case GraphPackage.GEDGE__SOURCE_ID:
			return getSourceId();
		case GraphPackage.GEDGE__TARGET_ID:
			return getTargetId();
		case GraphPackage.GEDGE__ROUTING_POINTS:
			return getRoutingPoints();
		case GraphPackage.GEDGE__SOURCE:
			if (resolve)
				return getSource();
			return basicGetSource();
		case GraphPackage.GEDGE__TARGET:
			if (resolve)
				return getTarget();
			return basicGetTarget();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case GraphPackage.GEDGE__ID:
			setId((String) newValue);
			return;
		case GraphPackage.GEDGE__CSS_CLASSES:
			getCssClasses().clear();
			getCssClasses().addAll((Collection<? extends String>) newValue);
			return;
		case GraphPackage.GEDGE__CHILDREN:
			getChildren().clear();
			getChildren().addAll((Collection<? extends GModelElement>) newValue);
			return;
		case GraphPackage.GEDGE__PARENT:
			setParent((GModelElement) newValue);
			return;
		case GraphPackage.GEDGE__TRACE:
			setTrace((String) newValue);
			return;
		case GraphPackage.GEDGE__ROUTING_POINTS:
			getRoutingPoints().clear();
			getRoutingPoints().addAll((Collection<? extends GPoint>) newValue);
			return;
		case GraphPackage.GEDGE__SOURCE:
			setSource((GModelElement) newValue);
			return;
		case GraphPackage.GEDGE__TARGET:
			setTarget((GModelElement) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case GraphPackage.GEDGE__ID:
			setId(ID_EDEFAULT);
			return;
		case GraphPackage.GEDGE__CSS_CLASSES:
			getCssClasses().clear();
			return;
		case GraphPackage.GEDGE__CHILDREN:
			getChildren().clear();
			return;
		case GraphPackage.GEDGE__PARENT:
			setParent((GModelElement) null);
			return;
		case GraphPackage.GEDGE__TRACE:
			setTrace(TRACE_EDEFAULT);
			return;
		case GraphPackage.GEDGE__ROUTING_POINTS:
			getRoutingPoints().clear();
			return;
		case GraphPackage.GEDGE__SOURCE:
			setSource((GModelElement) null);
			return;
		case GraphPackage.GEDGE__TARGET:
			setTarget((GModelElement) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case GraphPackage.GEDGE__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case GraphPackage.GEDGE__CSS_CLASSES:
			return cssClasses != null && !cssClasses.isEmpty();
		case GraphPackage.GEDGE__CHILDREN:
			return children != null && !children.isEmpty();
		case GraphPackage.GEDGE__PARENT:
			return getParent() != null;
		case GraphPackage.GEDGE__TRACE:
			return TRACE_EDEFAULT == null ? trace != null : !TRACE_EDEFAULT.equals(trace);
		case GraphPackage.GEDGE__SOURCE_ID:
			return SOURCE_ID_EDEFAULT == null ? getSourceId() != null : !SOURCE_ID_EDEFAULT.equals(getSourceId());
		case GraphPackage.GEDGE__TARGET_ID:
			return TARGET_ID_EDEFAULT == null ? getTargetId() != null : !TARGET_ID_EDEFAULT.equals(getTargetId());
		case GraphPackage.GEDGE__ROUTING_POINTS:
			return routingPoints != null && !routingPoints.isEmpty();
		case GraphPackage.GEDGE__SOURCE:
			return source != null;
		case GraphPackage.GEDGE__TARGET:
			return target != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(", cssClasses: ");
		result.append(cssClasses);
		result.append(", trace: ");
		result.append(trace);
		result.append(')');
		return result.toString();
	}

} // GEdgeImpl
