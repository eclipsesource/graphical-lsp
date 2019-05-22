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
import com.eclipsesource.glsp.graph.GCompartment;
import com.eclipsesource.glsp.graph.GDimension;
import com.eclipsesource.glsp.graph.GLayoutOptions;
import com.eclipsesource.glsp.graph.GLayouting;
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
 * An implementation of the model object '<em><b>GCompartment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GCompartmentImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GCompartmentImpl#getCssClasses <em>Css Classes</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GCompartmentImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GCompartmentImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GCompartmentImpl#getTrace <em>Trace</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GCompartmentImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GCompartmentImpl#getSize <em>Size</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GCompartmentImpl#getLayout <em>Layout</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GCompartmentImpl#getLayoutOptions <em>Layout Options</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GCompartmentImpl extends MinimalEObjectImpl.Container implements GCompartment {
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
	 * The default value of the '{@link #getLayout() <em>Layout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayout()
	 * @generated
	 * @ordered
	 */
	protected static final String LAYOUT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLayout() <em>Layout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayout()
	 * @generated
	 * @ordered
	 */
	protected String layout = LAYOUT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getLayoutOptions() <em>Layout Options</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLayoutOptions()
	 * @generated
	 * @ordered
	 */
	protected GLayoutOptions layoutOptions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GCompartmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GraphPackage.Literals.GCOMPARTMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GCOMPARTMENT__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<String> getCssClasses() {
		if (cssClasses == null) {
			cssClasses = new EDataTypeUniqueEList<String>(String.class, this, GraphPackage.GCOMPARTMENT__CSS_CLASSES);
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
					GraphPackage.GCOMPARTMENT__CHILDREN, GraphPackage.GMODEL_ELEMENT__PARENT);
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
		if (eContainerFeatureID() != GraphPackage.GCOMPARTMENT__PARENT)
			return null;
		return (GModelElement) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(GModelElement newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newParent, GraphPackage.GCOMPARTMENT__PARENT, msgs);
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
				|| (eContainerFeatureID() != GraphPackage.GCOMPARTMENT__PARENT && newParent != null)) {
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
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GCOMPARTMENT__PARENT, newParent,
					newParent));
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
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GCOMPARTMENT__TRACE, oldTrace, trace));
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
					GraphPackage.GCOMPARTMENT__POSITION, oldPosition, newPosition);
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
						EOPPOSITE_FEATURE_BASE - GraphPackage.GCOMPARTMENT__POSITION, null, msgs);
			if (newPosition != null)
				msgs = ((InternalEObject) newPosition).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GraphPackage.GCOMPARTMENT__POSITION, null, msgs);
			msgs = basicSetPosition(newPosition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GCOMPARTMENT__POSITION, newPosition,
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GraphPackage.GCOMPARTMENT__SIZE, oldSize, newSize);
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
						EOPPOSITE_FEATURE_BASE - GraphPackage.GCOMPARTMENT__SIZE, null, msgs);
			if (newSize != null)
				msgs = ((InternalEObject) newSize).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GraphPackage.GCOMPARTMENT__SIZE, null, msgs);
			msgs = basicSetSize(newSize, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GCOMPARTMENT__SIZE, newSize, newSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLayout() {
		return layout;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLayout(String newLayout) {
		String oldLayout = layout;
		layout = newLayout;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GCOMPARTMENT__LAYOUT, oldLayout,
					layout));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GLayoutOptions getLayoutOptions() {
		return layoutOptions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLayoutOptions(GLayoutOptions newLayoutOptions, NotificationChain msgs) {
		GLayoutOptions oldLayoutOptions = layoutOptions;
		layoutOptions = newLayoutOptions;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					GraphPackage.GCOMPARTMENT__LAYOUT_OPTIONS, oldLayoutOptions, newLayoutOptions);
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
	public void setLayoutOptions(GLayoutOptions newLayoutOptions) {
		if (newLayoutOptions != layoutOptions) {
			NotificationChain msgs = null;
			if (layoutOptions != null)
				msgs = ((InternalEObject) layoutOptions).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - GraphPackage.GCOMPARTMENT__LAYOUT_OPTIONS, null, msgs);
			if (newLayoutOptions != null)
				msgs = ((InternalEObject) newLayoutOptions).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GraphPackage.GCOMPARTMENT__LAYOUT_OPTIONS, null, msgs);
			msgs = basicSetLayoutOptions(newLayoutOptions, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GCOMPARTMENT__LAYOUT_OPTIONS,
					newLayoutOptions, newLayoutOptions));
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
		case GraphPackage.GCOMPARTMENT__CHILDREN:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getChildren()).basicAdd(otherEnd, msgs);
		case GraphPackage.GCOMPARTMENT__PARENT:
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
		case GraphPackage.GCOMPARTMENT__CHILDREN:
			return ((InternalEList<?>) getChildren()).basicRemove(otherEnd, msgs);
		case GraphPackage.GCOMPARTMENT__PARENT:
			return basicSetParent(null, msgs);
		case GraphPackage.GCOMPARTMENT__POSITION:
			return basicSetPosition(null, msgs);
		case GraphPackage.GCOMPARTMENT__SIZE:
			return basicSetSize(null, msgs);
		case GraphPackage.GCOMPARTMENT__LAYOUT_OPTIONS:
			return basicSetLayoutOptions(null, msgs);
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
		case GraphPackage.GCOMPARTMENT__PARENT:
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
		case GraphPackage.GCOMPARTMENT__ID:
			return getId();
		case GraphPackage.GCOMPARTMENT__CSS_CLASSES:
			return getCssClasses();
		case GraphPackage.GCOMPARTMENT__CHILDREN:
			return getChildren();
		case GraphPackage.GCOMPARTMENT__PARENT:
			return getParent();
		case GraphPackage.GCOMPARTMENT__TRACE:
			return getTrace();
		case GraphPackage.GCOMPARTMENT__POSITION:
			return getPosition();
		case GraphPackage.GCOMPARTMENT__SIZE:
			return getSize();
		case GraphPackage.GCOMPARTMENT__LAYOUT:
			return getLayout();
		case GraphPackage.GCOMPARTMENT__LAYOUT_OPTIONS:
			return getLayoutOptions();
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
		case GraphPackage.GCOMPARTMENT__ID:
			setId((String) newValue);
			return;
		case GraphPackage.GCOMPARTMENT__CSS_CLASSES:
			getCssClasses().clear();
			getCssClasses().addAll((Collection<? extends String>) newValue);
			return;
		case GraphPackage.GCOMPARTMENT__CHILDREN:
			getChildren().clear();
			getChildren().addAll((Collection<? extends GModelElement>) newValue);
			return;
		case GraphPackage.GCOMPARTMENT__PARENT:
			setParent((GModelElement) newValue);
			return;
		case GraphPackage.GCOMPARTMENT__TRACE:
			setTrace((String) newValue);
			return;
		case GraphPackage.GCOMPARTMENT__POSITION:
			setPosition((GPoint) newValue);
			return;
		case GraphPackage.GCOMPARTMENT__SIZE:
			setSize((GDimension) newValue);
			return;
		case GraphPackage.GCOMPARTMENT__LAYOUT:
			setLayout((String) newValue);
			return;
		case GraphPackage.GCOMPARTMENT__LAYOUT_OPTIONS:
			setLayoutOptions((GLayoutOptions) newValue);
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
		case GraphPackage.GCOMPARTMENT__ID:
			setId(ID_EDEFAULT);
			return;
		case GraphPackage.GCOMPARTMENT__CSS_CLASSES:
			getCssClasses().clear();
			return;
		case GraphPackage.GCOMPARTMENT__CHILDREN:
			getChildren().clear();
			return;
		case GraphPackage.GCOMPARTMENT__PARENT:
			setParent((GModelElement) null);
			return;
		case GraphPackage.GCOMPARTMENT__TRACE:
			setTrace(TRACE_EDEFAULT);
			return;
		case GraphPackage.GCOMPARTMENT__POSITION:
			setPosition((GPoint) null);
			return;
		case GraphPackage.GCOMPARTMENT__SIZE:
			setSize((GDimension) null);
			return;
		case GraphPackage.GCOMPARTMENT__LAYOUT:
			setLayout(LAYOUT_EDEFAULT);
			return;
		case GraphPackage.GCOMPARTMENT__LAYOUT_OPTIONS:
			setLayoutOptions((GLayoutOptions) null);
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
		case GraphPackage.GCOMPARTMENT__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case GraphPackage.GCOMPARTMENT__CSS_CLASSES:
			return cssClasses != null && !cssClasses.isEmpty();
		case GraphPackage.GCOMPARTMENT__CHILDREN:
			return children != null && !children.isEmpty();
		case GraphPackage.GCOMPARTMENT__PARENT:
			return getParent() != null;
		case GraphPackage.GCOMPARTMENT__TRACE:
			return TRACE_EDEFAULT == null ? trace != null : !TRACE_EDEFAULT.equals(trace);
		case GraphPackage.GCOMPARTMENT__POSITION:
			return position != null;
		case GraphPackage.GCOMPARTMENT__SIZE:
			return size != null;
		case GraphPackage.GCOMPARTMENT__LAYOUT:
			return LAYOUT_EDEFAULT == null ? layout != null : !LAYOUT_EDEFAULT.equals(layout);
		case GraphPackage.GCOMPARTMENT__LAYOUT_OPTIONS:
			return layoutOptions != null;
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
			case GraphPackage.GCOMPARTMENT__POSITION:
				return GraphPackage.GBOUNDS_AWARE__POSITION;
			case GraphPackage.GCOMPARTMENT__SIZE:
				return GraphPackage.GBOUNDS_AWARE__SIZE;
			default:
				return -1;
			}
		}
		if (baseClass == GLayouting.class) {
			switch (derivedFeatureID) {
			case GraphPackage.GCOMPARTMENT__LAYOUT:
				return GraphPackage.GLAYOUTING__LAYOUT;
			case GraphPackage.GCOMPARTMENT__LAYOUT_OPTIONS:
				return GraphPackage.GLAYOUTING__LAYOUT_OPTIONS;
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
				return GraphPackage.GCOMPARTMENT__POSITION;
			case GraphPackage.GBOUNDS_AWARE__SIZE:
				return GraphPackage.GCOMPARTMENT__SIZE;
			default:
				return -1;
			}
		}
		if (baseClass == GLayouting.class) {
			switch (baseFeatureID) {
			case GraphPackage.GLAYOUTING__LAYOUT:
				return GraphPackage.GCOMPARTMENT__LAYOUT;
			case GraphPackage.GLAYOUTING__LAYOUT_OPTIONS:
				return GraphPackage.GCOMPARTMENT__LAYOUT_OPTIONS;
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
		result.append(", layout: ");
		result.append(layout);
		result.append(')');
		return result.toString();
	}

} //GCompartmentImpl
