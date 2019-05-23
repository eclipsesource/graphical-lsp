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
package com.eclipsesource.glsp.example.workflow.wfgraph;

import com.eclipsesource.glsp.graph.GNode;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Activity Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.eclipsesource.glsp.example.workflow.wfgraph.ActivityNode#getNodeType <em>Node Type</em>}</li>
 * </ul>
 *
 * @see com.eclipsesource.glsp.example.workflow.wfgraph.WfgraphPackage#getActivityNode()
 * @model abstract="true"
 * @generated
 */
public interface ActivityNode extends GNode {
	/**
	 * Returns the value of the '<em><b>Node Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node Type</em>' attribute.
	 * @see #setNodeType(String)
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.WfgraphPackage#getActivityNode_NodeType()
	 * @model
	 * @generated
	 */
	String getNodeType();

	/**
	 * Sets the value of the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.ActivityNode#getNodeType <em>Node Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node Type</em>' attribute.
	 * @see #getNodeType()
	 * @generated
	 */
	void setNodeType(String value);

} // ActivityNode
