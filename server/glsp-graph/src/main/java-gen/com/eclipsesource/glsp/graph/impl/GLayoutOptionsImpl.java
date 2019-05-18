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

import com.eclipsesource.glsp.graph.GLayoutOptions;
import com.eclipsesource.glsp.graph.GraphPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>GLayout
 * Options</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GLayoutOptionsImpl#getPaddingLeft <em>Padding Left</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GLayoutOptionsImpl#getPaddingRight <em>Padding Right</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GLayoutOptionsImpl#getPaddingTop <em>Padding Top</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GLayoutOptionsImpl#getPaddingBottom <em>Padding Bottom</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GLayoutOptionsImpl#getPaddingFactor <em>Padding Factor</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GLayoutOptionsImpl#isResizeContainer <em>Resize Container</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GLayoutOptionsImpl#getVGap <em>VGap</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GLayoutOptionsImpl#getHGap <em>HGap</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GLayoutOptionsImpl#getVAlign <em>VAlign</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.impl.GLayoutOptionsImpl#getHAlign <em>HAlign</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GLayoutOptionsImpl extends MinimalEObjectImpl.Container implements GLayoutOptions {
	/**
	 * The default value of the '{@link #getPaddingLeft() <em>Padding Left</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPaddingLeft()
	 * @generated
	 * @ordered
	 */
	protected static final double PADDING_LEFT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getPaddingLeft() <em>Padding Left</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPaddingLeft()
	 * @generated
	 * @ordered
	 */
	protected double paddingLeft = PADDING_LEFT_EDEFAULT;

	/**
	 * The default value of the '{@link #getPaddingRight() <em>Padding Right</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPaddingRight()
	 * @generated
	 * @ordered
	 */
	protected static final double PADDING_RIGHT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getPaddingRight() <em>Padding Right</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPaddingRight()
	 * @generated
	 * @ordered
	 */
	protected double paddingRight = PADDING_RIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #getPaddingTop() <em>Padding Top</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPaddingTop()
	 * @generated
	 * @ordered
	 */
	protected static final double PADDING_TOP_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getPaddingTop() <em>Padding Top</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPaddingTop()
	 * @generated
	 * @ordered
	 */
	protected double paddingTop = PADDING_TOP_EDEFAULT;

	/**
	 * The default value of the '{@link #getPaddingBottom() <em>Padding Bottom</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPaddingBottom()
	 * @generated
	 * @ordered
	 */
	protected static final double PADDING_BOTTOM_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getPaddingBottom() <em>Padding Bottom</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPaddingBottom()
	 * @generated
	 * @ordered
	 */
	protected double paddingBottom = PADDING_BOTTOM_EDEFAULT;

	/**
	 * The default value of the '{@link #getPaddingFactor() <em>Padding Factor</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPaddingFactor()
	 * @generated
	 * @ordered
	 */
	protected static final double PADDING_FACTOR_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getPaddingFactor() <em>Padding Factor</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPaddingFactor()
	 * @generated
	 * @ordered
	 */
	protected double paddingFactor = PADDING_FACTOR_EDEFAULT;

	/**
	 * The default value of the '{@link #isResizeContainer() <em>Resize Container</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isResizeContainer()
	 * @generated
	 * @ordered
	 */
	protected static final boolean RESIZE_CONTAINER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isResizeContainer() <em>Resize Container</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isResizeContainer()
	 * @generated
	 * @ordered
	 */
	protected boolean resizeContainer = RESIZE_CONTAINER_EDEFAULT;

	/**
	 * The default value of the '{@link #getVGap() <em>VGap</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVGap()
	 * @generated
	 * @ordered
	 */
	protected static final double VGAP_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getVGap() <em>VGap</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getVGap()
	 * @generated
	 * @ordered
	 */
	protected double vGap = VGAP_EDEFAULT;

	/**
	 * The default value of the '{@link #getHGap() <em>HGap</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getHGap()
	 * @generated
	 * @ordered
	 */
	protected static final double HGAP_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getHGap() <em>HGap</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getHGap()
	 * @generated
	 * @ordered
	 */
	protected double hGap = HGAP_EDEFAULT;

	/**
	 * The default value of the '{@link #getVAlign() <em>VAlign</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVAlign()
	 * @generated
	 * @ordered
	 */
	protected static final String VALIGN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVAlign() <em>VAlign</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getVAlign()
	 * @generated
	 * @ordered
	 */
	protected String vAlign = VALIGN_EDEFAULT;

	/**
	 * The default value of the '{@link #getHAlign() <em>HAlign</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getHAlign()
	 * @generated
	 * @ordered
	 */
	protected static final String HALIGN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHAlign() <em>HAlign</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getHAlign()
	 * @generated
	 * @ordered
	 */
	protected String hAlign = HALIGN_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected GLayoutOptionsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GraphPackage.Literals.GLAYOUT_OPTIONS;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getPaddingLeft() {
		return paddingLeft;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPaddingLeft(double newPaddingLeft) {
		double oldPaddingLeft = paddingLeft;
		paddingLeft = newPaddingLeft;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__PADDING_LEFT,
					oldPaddingLeft, paddingLeft));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getPaddingRight() {
		return paddingRight;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPaddingRight(double newPaddingRight) {
		double oldPaddingRight = paddingRight;
		paddingRight = newPaddingRight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__PADDING_RIGHT,
					oldPaddingRight, paddingRight));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getPaddingTop() {
		return paddingTop;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPaddingTop(double newPaddingTop) {
		double oldPaddingTop = paddingTop;
		paddingTop = newPaddingTop;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__PADDING_TOP,
					oldPaddingTop, paddingTop));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getPaddingBottom() {
		return paddingBottom;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPaddingBottom(double newPaddingBottom) {
		double oldPaddingBottom = paddingBottom;
		paddingBottom = newPaddingBottom;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__PADDING_BOTTOM,
					oldPaddingBottom, paddingBottom));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getPaddingFactor() {
		return paddingFactor;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPaddingFactor(double newPaddingFactor) {
		double oldPaddingFactor = paddingFactor;
		paddingFactor = newPaddingFactor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__PADDING_FACTOR,
					oldPaddingFactor, paddingFactor));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isResizeContainer() {
		return resizeContainer;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResizeContainer(boolean newResizeContainer) {
		boolean oldResizeContainer = resizeContainer;
		resizeContainer = newResizeContainer;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__RESIZE_CONTAINER,
					oldResizeContainer, resizeContainer));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getVGap() {
		return vGap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVGap(double newVGap) {
		double oldVGap = vGap;
		vGap = newVGap;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__VGAP, oldVGap, vGap));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public double getHGap() {
		return hGap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHGap(double newHGap) {
		double oldHGap = hGap;
		hGap = newHGap;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__HGAP, oldHGap, hGap));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getVAlign() {
		return vAlign;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setVAlign(String newVAlign) {
		String oldVAlign = vAlign;
		vAlign = newVAlign;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__VALIGN, oldVAlign,
					vAlign));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getHAlign() {
		return hAlign;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHAlign(String newHAlign) {
		String oldHAlign = hAlign;
		hAlign = newHAlign;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GraphPackage.GLAYOUT_OPTIONS__HALIGN, oldHAlign,
					hAlign));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_LEFT:
			return getPaddingLeft();
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_RIGHT:
			return getPaddingRight();
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_TOP:
			return getPaddingTop();
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_BOTTOM:
			return getPaddingBottom();
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_FACTOR:
			return getPaddingFactor();
		case GraphPackage.GLAYOUT_OPTIONS__RESIZE_CONTAINER:
			return isResizeContainer();
		case GraphPackage.GLAYOUT_OPTIONS__VGAP:
			return getVGap();
		case GraphPackage.GLAYOUT_OPTIONS__HGAP:
			return getHGap();
		case GraphPackage.GLAYOUT_OPTIONS__VALIGN:
			return getVAlign();
		case GraphPackage.GLAYOUT_OPTIONS__HALIGN:
			return getHAlign();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_LEFT:
			setPaddingLeft((Double) newValue);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_RIGHT:
			setPaddingRight((Double) newValue);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_TOP:
			setPaddingTop((Double) newValue);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_BOTTOM:
			setPaddingBottom((Double) newValue);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_FACTOR:
			setPaddingFactor((Double) newValue);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__RESIZE_CONTAINER:
			setResizeContainer((Boolean) newValue);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__VGAP:
			setVGap((Double) newValue);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__HGAP:
			setHGap((Double) newValue);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__VALIGN:
			setVAlign((String) newValue);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__HALIGN:
			setHAlign((String) newValue);
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
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_LEFT:
			setPaddingLeft(PADDING_LEFT_EDEFAULT);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_RIGHT:
			setPaddingRight(PADDING_RIGHT_EDEFAULT);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_TOP:
			setPaddingTop(PADDING_TOP_EDEFAULT);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_BOTTOM:
			setPaddingBottom(PADDING_BOTTOM_EDEFAULT);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_FACTOR:
			setPaddingFactor(PADDING_FACTOR_EDEFAULT);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__RESIZE_CONTAINER:
			setResizeContainer(RESIZE_CONTAINER_EDEFAULT);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__VGAP:
			setVGap(VGAP_EDEFAULT);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__HGAP:
			setHGap(HGAP_EDEFAULT);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__VALIGN:
			setVAlign(VALIGN_EDEFAULT);
			return;
		case GraphPackage.GLAYOUT_OPTIONS__HALIGN:
			setHAlign(HALIGN_EDEFAULT);
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
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_LEFT:
			return paddingLeft != PADDING_LEFT_EDEFAULT;
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_RIGHT:
			return paddingRight != PADDING_RIGHT_EDEFAULT;
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_TOP:
			return paddingTop != PADDING_TOP_EDEFAULT;
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_BOTTOM:
			return paddingBottom != PADDING_BOTTOM_EDEFAULT;
		case GraphPackage.GLAYOUT_OPTIONS__PADDING_FACTOR:
			return paddingFactor != PADDING_FACTOR_EDEFAULT;
		case GraphPackage.GLAYOUT_OPTIONS__RESIZE_CONTAINER:
			return resizeContainer != RESIZE_CONTAINER_EDEFAULT;
		case GraphPackage.GLAYOUT_OPTIONS__VGAP:
			return vGap != VGAP_EDEFAULT;
		case GraphPackage.GLAYOUT_OPTIONS__HGAP:
			return hGap != HGAP_EDEFAULT;
		case GraphPackage.GLAYOUT_OPTIONS__VALIGN:
			return VALIGN_EDEFAULT == null ? vAlign != null : !VALIGN_EDEFAULT.equals(vAlign);
		case GraphPackage.GLAYOUT_OPTIONS__HALIGN:
			return HALIGN_EDEFAULT == null ? hAlign != null : !HALIGN_EDEFAULT.equals(hAlign);
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
		result.append(" (paddingLeft: ");
		result.append(paddingLeft);
		result.append(", paddingRight: ");
		result.append(paddingRight);
		result.append(", paddingTop: ");
		result.append(paddingTop);
		result.append(", paddingBottom: ");
		result.append(paddingBottom);
		result.append(", paddingFactor: ");
		result.append(paddingFactor);
		result.append(", resizeContainer: ");
		result.append(resizeContainer);
		result.append(", vGap: ");
		result.append(vGap);
		result.append(", hGap: ");
		result.append(hGap);
		result.append(", vAlign: ");
		result.append(vAlign);
		result.append(", hAlign: ");
		result.append(hAlign);
		result.append(')');
		return result.toString();
	}

} // GLayoutOptionsImpl
