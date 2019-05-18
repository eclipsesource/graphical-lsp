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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>GModel
 * Element</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.eclipsesource.glsp.graph.GModelElement#getId <em>Id</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GModelElement#getCssClasses <em>Css Classes</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GModelElement#getChildren <em>Children</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GModelElement#getParent <em>Parent</em>}</li>
 *   <li>{@link com.eclipsesource.glsp.graph.GModelElement#getTrace <em>Trace</em>}</li>
 * </ul>
 *
 * @see com.eclipsesource.glsp.graph.GraphPackage#getGModelElement()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface GModelElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGModelElement_Id()
	 * @model id="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GModelElement#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Css Classes</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Css Classes</em>' attribute list.
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGModelElement_CssClasses()
	 * @model
	 * @generated
	 */
	EList<String> getCssClasses();

	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link com.eclipsesource.glsp.graph.GModelElement}.
	 * It is bidirectional and its opposite is '{@link com.eclipsesource.glsp.graph.GModelElement#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGModelElement_Children()
	 * @see com.eclipsesource.glsp.graph.GModelElement#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<GModelElement> getChildren();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link com.eclipsesource.glsp.graph.GModelElement#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(GModelElement)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGModelElement_Parent()
	 * @see com.eclipsesource.glsp.graph.GModelElement#getChildren
	 * @model opposite="children" transient="false"
	 * @generated
	 */
	GModelElement getParent();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GModelElement#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(GModelElement value);

	/**
	 * Returns the value of the '<em><b>Trace</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Trace</em>' attribute.
	 * @see #setTrace(String)
	 * @see com.eclipsesource.glsp.graph.GraphPackage#getGModelElement_Trace()
	 * @model
	 * @generated
	 */
	String getTrace();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.graph.GModelElement#getTrace <em>Trace</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the new value of the '<em>Trace</em>' attribute.
	 * @see #getTrace()
	 * @generated
	 */
	void setTrace(String value);

} // GModelElement
