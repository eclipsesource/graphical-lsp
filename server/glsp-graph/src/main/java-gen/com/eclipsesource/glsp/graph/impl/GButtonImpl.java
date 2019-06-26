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

import com.eclipsesource.glsp.graph.GBoundsAware;
import com.eclipsesource.glsp.graph.GButton;
import com.eclipsesource.glsp.graph.GDimension;
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
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GButton</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GButtonImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GButtonImpl#getCssClasses <em>Css Classes</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GButtonImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GButtonImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GButtonImpl#getTrace <em>Trace</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GButtonImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GButtonImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GButtonImpl#getSize <em>Size</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GButtonImpl#isEnabled <em>Enabled</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GButtonImpl extends MinimalEObjectImpl.Container implements GButton {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCssClasses() <em>Css Classes</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCssClasses()
	 * @generated
	 * @ordered
	 */
	protected EList<String> cssClasses;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<GModelElement> children;

	/**
	 * The default value of the '{@link #getTrace() <em>Trace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrace()
	 * @generated
	 * @ordered
	 */
	protected static final String TRACE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTrace() <em>Trace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrace()
	 * @generated
	 * @ordered
	 */
	protected String trace = TRACE_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPosition() <em>Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected GPoint position;

	/**
	 * The cached value of the '{@link #getSize() <em>Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected GDimension size;

	/**
	 * The default value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean enabled = ENABLED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GButtonImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GraphPackage.Literals.GBUTTON;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GBUTTON__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getCssClasses() {
		if (cssClasses == null) {
			cssClasses = new EDataTypeUniqueEList<String>(String.class, this, GraphPackage.GBUTTON__CSS_CLASSES);
		}
		return cssClasses;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GModelElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<GModelElement>(GModelElement.class, this,
					GraphPackage.GBUTTON__CHILDREN, GraphPackage.GMODEL_ELEMENT__PARENT);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GModelElement getParent() {
		if (eContainerFeatureID() != GraphPackage.GBUTTON__PARENT)
			return null;
		return (GModelElement) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(GModelElement newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newParent, GraphPackage.GBUTTON__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParent(GModelElement newParent) {
		if (newParent != eInternalContainer()
				|| (eContainerFeatureID() != GraphPackage.GBUTTON__PARENT && newParent != null)) {
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
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GBUTTON__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTrace() {
		return trace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTrace(String newTrace) {
		String oldTrace = trace;
		trace = newTrace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GBUTTON__TRACE, oldTrace, trace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GBUTTON__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GPoint getPosition() {
		return position;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPosition(GPoint newPosition, NotificationChain msgs) {
		GPoint oldPosition = position;
		position = newPosition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GraphPackage.GBUTTON__POSITION, oldPosition, newPosition);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPosition(GPoint newPosition) {
		if (newPosition != position) {
			NotificationChain msgs = null;
			if (position != null)
				msgs = ((InternalEObject) position).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GraphPackage.GBUTTON__POSITION, null, msgs);
			if (newPosition != null)
				msgs = ((InternalEObject) newPosition).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GraphPackage.GBUTTON__POSITION, null, msgs);
			msgs = basicSetPosition(newPosition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GBUTTON__POSITION, newPosition,
					newPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GDimension getSize() {
		return size;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSize(GDimension newSize, NotificationChain msgs) {
		GDimension oldSize = size;
		size = newSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GraphPackage.GBUTTON__SIZE,
					oldSize, newSize);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSize(GDimension newSize) {
		if (newSize != size) {
			NotificationChain msgs = null;
			if (size != null)
				msgs = ((InternalEObject) size).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GraphPackage.GBUTTON__SIZE, null, msgs);
			if (newSize != null)
				msgs = ((InternalEObject) newSize).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GraphPackage.GBUTTON__SIZE, null, msgs);
			msgs = basicSetSize(newSize, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GBUTTON__SIZE, newSize, newSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEnabled(boolean newEnabled) {
		boolean oldEnabled = enabled;
		enabled = newEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GBUTTON__ENABLED, oldEnabled, enabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GraphPackage.GBUTTON__CHILDREN:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getChildren()).basicAdd(otherEnd, msgs);
		case GraphPackage.GBUTTON__PARENT:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetParent((GModelElement) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GraphPackage.GBUTTON__CHILDREN:
			return ((InternalEList<?>) getChildren()).basicRemove(otherEnd, msgs);
		case GraphPackage.GBUTTON__PARENT:
			return basicSetParent(null, msgs);
		case GraphPackage.GBUTTON__POSITION:
			return basicSetPosition(null, msgs);
		case GraphPackage.GBUTTON__SIZE:
			return basicSetSize(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case GraphPackage.GBUTTON__PARENT:
			return eInternalContainer().eInverseRemove(this, GraphPackage.GMODEL_ELEMENT__CHILDREN, GModelElement.class,
					msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GraphPackage.GBUTTON__ID:
			return getId();
		case GraphPackage.GBUTTON__CSS_CLASSES:
			return getCssClasses();
		case GraphPackage.GBUTTON__CHILDREN:
			return getChildren();
		case GraphPackage.GBUTTON__PARENT:
			return getParent();
		case GraphPackage.GBUTTON__TRACE:
			return getTrace();
		case GraphPackage.GBUTTON__TYPE:
			return getType();
		case GraphPackage.GBUTTON__POSITION:
			return getPosition();
		case GraphPackage.GBUTTON__SIZE:
			return getSize();
		case GraphPackage.GBUTTON__ENABLED:
			return isEnabled();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case GraphPackage.GBUTTON__ID:
			setId((String) newValue);
			return;
		case GraphPackage.GBUTTON__CSS_CLASSES:
			getCssClasses().clear();
			getCssClasses().addAll((Collection<? extends String>) newValue);
			return;
		case GraphPackage.GBUTTON__CHILDREN:
			getChildren().clear();
			getChildren().addAll((Collection<? extends GModelElement>) newValue);
			return;
		case GraphPackage.GBUTTON__PARENT:
			setParent((GModelElement) newValue);
			return;
		case GraphPackage.GBUTTON__TRACE:
			setTrace((String) newValue);
			return;
		case GraphPackage.GBUTTON__TYPE:
			setType((String) newValue);
			return;
		case GraphPackage.GBUTTON__POSITION:
			setPosition((GPoint) newValue);
			return;
		case GraphPackage.GBUTTON__SIZE:
			setSize((GDimension) newValue);
			return;
		case GraphPackage.GBUTTON__ENABLED:
			setEnabled((Boolean) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case GraphPackage.GBUTTON__ID:
			setId(ID_EDEFAULT);
			return;
		case GraphPackage.GBUTTON__CSS_CLASSES:
			getCssClasses().clear();
			return;
		case GraphPackage.GBUTTON__CHILDREN:
			getChildren().clear();
			return;
		case GraphPackage.GBUTTON__PARENT:
			setParent((GModelElement) null);
			return;
		case GraphPackage.GBUTTON__TRACE:
			setTrace(TRACE_EDEFAULT);
			return;
		case GraphPackage.GBUTTON__TYPE:
			setType(TYPE_EDEFAULT);
			return;
		case GraphPackage.GBUTTON__POSITION:
			setPosition((GPoint) null);
			return;
		case GraphPackage.GBUTTON__SIZE:
			setSize((GDimension) null);
			return;
		case GraphPackage.GBUTTON__ENABLED:
			setEnabled(ENABLED_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case GraphPackage.GBUTTON__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case GraphPackage.GBUTTON__CSS_CLASSES:
			return cssClasses != null && !cssClasses.isEmpty();
		case GraphPackage.GBUTTON__CHILDREN:
			return children != null && !children.isEmpty();
		case GraphPackage.GBUTTON__PARENT:
			return getParent() != null;
		case GraphPackage.GBUTTON__TRACE:
			return TRACE_EDEFAULT == null ? trace != null : !TRACE_EDEFAULT.equals(trace);
		case GraphPackage.GBUTTON__TYPE:
			return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
		case GraphPackage.GBUTTON__POSITION:
			return position != null;
		case GraphPackage.GBUTTON__SIZE:
			return size != null;
		case GraphPackage.GBUTTON__ENABLED:
			return enabled != ENABLED_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == GBoundsAware.class) {
			switch (derivedFeatureID) {
			case GraphPackage.GBUTTON__POSITION:
				return GraphPackage.GBOUNDS_AWARE__POSITION;
			case GraphPackage.GBUTTON__SIZE:
				return GraphPackage.GBOUNDS_AWARE__SIZE;
			default:
				return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == GBoundsAware.class) {
			switch (baseFeatureID) {
			case GraphPackage.GBOUNDS_AWARE__POSITION:
				return GraphPackage.GBUTTON__POSITION;
			case GraphPackage.GBOUNDS_AWARE__SIZE:
				return GraphPackage.GBUTTON__SIZE;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
		result.append(", type: ");
		result.append(type);
		result.append(", enabled: ");
		result.append(enabled);
		result.append(')');
		return result.toString();
	}

} //GButtonImpl
