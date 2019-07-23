/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.example.modelserver.workflow.handler;

import org.eclipse.emf.ecore.EClass;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.CreateNodeOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ModelServerAwareModelState;
import com.eclipsesource.glsp.example.modelserver.workflow.model.ShapeUtil;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowFacade;
import com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerAccess;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationFactory;
import com.eclipsesource.modelserver.coffee.model.coffee.CoffeeFactory;
import com.eclipsesource.modelserver.coffee.model.coffee.Node;
import com.eclipsesource.modelserver.coffee.model.coffee.Workflow;

public abstract class AbstractCreateNodeHandler implements OperationHandler {

	protected String type;
	private EClass eClass;

	public AbstractCreateNodeHandler(String type, EClass eClass) {
		super();
		this.type = type;
		this.eClass = eClass;
	}

	@Override
	public Class<? extends Action> handlesActionType() {
		return CreateNodeOperationAction.class;
	}

	@Override
	public boolean handles(AbstractOperationAction action) {
		return OperationHandler.super.handles(action)
				? ((CreateNodeOperationAction) action).getElementTypeId().equals(type)
				: false;
	}

	@Override
	public String getLabel(AbstractOperationAction action) {
		return "Create node";
	}

	@Override
	public void execute(AbstractOperationAction action, GraphicalModelState modelState) {
		CreateNodeOperationAction createNodeOperationAction = (CreateNodeOperationAction) action;
		WorkflowModelServerAccess modelAccess = ModelServerAwareModelState.getModelAccess(modelState);
		WorkflowFacade workflowFacade = modelAccess.getWorkflowFacade();
		Workflow workflow = workflowFacade.getCurrentWorkflow();

		Node node = initializeNode((Node) CoffeeFactory.eINSTANCE.create(eClass), modelState);
		workflow.getNodes().add(node);

		workflowFacade.findDiagram(workflow).ifPresent(diagram -> {
			Shape shape = WfnotationFactory.eINSTANCE.createShape();
			shape.setSemanticElement(workflowFacade.createProxy(node));
			shape.setPosition(ShapeUtil.point(createNodeOperationAction.getLocation()));
			diagram.getElements().add(shape);
		});
	}

	protected Node initializeNode(Node node, GraphicalModelState modelState) {
		return node;
	}
}
