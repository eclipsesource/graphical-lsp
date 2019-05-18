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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>GEdge</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.eclipsesource.glsp.graph.GEdge#getSourceId <em>Source Id</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GEdge#getTargetId <em>Target Id</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GEdge#getRoutingPoints <em>Routing Points</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GEdge#getSource <em>Source</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GEdge#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see com.eclipsesource.glsp.graph.GraphPackage#getGEdge()
 * @model
 * @generated
 */
public interface GEdge extends GModelElement {
	/**
	 * Returns the value of the '<em><b>Source Id</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Source Id</em>' attribute.
	 * @see #setSourceId(String)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGEdge_SourceId()
	 * @model derived="true"
	 * @generated
	 */
	String getSourceId();

	/**
	 * Returns the value of the '<em><b>Target Id</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Target Id</em>' attribute.
	 * @see #setTargetId(String)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGEdge_TargetId()
	 * @model derived="true"
	 * @generated
	 */
	String getTargetId();

	/**
	 * Returns the value of the '<em><b>Routing Points</b></em>' containment reference list.
	 * The list contents are of type {@link com.eclipsesource.glsp.graph.GPoint}.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @return the value of the '<em>Routing Points</em>' containment reference list.
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGEdge_RoutingPoints()
	 * @model containment="true"
	 * @generated
	 */
	EList<GPoint> getRoutingPoints();

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(GModelElement)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGEdge_Source()
	 * @model
	 * @generated
	 */
	GModelElement getSource();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GEdge#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(GModelElement value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Target</em>' reference.
	 * @see #setTarget(GModelElement)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGEdge_Target()
	 * @model
	 * @generated
	 */
	GModelElement getTarget();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GEdge#getTarget <em>Target</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(GModelElement value);

} // GEdge
