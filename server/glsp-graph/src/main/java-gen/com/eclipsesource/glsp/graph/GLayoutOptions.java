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
package com.eclipsesource.glsp.graph;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>GLayout
 * Options</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.eclipsesource.glsp.graph.GLayoutOptions#getPaddingLeft <em>Padding Left</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GLayoutOptions#getPaddingRight <em>Padding Right</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GLayoutOptions#getPaddingTop <em>Padding Top</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GLayoutOptions#getPaddingBottom <em>Padding Bottom</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GLayoutOptions#getPaddingFactor <em>Padding Factor</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GLayoutOptions#isResizeContainer <em>Resize Container</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GLayoutOptions#getVGap <em>VGap</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GLayoutOptions#getHGap <em>HGap</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GLayoutOptions#getVAlign <em>VAlign</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GLayoutOptions#getHAlign <em>HAlign</em>}</li>
 * </ul>
 *
 * @see com.eclipsesource.glsp.graph.GraphPackage#getGLayoutOptions()
 * @model
 * @generated
 */
public interface GLayoutOptions extends EObject {
	/**
	 * Returns the value of the '<em><b>Padding Left</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Padding Left</em>' attribute.
	 * @see #setPaddingLeft(double)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGLayoutOptions_PaddingLeft()
	 * @model
	 * @generated
	 */
	double getPaddingLeft();

	/**
	 * Sets the value of the
	 * '{@link com.eclipsesource.glsp.graph.GLayoutOptions#getPaddingLeft
	 * <em>Padding Left</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Padding Left</em>' attribute.
	 * @see #getPaddingLeft()
	 * @generated
	 */
	void setPaddingLeft(double value);

	/**
	 * Returns the value of the '<em><b>Padding Right</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Padding Right</em>' attribute.
	 * @see #setPaddingRight(double)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGLayoutOptions_PaddingRight()
	 * @model
	 * @generated
	 */
	double getPaddingRight();

	/**
	 * Sets the value of the
	 * '{@link com.eclipsesource.glsp.graph.GLayoutOptions#getPaddingRight
	 * <em>Padding Right</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @param value the new value of the '<em>Padding Right</em>' attribute.
	 * @see #getPaddingRight()
	 * @generated
	 */
	void setPaddingRight(double value);

	/**
	 * Returns the value of the '<em><b>Padding Top</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Padding Top</em>' attribute.
	 * @see #setPaddingTop(double)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGLayoutOptions_PaddingTop()
	 * @model
	 * @generated
	 */
	double getPaddingTop();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GLayoutOptions#getPaddingTop <em>Padding Top</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Padding Top</em>' attribute.
	 * @see #getPaddingTop()
	 * @generated
	 */
	void setPaddingTop(double value);

	/**
	 * Returns the value of the '<em><b>Padding Bottom</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Padding Bottom</em>' attribute.
	 * @see #setPaddingBottom(double)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGLayoutOptions_PaddingBottom()
	 * @model
	 * @generated
	 */
	double getPaddingBottom();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GLayoutOptions#getPaddingBottom <em>Padding Bottom</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Padding Bottom</em>' attribute.
	 * @see #getPaddingBottom()
	 * @generated
	 */
	void setPaddingBottom(double value);

	/**
	 * Returns the value of the '<em><b>Padding Factor</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Padding Factor</em>' attribute.
	 * @see #setPaddingFactor(double)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGLayoutOptions_PaddingFactor()
	 * @model
	 * @generated
	 */
	double getPaddingFactor();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GLayoutOptions#getPaddingFactor <em>Padding Factor</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Padding Factor</em>' attribute.
	 * @see #getPaddingFactor()
	 * @generated
	 */
	void setPaddingFactor(double value);

	/**
	 * Returns the value of the '<em><b>Resize Container</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Resize Container</em>' attribute.
	 * @see #setResizeContainer(boolean)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGLayoutOptions_ResizeContainer()
	 * @model
	 * @generated
	 */
	boolean isResizeContainer();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GLayoutOptions#isResizeContainer <em>Resize Container</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Resize Container</em>' attribute.
	 * @see #isResizeContainer()
	 * @generated
	 */
	void setResizeContainer(boolean value);

	/**
	 * Returns the value of the '<em><b>VGap</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>VGap</em>' attribute.
	 * @see #setVGap(double)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGLayoutOptions_VGap()
	 * @model
	 * @generated
	 */
	double getVGap();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GLayoutOptions#getVGap <em>VGap</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>VGap</em>' attribute.
	 * @see #getVGap()
	 * @generated
	 */
	void setVGap(double value);

	/**
	 * Returns the value of the '<em><b>HGap</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>HGap</em>' attribute.
	 * @see #setHGap(double)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGLayoutOptions_HGap()
	 * @model
	 * @generated
	 */
	double getHGap();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GLayoutOptions#getHGap <em>HGap</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>HGap</em>' attribute.
	 * @see #getHGap()
	 * @generated
	 */
	void setHGap(double value);

	/**
	 * Returns the value of the '<em><b>VAlign</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>VAlign</em>' attribute.
	 * @see #setVAlign(String)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGLayoutOptions_VAlign()
	 * @model
	 * @generated
	 */
	String getVAlign();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GLayoutOptions#getVAlign <em>VAlign</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>VAlign</em>' attribute.
	 * @see #getVAlign()
	 * @generated
	 */
	void setVAlign(String value);

	/**
	 * Returns the value of the '<em><b>HAlign</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>HAlign</em>' attribute.
	 * @see #setHAlign(String)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGLayoutOptions_HAlign()
	 * @model
	 * @generated
	 */
	String getHAlign();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GLayoutOptions#getHAlign <em>HAlign</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>HAlign</em>' attribute.
	 * @see #getHAlign()
	 * @generated
	 */
	void setHAlign(String value);

} // GLayoutOptions
