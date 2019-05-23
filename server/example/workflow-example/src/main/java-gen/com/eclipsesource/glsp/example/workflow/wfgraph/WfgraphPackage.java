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

import com.eclipsesource.glsp.graph.GraphPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.eclipsesource.glsp.example.workflow.wfgraph.WfgraphFactory
 * @model kind="package"
 * @generated
 */
public interface WfgraphPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "wfgraph";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipsesource.com/glsp/examples/workflow/graph";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "wfgraph";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	WfgraphPackage eINSTANCE = com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.ActivityNodeImpl <em>Activity Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.ActivityNodeImpl
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getActivityNode()
	 * @generated
	 */
	int ACTIVITY_NODE = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__ID = GraphPackage.GNODE__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__CSS_CLASSES = GraphPackage.GNODE__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__CHILDREN = GraphPackage.GNODE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__PARENT = GraphPackage.GNODE__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__TRACE = GraphPackage.GNODE__TRACE;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__POSITION = GraphPackage.GNODE__POSITION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__SIZE = GraphPackage.GNODE__SIZE;

	/**
	 * The feature id for the '<em><b>Edge Placement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__EDGE_PLACEMENT = GraphPackage.GNODE__EDGE_PLACEMENT;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__LAYOUT = GraphPackage.GNODE__LAYOUT;

	/**
	 * The feature id for the '<em><b>Layout Options</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__LAYOUT_OPTIONS = GraphPackage.GNODE__LAYOUT_OPTIONS;

	/**
	 * The feature id for the '<em><b>Node Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE__NODE_TYPE = GraphPackage.GNODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Activity Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE_FEATURE_COUNT = GraphPackage.GNODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Activity Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTIVITY_NODE_OPERATION_COUNT = GraphPackage.GNODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.DecisionNodeImpl <em>Decision Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.DecisionNodeImpl
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getDecisionNode()
	 * @generated
	 */
	int DECISION_NODE = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_NODE__ID = ACTIVITY_NODE__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_NODE__CSS_CLASSES = ACTIVITY_NODE__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_NODE__CHILDREN = ACTIVITY_NODE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_NODE__PARENT = ACTIVITY_NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_NODE__TRACE = ACTIVITY_NODE__TRACE;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_NODE__POSITION = ACTIVITY_NODE__POSITION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_NODE__SIZE = ACTIVITY_NODE__SIZE;

	/**
	 * The feature id for the '<em><b>Edge Placement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_NODE__EDGE_PLACEMENT = ACTIVITY_NODE__EDGE_PLACEMENT;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_NODE__LAYOUT = ACTIVITY_NODE__LAYOUT;

	/**
	 * The feature id for the '<em><b>Layout Options</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_NODE__LAYOUT_OPTIONS = ACTIVITY_NODE__LAYOUT_OPTIONS;

	/**
	 * The feature id for the '<em><b>Node Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_NODE__NODE_TYPE = ACTIVITY_NODE__NODE_TYPE;

	/**
	 * The number of structural features of the '<em>Decision Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_NODE_FEATURE_COUNT = ACTIVITY_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Decision Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_NODE_OPERATION_COUNT = ACTIVITY_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.MergeNodeImpl <em>Merge Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.MergeNodeImpl
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getMergeNode()
	 * @generated
	 */
	int MERGE_NODE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NODE__ID = ACTIVITY_NODE__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NODE__CSS_CLASSES = ACTIVITY_NODE__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NODE__CHILDREN = ACTIVITY_NODE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NODE__PARENT = ACTIVITY_NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NODE__TRACE = ACTIVITY_NODE__TRACE;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NODE__POSITION = ACTIVITY_NODE__POSITION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NODE__SIZE = ACTIVITY_NODE__SIZE;

	/**
	 * The feature id for the '<em><b>Edge Placement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NODE__EDGE_PLACEMENT = ACTIVITY_NODE__EDGE_PLACEMENT;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NODE__LAYOUT = ACTIVITY_NODE__LAYOUT;

	/**
	 * The feature id for the '<em><b>Layout Options</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NODE__LAYOUT_OPTIONS = ACTIVITY_NODE__LAYOUT_OPTIONS;

	/**
	 * The feature id for the '<em><b>Node Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NODE__NODE_TYPE = ACTIVITY_NODE__NODE_TYPE;

	/**
	 * The number of structural features of the '<em>Merge Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NODE_FEATURE_COUNT = ACTIVITY_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Merge Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MERGE_NODE_OPERATION_COUNT = ACTIVITY_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.TaskNodeImpl <em>Task Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.TaskNodeImpl
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getTaskNode()
	 * @generated
	 */
	int TASK_NODE = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__ID = GraphPackage.GNODE__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__CSS_CLASSES = GraphPackage.GNODE__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__CHILDREN = GraphPackage.GNODE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__PARENT = GraphPackage.GNODE__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__TRACE = GraphPackage.GNODE__TRACE;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__POSITION = GraphPackage.GNODE__POSITION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__SIZE = GraphPackage.GNODE__SIZE;

	/**
	 * The feature id for the '<em><b>Edge Placement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__EDGE_PLACEMENT = GraphPackage.GNODE__EDGE_PLACEMENT;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__LAYOUT = GraphPackage.GNODE__LAYOUT;

	/**
	 * The feature id for the '<em><b>Layout Options</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__LAYOUT_OPTIONS = GraphPackage.GNODE__LAYOUT_OPTIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__NAME = GraphPackage.GNODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expanded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__EXPANDED = GraphPackage.GNODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__DURATION = GraphPackage.GNODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Task Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__TASK_TYPE = GraphPackage.GNODE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE__REFERENCE = GraphPackage.GNODE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Task Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE_FEATURE_COUNT = GraphPackage.GNODE_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Task Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_NODE_OPERATION_COUNT = GraphPackage.GNODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.AutomatedTaskImpl <em>Automated Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.AutomatedTaskImpl
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getAutomatedTask()
	 * @generated
	 */
	int AUTOMATED_TASK = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__ID = TASK_NODE__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__CSS_CLASSES = TASK_NODE__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__CHILDREN = TASK_NODE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__PARENT = TASK_NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__TRACE = TASK_NODE__TRACE;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__POSITION = TASK_NODE__POSITION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__SIZE = TASK_NODE__SIZE;

	/**
	 * The feature id for the '<em><b>Edge Placement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__EDGE_PLACEMENT = TASK_NODE__EDGE_PLACEMENT;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__LAYOUT = TASK_NODE__LAYOUT;

	/**
	 * The feature id for the '<em><b>Layout Options</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__LAYOUT_OPTIONS = TASK_NODE__LAYOUT_OPTIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__NAME = TASK_NODE__NAME;

	/**
	 * The feature id for the '<em><b>Expanded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__EXPANDED = TASK_NODE__EXPANDED;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__DURATION = TASK_NODE__DURATION;

	/**
	 * The feature id for the '<em><b>Task Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__TASK_TYPE = TASK_NODE__TASK_TYPE;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK__REFERENCE = TASK_NODE__REFERENCE;

	/**
	 * The number of structural features of the '<em>Automated Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK_FEATURE_COUNT = TASK_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Automated Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AUTOMATED_TASK_OPERATION_COUNT = TASK_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.ManualTaskImpl <em>Manual Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.ManualTaskImpl
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getManualTask()
	 * @generated
	 */
	int MANUAL_TASK = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__ID = TASK_NODE__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__CSS_CLASSES = TASK_NODE__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__CHILDREN = TASK_NODE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__PARENT = TASK_NODE__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__TRACE = TASK_NODE__TRACE;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__POSITION = TASK_NODE__POSITION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__SIZE = TASK_NODE__SIZE;

	/**
	 * The feature id for the '<em><b>Edge Placement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__EDGE_PLACEMENT = TASK_NODE__EDGE_PLACEMENT;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__LAYOUT = TASK_NODE__LAYOUT;

	/**
	 * The feature id for the '<em><b>Layout Options</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__LAYOUT_OPTIONS = TASK_NODE__LAYOUT_OPTIONS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__NAME = TASK_NODE__NAME;

	/**
	 * The feature id for the '<em><b>Expanded</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__EXPANDED = TASK_NODE__EXPANDED;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__DURATION = TASK_NODE__DURATION;

	/**
	 * The feature id for the '<em><b>Task Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__TASK_TYPE = TASK_NODE__TASK_TYPE;

	/**
	 * The feature id for the '<em><b>Reference</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK__REFERENCE = TASK_NODE__REFERENCE;

	/**
	 * The number of structural features of the '<em>Manual Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK_FEATURE_COUNT = TASK_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Manual Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MANUAL_TASK_OPERATION_COUNT = TASK_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.IconImpl <em>Icon</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.IconImpl
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getIcon()
	 * @generated
	 */
	int ICON = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__ID = GraphPackage.GCOMPARTMENT__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__CSS_CLASSES = GraphPackage.GCOMPARTMENT__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__CHILDREN = GraphPackage.GCOMPARTMENT__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__PARENT = GraphPackage.GCOMPARTMENT__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__TRACE = GraphPackage.GCOMPARTMENT__TRACE;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__POSITION = GraphPackage.GCOMPARTMENT__POSITION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__SIZE = GraphPackage.GCOMPARTMENT__SIZE;

	/**
	 * The feature id for the '<em><b>Layout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__LAYOUT = GraphPackage.GCOMPARTMENT__LAYOUT;

	/**
	 * The feature id for the '<em><b>Layout Options</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__LAYOUT_OPTIONS = GraphPackage.GCOMPARTMENT__LAYOUT_OPTIONS;

	/**
	 * The feature id for the '<em><b>Command Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON__COMMAND_ID = GraphPackage.GCOMPARTMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Icon</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON_FEATURE_COUNT = GraphPackage.GCOMPARTMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Icon</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICON_OPERATION_COUNT = GraphPackage.GCOMPARTMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.WeightedEdgeImpl <em>Weighted Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WeightedEdgeImpl
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getWeightedEdge()
	 * @generated
	 */
	int WEIGHTED_EDGE = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__ID = GraphPackage.GEDGE__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__CSS_CLASSES = GraphPackage.GEDGE__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__CHILDREN = GraphPackage.GEDGE__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__PARENT = GraphPackage.GEDGE__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__TRACE = GraphPackage.GEDGE__TRACE;

	/**
	 * The feature id for the '<em><b>Routing Points</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__ROUTING_POINTS = GraphPackage.GEDGE__ROUTING_POINTS;

	/**
	 * The feature id for the '<em><b>Source Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__SOURCE_ID = GraphPackage.GEDGE__SOURCE_ID;

	/**
	 * The feature id for the '<em><b>Target Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__TARGET_ID = GraphPackage.GEDGE__TARGET_ID;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__SOURCE = GraphPackage.GEDGE__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__TARGET = GraphPackage.GEDGE__TARGET;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE__PROBABILITY = GraphPackage.GEDGE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Weighted Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE_FEATURE_COUNT = GraphPackage.GEDGE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Weighted Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIGHTED_EDGE_OPERATION_COUNT = GraphPackage.GEDGE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.LabelHeadingImpl <em>Label Heading</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.LabelHeadingImpl
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getLabelHeading()
	 * @generated
	 */
	int LABEL_HEADING = 8;

	/**
	 * The feature id for the '<em><b>Alignment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_HEADING__ALIGNMENT = GraphPackage.GLABEL__ALIGNMENT;

	/**
	 * The feature id for the '<em><b>Edge Placement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_HEADING__EDGE_PLACEMENT = GraphPackage.GLABEL__EDGE_PLACEMENT;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_HEADING__ID = GraphPackage.GLABEL__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_HEADING__CSS_CLASSES = GraphPackage.GLABEL__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_HEADING__CHILDREN = GraphPackage.GLABEL__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_HEADING__PARENT = GraphPackage.GLABEL__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_HEADING__TRACE = GraphPackage.GLABEL__TRACE;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_HEADING__POSITION = GraphPackage.GLABEL__POSITION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_HEADING__SIZE = GraphPackage.GLABEL__SIZE;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_HEADING__TEXT = GraphPackage.GLABEL__TEXT;

	/**
	 * The number of structural features of the '<em>Label Heading</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_HEADING_FEATURE_COUNT = GraphPackage.GLABEL_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Label Heading</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_HEADING_OPERATION_COUNT = GraphPackage.GLABEL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.LabelTextImpl <em>Label Text</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.LabelTextImpl
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getLabelText()
	 * @generated
	 */
	int LABEL_TEXT = 9;

	/**
	 * The feature id for the '<em><b>Alignment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_TEXT__ALIGNMENT = GraphPackage.GLABEL__ALIGNMENT;

	/**
	 * The feature id for the '<em><b>Edge Placement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_TEXT__EDGE_PLACEMENT = GraphPackage.GLABEL__EDGE_PLACEMENT;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_TEXT__ID = GraphPackage.GLABEL__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_TEXT__CSS_CLASSES = GraphPackage.GLABEL__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_TEXT__CHILDREN = GraphPackage.GLABEL__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_TEXT__PARENT = GraphPackage.GLABEL__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_TEXT__TRACE = GraphPackage.GLABEL__TRACE;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_TEXT__POSITION = GraphPackage.GLABEL__POSITION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_TEXT__SIZE = GraphPackage.GLABEL__SIZE;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_TEXT__TEXT = GraphPackage.GLABEL__TEXT;

	/**
	 * The number of structural features of the '<em>Label Text</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_TEXT_FEATURE_COUNT = GraphPackage.GLABEL_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Label Text</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_TEXT_OPERATION_COUNT = GraphPackage.GLABEL_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.LabelIconImpl <em>Label Icon</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.LabelIconImpl
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getLabelIcon()
	 * @generated
	 */
	int LABEL_ICON = 10;

	/**
	 * The feature id for the '<em><b>Alignment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ICON__ALIGNMENT = GraphPackage.GLABEL__ALIGNMENT;

	/**
	 * The feature id for the '<em><b>Edge Placement</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ICON__EDGE_PLACEMENT = GraphPackage.GLABEL__EDGE_PLACEMENT;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ICON__ID = GraphPackage.GLABEL__ID;

	/**
	 * The feature id for the '<em><b>Css Classes</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ICON__CSS_CLASSES = GraphPackage.GLABEL__CSS_CLASSES;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ICON__CHILDREN = GraphPackage.GLABEL__CHILDREN;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ICON__PARENT = GraphPackage.GLABEL__PARENT;

	/**
	 * The feature id for the '<em><b>Trace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ICON__TRACE = GraphPackage.GLABEL__TRACE;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ICON__POSITION = GraphPackage.GLABEL__POSITION;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ICON__SIZE = GraphPackage.GLABEL__SIZE;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ICON__TEXT = GraphPackage.GLABEL__TEXT;

	/**
	 * The number of structural features of the '<em>Label Icon</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ICON_FEATURE_COUNT = GraphPackage.GLABEL_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Label Icon</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LABEL_ICON_OPERATION_COUNT = GraphPackage.GLABEL_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.glsp.example.workflow.wfgraph.ActivityNode <em>Activity Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Activity Node</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.ActivityNode
	 * @generated
	 */
	EClass getActivityNode();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.glsp.example.workflow.wfgraph.ActivityNode#getNodeType <em>Node Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Node Type</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.ActivityNode#getNodeType()
	 * @see #getActivityNode()
	 * @generated
	 */
	EAttribute getActivityNode_NodeType();

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.glsp.example.workflow.wfgraph.DecisionNode <em>Decision Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Decision Node</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.DecisionNode
	 * @generated
	 */
	EClass getDecisionNode();

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.glsp.example.workflow.wfgraph.MergeNode <em>Merge Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Merge Node</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.MergeNode
	 * @generated
	 */
	EClass getMergeNode();

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode <em>Task Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Node</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode
	 * @generated
	 */
	EClass getTaskNode();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode#getName()
	 * @see #getTaskNode()
	 * @generated
	 */
	EAttribute getTaskNode_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode#isExpanded <em>Expanded</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expanded</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode#isExpanded()
	 * @see #getTaskNode()
	 * @generated
	 */
	EAttribute getTaskNode_Expanded();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode#getDuration <em>Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Duration</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode#getDuration()
	 * @see #getTaskNode()
	 * @generated
	 */
	EAttribute getTaskNode_Duration();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode#getTaskType <em>Task Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Task Type</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode#getTaskType()
	 * @see #getTaskNode()
	 * @generated
	 */
	EAttribute getTaskNode_TaskType();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode#getReference <em>Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reference</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode#getReference()
	 * @see #getTaskNode()
	 * @generated
	 */
	EAttribute getTaskNode_Reference();

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.glsp.example.workflow.wfgraph.AutomatedTask <em>Automated Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Automated Task</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.AutomatedTask
	 * @generated
	 */
	EClass getAutomatedTask();

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.glsp.example.workflow.wfgraph.ManualTask <em>Manual Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Manual Task</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.ManualTask
	 * @generated
	 */
	EClass getManualTask();

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.glsp.example.workflow.wfgraph.Icon <em>Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Icon</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.Icon
	 * @generated
	 */
	EClass getIcon();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.glsp.example.workflow.wfgraph.Icon#getCommandId <em>Command Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Command Id</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.Icon#getCommandId()
	 * @see #getIcon()
	 * @generated
	 */
	EAttribute getIcon_CommandId();

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.glsp.example.workflow.wfgraph.WeightedEdge <em>Weighted Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Weighted Edge</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.WeightedEdge
	 * @generated
	 */
	EClass getWeightedEdge();

	/**
	 * Returns the meta object for the attribute '{@link com.eclipsesource.glsp.example.workflow.wfgraph.WeightedEdge#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Probability</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.WeightedEdge#getProbability()
	 * @see #getWeightedEdge()
	 * @generated
	 */
	EAttribute getWeightedEdge_Probability();

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.glsp.example.workflow.wfgraph.LabelHeading <em>Label Heading</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Label Heading</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.LabelHeading
	 * @generated
	 */
	EClass getLabelHeading();

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.glsp.example.workflow.wfgraph.LabelText <em>Label Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Label Text</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.LabelText
	 * @generated
	 */
	EClass getLabelText();

	/**
	 * Returns the meta object for class '{@link com.eclipsesource.glsp.example.workflow.wfgraph.LabelIcon <em>Label Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Label Icon</em>'.
	 * @see com.eclipsesource.glsp.example.workflow.wfgraph.LabelIcon
	 * @generated
	 */
	EClass getLabelIcon();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	WfgraphFactory getWfgraphFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.ActivityNodeImpl <em>Activity Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.ActivityNodeImpl
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getActivityNode()
		 * @generated
		 */
		EClass ACTIVITY_NODE = eINSTANCE.getActivityNode();

		/**
		 * The meta object literal for the '<em><b>Node Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTIVITY_NODE__NODE_TYPE = eINSTANCE.getActivityNode_NodeType();

		/**
		 * The meta object literal for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.DecisionNodeImpl <em>Decision Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.DecisionNodeImpl
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getDecisionNode()
		 * @generated
		 */
		EClass DECISION_NODE = eINSTANCE.getDecisionNode();

		/**
		 * The meta object literal for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.MergeNodeImpl <em>Merge Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.MergeNodeImpl
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getMergeNode()
		 * @generated
		 */
		EClass MERGE_NODE = eINSTANCE.getMergeNode();

		/**
		 * The meta object literal for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.TaskNodeImpl <em>Task Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.TaskNodeImpl
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getTaskNode()
		 * @generated
		 */
		EClass TASK_NODE = eINSTANCE.getTaskNode();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_NODE__NAME = eINSTANCE.getTaskNode_Name();

		/**
		 * The meta object literal for the '<em><b>Expanded</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_NODE__EXPANDED = eINSTANCE.getTaskNode_Expanded();

		/**
		 * The meta object literal for the '<em><b>Duration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_NODE__DURATION = eINSTANCE.getTaskNode_Duration();

		/**
		 * The meta object literal for the '<em><b>Task Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_NODE__TASK_TYPE = eINSTANCE.getTaskNode_TaskType();

		/**
		 * The meta object literal for the '<em><b>Reference</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK_NODE__REFERENCE = eINSTANCE.getTaskNode_Reference();

		/**
		 * The meta object literal for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.AutomatedTaskImpl <em>Automated Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.AutomatedTaskImpl
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getAutomatedTask()
		 * @generated
		 */
		EClass AUTOMATED_TASK = eINSTANCE.getAutomatedTask();

		/**
		 * The meta object literal for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.ManualTaskImpl <em>Manual Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.ManualTaskImpl
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getManualTask()
		 * @generated
		 */
		EClass MANUAL_TASK = eINSTANCE.getManualTask();

		/**
		 * The meta object literal for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.IconImpl <em>Icon</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.IconImpl
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getIcon()
		 * @generated
		 */
		EClass ICON = eINSTANCE.getIcon();

		/**
		 * The meta object literal for the '<em><b>Command Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ICON__COMMAND_ID = eINSTANCE.getIcon_CommandId();

		/**
		 * The meta object literal for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.WeightedEdgeImpl <em>Weighted Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WeightedEdgeImpl
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getWeightedEdge()
		 * @generated
		 */
		EClass WEIGHTED_EDGE = eINSTANCE.getWeightedEdge();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WEIGHTED_EDGE__PROBABILITY = eINSTANCE.getWeightedEdge_Probability();

		/**
		 * The meta object literal for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.LabelHeadingImpl <em>Label Heading</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.LabelHeadingImpl
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getLabelHeading()
		 * @generated
		 */
		EClass LABEL_HEADING = eINSTANCE.getLabelHeading();

		/**
		 * The meta object literal for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.LabelTextImpl <em>Label Text</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.LabelTextImpl
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getLabelText()
		 * @generated
		 */
		EClass LABEL_TEXT = eINSTANCE.getLabelText();

		/**
		 * The meta object literal for the '{@link com.eclipsesource.glsp.example.workflow.wfgraph.impl.LabelIconImpl <em>Label Icon</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.LabelIconImpl
		 * @see com.eclipsesource.glsp.example.workflow.wfgraph.impl.WfgraphPackageImpl#getLabelIcon()
		 * @generated
		 */
		EClass LABEL_ICON = eINSTANCE.getLabelIcon();

	}

} //WfgraphPackage
