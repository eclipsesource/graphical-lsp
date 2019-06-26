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
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>GEdge Placement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.eclipsesource.glsp.graph.GEdgePlacement#getPosition <em>Position</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GEdgePlacement#getOffset <em>Offset</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GEdgePlacement#getSide <em>Side</em>}</li>
 * </ul>
 *
 * @see com.eclipsesource.glsp.graph.GraphPackage#getGEdgePlacement()
 * @model
 * @generated
 */
public interface GEdgePlacement extends EObject {
	/**
	 * Returns the value of the '<em><b>Position</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Position</em>' attribute.
	 * @see #setPosition(Double)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGEdgePlacement_Position()
	 * @model default="0" required="true"
	 * @generated
	 */
	Double getPosition();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GEdgePlacement#getPosition <em>Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Position</em>' attribute.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(Double value);

	/**
	 * Returns the value of the '<em><b>Offset</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Offset</em>' attribute.
	 * @see #setOffset(Double)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGEdgePlacement_Offset()
	 * @model default="0" required="true"
	 * @generated
	 */
	Double getOffset();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GEdgePlacement#getOffset <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Offset</em>' attribute.
	 * @see #getOffset()
	 * @generated
	 */
	void setOffset(Double value);

	/**
	 * Returns the value of the '<em><b>Side</b></em>' attribute.
	 * The default value is <code>"left"</code>.
	 * The literals are from the enumeration {@link com.eclipsesource.glsp.graph.GSide}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Side</em>' attribute.
	 * @see com.eclipsesource.glsp.graph.GSide
	 * @see #setSide(GSide)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGEdgePlacement_Side()
	 * @model default="left"
	 * @generated
	 */
	GSide getSide();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GEdgePlacement#getSide <em>Side</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Side</em>' attribute.
	 * @see com.eclipsesource.glsp.graph.GSide
	 * @see #getSide()
	 * @generated
	 */
	void setSide(GSide value);

} // GEdgePlacement
