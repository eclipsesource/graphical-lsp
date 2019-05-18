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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>GModel
 * Root</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.eclipsesource.glsp.graph.GModelRoot#getCanvasBounds <em>Canvas Bounds</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GModelRoot#getRevision <em>Revision</em>}</li>
 * </ul>
 *
 * @see com.eclipsesource.glsp.graph.GraphPackage#getGModelRoot()
 * @model
 * @generated
 */
public interface GModelRoot extends GModelElement {
	/**
	 * Returns the value of the '<em><b>Canvas Bounds</b></em>' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Canvas Bounds</em>' containment reference.
	 * @see #setCanvasBounds(GBounds)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGModelRoot_CanvasBounds()
	 * @model containment="true"
	 * @generated
	 */
	GBounds getCanvasBounds();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GModelRoot#getCanvasBounds <em>Canvas Bounds</em>}' containment reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Canvas Bounds</em>' containment reference.
	 * @see #getCanvasBounds()
	 * @generated
	 */
	void setCanvasBounds(GBounds value);

	/**
	 * Returns the value of the '<em><b>Revision</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Revision</em>' attribute.
	 * @see #setRevision(String)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGModelRoot_Revision()
	 * @model
	 * @generated
	 */
	String getRevision();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GModelRoot#getRevision <em>Revision</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Revision</em>' attribute.
	 * @see #getRevision()
	 * @generated
	 */
	void setRevision(String value);

} // GModelRoot
