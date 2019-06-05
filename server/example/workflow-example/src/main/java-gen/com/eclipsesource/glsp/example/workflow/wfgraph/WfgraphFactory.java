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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.eclipsesource.glsp.example.workflow.wfgraph.WfgraphPackage
 * @generated
 */
public interface WfgraphFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	WfgraphFactory eINSTANCE = com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Decision Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Decision Node</em>'.
	 * @generated
	 */
	DecisionNode createDecisionNode();

	/**
	 * Returns a new object of class '<em>Merge Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Merge Node</em>'.
	 * @generated
	 */
	MergeNode createMergeNode();

	/**
	 * Returns a new object of class '<em>Task Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Task Node</em>'.
	 * @generated
	 */
	TaskNode createTaskNode();

	/**
	 * Returns a new object of class '<em>Automated Task</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Automated Task</em>'.
	 * @generated
	 */
	AutomatedTask createAutomatedTask();

	/**
	 * Returns a new object of class '<em>Manual Task</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Manual Task</em>'.
	 * @generated
	 */
	ManualTask createManualTask();

	/**
	 * Returns a new object of class '<em>Icon</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Icon</em>'.
	 * @generated
	 */
	Icon createIcon();

	/**
	 * Returns a new object of class '<em>Weighted Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Weighted Edge</em>'.
	 * @generated
	 */
	WeightedEdge createWeightedEdge();

	/**
	 * Returns a new object of class '<em>Label Heading</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Label Heading</em>'.
	 * @generated
	 */
	LabelHeading createLabelHeading();

	/**
	 * Returns a new object of class '<em>Label Text</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Label Text</em>'.
	 * @generated
	 */
	LabelText createLabelText();

	/**
	 * Returns a new object of class '<em>Label Icon</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Label Icon</em>'.
	 * @generated
	 */
	LabelIcon createLabelIcon();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	WfgraphPackage getWfgraphPackage();

} //WfgraphFactory
