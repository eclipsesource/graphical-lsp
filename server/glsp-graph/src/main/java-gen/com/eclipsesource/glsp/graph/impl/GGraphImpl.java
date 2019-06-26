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
import com.eclipsesource.glsp.graph.GDimension;
import com.eclipsesource.glsp.graph.GGraph;
import com.eclipsesource.glsp.graph.GLayoutOptions;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.graph.GraphPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>GGraph</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GGraphImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GGraphImpl#getSize <em>Size</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GGraphImpl#getLayoutOptions <em>Layout Options</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GGraphImpl extends GModelRootImpl implements GGraph {
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
	public GGraphImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GraphPackage.Literals.GGRAPH;
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
					GraphPackage.GGRAPH__POSITION, oldPosition, newPosition);
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
						EOPPOSITE_FEATURE_BASE - GraphPackage.GGRAPH__POSITION, null, msgs);
			if (newPosition != null)
				msgs = ((InternalEObject) newPosition).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GraphPackage.GGRAPH__POSITION, null, msgs);
			msgs = basicSetPosition(newPosition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GGRAPH__POSITION, newPosition,
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GraphPackage.GGRAPH__SIZE,
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
				msgs = ((InternalEObject) size).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GraphPackage.GGRAPH__SIZE,
						null, msgs);
			if (newSize != null)
				msgs = ((InternalEObject) newSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GraphPackage.GGRAPH__SIZE,
						null, msgs);
			msgs = basicSetSize(newSize, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GGRAPH__SIZE, newSize, newSize));
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
					GraphPackage.GGRAPH__LAYOUT_OPTIONS, oldLayoutOptions, newLayoutOptions);
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
						EOPPOSITE_FEATURE_BASE - GraphPackage.GGRAPH__LAYOUT_OPTIONS, null, msgs);
			if (newLayoutOptions != null)
				msgs = ((InternalEObject) newLayoutOptions).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - GraphPackage.GGRAPH__LAYOUT_OPTIONS, null, msgs);
			msgs = basicSetLayoutOptions(newLayoutOptions, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GGRAPH__LAYOUT_OPTIONS, newLayoutOptions,
					newLayoutOptions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case GraphPackage.GGRAPH__POSITION:
			return basicSetPosition(null, msgs);
		case GraphPackage.GGRAPH__SIZE:
			return basicSetSize(null, msgs);
		case GraphPackage.GGRAPH__LAYOUT_OPTIONS:
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GraphPackage.GGRAPH__POSITION:
			return getPosition();
		case GraphPackage.GGRAPH__SIZE:
			return getSize();
		case GraphPackage.GGRAPH__LAYOUT_OPTIONS:
			return getLayoutOptions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case GraphPackage.GGRAPH__POSITION:
			setPosition((GPoint) newValue);
			return;
		case GraphPackage.GGRAPH__SIZE:
			setSize((GDimension) newValue);
			return;
		case GraphPackage.GGRAPH__LAYOUT_OPTIONS:
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
		case GraphPackage.GGRAPH__POSITION:
			setPosition((GPoint) null);
			return;
		case GraphPackage.GGRAPH__SIZE:
			setSize((GDimension) null);
			return;
		case GraphPackage.GGRAPH__LAYOUT_OPTIONS:
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
		case GraphPackage.GGRAPH__POSITION:
			return position != null;
		case GraphPackage.GGRAPH__SIZE:
			return size != null;
		case GraphPackage.GGRAPH__LAYOUT_OPTIONS:
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
			case GraphPackage.GGRAPH__POSITION:
				return GraphPackage.GBOUNDS_AWARE__POSITION;
			case GraphPackage.GGRAPH__SIZE:
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
				return GraphPackage.GGRAPH__POSITION;
			case GraphPackage.GBOUNDS_AWARE__SIZE:
				return GraphPackage.GGRAPH__SIZE;
			default:
				return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //GGraphImpl
