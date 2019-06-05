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
package com.eclipsesource.glsp.example.workflow.wfgraph.impl;

import com.eclipsesource.glsp.example.workflow.wfgraph.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class WfgraphFactoryImpl extends EFactoryImpl implements WfgraphFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static WfgraphFactory init() {
		try {
			WfgraphFactory theWfgraphFactory = (WfgraphFactory) EPackage.Registry.INSTANCE
					.getEFactory(WfgraphPackage.eNS_URI);
			if (theWfgraphFactory != null) {
				return theWfgraphFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new WfgraphFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WfgraphFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case WfgraphPackage.DECISION_NODE:
			return createDecisionNode();
		case WfgraphPackage.MERGE_NODE:
			return createMergeNode();
		case WfgraphPackage.TASK_NODE:
			return createTaskNode();
		case WfgraphPackage.AUTOMATED_TASK:
			return createAutomatedTask();
		case WfgraphPackage.MANUAL_TASK:
			return createManualTask();
		case WfgraphPackage.ICON:
			return createIcon();
		case WfgraphPackage.WEIGHTED_EDGE:
			return createWeightedEdge();
		case WfgraphPackage.LABEL_HEADING:
			return createLabelHeading();
		case WfgraphPackage.LABEL_TEXT:
			return createLabelText();
		case WfgraphPackage.LABEL_ICON:
			return createLabelIcon();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DecisionNode createDecisionNode() {
		DecisionNodeImpl decisionNode = new DecisionNodeImpl();
		return decisionNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MergeNode createMergeNode() {
		MergeNodeImpl mergeNode = new MergeNodeImpl();
		return mergeNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public TaskNode createTaskNode() {
		TaskNodeImpl taskNode = new TaskNodeImpl();
		return taskNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AutomatedTask createAutomatedTask() {
		AutomatedTaskImpl automatedTask = new AutomatedTaskImpl();
		return automatedTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ManualTask createManualTask() {
		ManualTaskImpl manualTask = new ManualTaskImpl();
		return manualTask;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Icon createIcon() {
		IconImpl icon = new IconImpl();
		return icon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public WeightedEdge createWeightedEdge() {
		WeightedEdgeImpl weightedEdge = new WeightedEdgeImpl();
		return weightedEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LabelHeading createLabelHeading() {
		LabelHeadingImpl labelHeading = new LabelHeadingImpl();
		return labelHeading;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LabelText createLabelText() {
		LabelTextImpl labelText = new LabelTextImpl();
		return labelText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public LabelIcon createLabelIcon() {
		LabelIconImpl labelIcon = new LabelIconImpl();
		return labelIcon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public WfgraphPackage getWfgraphPackage() {
		return (WfgraphPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static WfgraphPackage getPackage() {
		return WfgraphPackage.eINSTANCE;
	}

} //WfgraphFactoryImpl
