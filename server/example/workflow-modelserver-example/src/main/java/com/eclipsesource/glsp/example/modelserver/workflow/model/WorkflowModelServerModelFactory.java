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
package com.eclipsesource.glsp.example.modelserver.workflow.model;

import static com.eclipsesource.glsp.api.jsonrpc.GLSPServerException.getOrThrow;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.eclipsesource.glsp.api.action.ActionProcessor;
import com.eclipsesource.glsp.api.action.kind.RequestModelAction;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.utils.ClientOptions;
import com.eclipsesource.glsp.example.modelserver.workflow.ModelServerClientProvider;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Edge;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape;
import com.eclipsesource.glsp.example.workflow.utils.WorkflowBuilder.ActivityNodeBuilder;
import com.eclipsesource.glsp.example.workflow.utils.WorkflowBuilder.TaskNodeBuilder;
import com.eclipsesource.glsp.example.workflow.utils.WorkflowBuilder.WeightedEdgeBuilder;
import com.eclipsesource.glsp.example.workflow.wfgraph.ActivityNode;
import com.eclipsesource.glsp.example.workflow.wfgraph.TaskNode;
import com.eclipsesource.glsp.example.workflow.wfgraph.WeightedEdge;
import com.eclipsesource.glsp.graph.DefaultTypes;
import com.eclipsesource.glsp.graph.GEdge;
import com.eclipsesource.glsp.graph.GModelRoot;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.glsp.graph.builder.impl.GEdgeBuilder;
import com.eclipsesource.glsp.graph.builder.impl.GGraphBuilder;
import com.eclipsesource.modelserver.client.ModelServerClient;
import com.eclipsesource.modelserver.coffee.model.coffee.Flow;
import com.eclipsesource.modelserver.coffee.model.coffee.Machine;
import com.eclipsesource.modelserver.coffee.model.coffee.Node;
import com.eclipsesource.modelserver.coffee.model.coffee.Task;
import com.eclipsesource.modelserver.coffee.model.coffee.WeightedFlow;
import com.eclipsesource.modelserver.coffee.model.coffee.Workflow;
import com.eclipsesource.modelserver.edit.CommandCodec;
import com.google.inject.Inject;

public class WorkflowModelServerModelFactory implements ModelFactory {
	private static Logger LOGGER = Logger.getLogger(WorkflowModelServerModelFactory.class);
	private static final String ROOT_ID = "sprotty";
	public static final String OPTION_WORKFLOW_INDEX = "workflowIndex";
	public static final int WORKFLOW_INDEX_DEFAULT = 0;

	@Inject
	private ModelServerClientProvider modelServerClientProvider;

	@Inject
	private ActionProcessor actionProcessor;

	@Inject
	private AdapterFactory adapterFactory;

	@Inject
	private CommandCodec commandCodec;

	@Override
	public GModelRoot loadModel(RequestModelAction action, GraphicalModelState modelState) {
		// 1. Load models and create workflow facade
		Optional<String> sourceURI = ClientOptions.getValue(action.getOptions(), ClientOptions.SOURCE_URI);
		if (sourceURI.isEmpty()) {
			LOGGER.error("No source uri given to load model, return empty model.");
			return createEmptyRoot();
		}
		Optional<ModelServerClient> modelServerClient = modelServerClientProvider.get();
		if (modelServerClient.isEmpty()) {
			LOGGER.error("Connection to modelserver has not been initialized, return empty model");
			return createEmptyRoot();
		}

		WorkflowModelServerAccess modelAccess = new WorkflowModelServerAccess(sourceURI.get(), modelServerClient.get(),
				adapterFactory, commandCodec);
		modelAccess.subscribe(new WorkflowModelServerSubscriptionListener(modelState, modelAccess, actionProcessor));

		if (modelState instanceof ModelServerAwareModelState) {
			((ModelServerAwareModelState) modelState).setModelAccess(modelAccess);
		}

		WorkflowFacade workflowFacade = modelAccess.getWorkflowFacade();
		if (workflowFacade == null) {
			return createEmptyRoot();
		}

		// 2. Check if request model action can be fulfilled
		Optional<Integer> givenWorkflowIndex = ClientOptions.getIntValue(action.getOptions(), OPTION_WORKFLOW_INDEX);
		int workflowIndex = givenWorkflowIndex.orElse(WORKFLOW_INDEX_DEFAULT);
		if (givenWorkflowIndex.isEmpty()) {
			LOGGER.warn("No workflow index given to create model, use workflow with index: " + workflowIndex);
		}
		Optional<Machine> machine = workflowFacade.getMachine();
		if (machine.isEmpty() || workflowIndex < 0 || machine.get().getWorkflows().size() <= workflowIndex) {
			LOGGER.error("No workflow with index " + workflowIndex + " in " + machine + ", return empty model.");
			return createEmptyRoot();
		}

		// 3. Set current workflow
		workflowFacade.setCurrentWorkflowIndex(workflowIndex);
		MappedGModelRoot mappedGModelRoot = populate(workflowFacade, modelState, true);
		modelAccess.setNodeMapping(mappedGModelRoot.getMapping());

		return mappedGModelRoot.getRoot();
	}

	public static MappedGModelRoot populate(WorkflowFacade workflowFacade, GraphicalModelState modelState,
			boolean intial) {
		Workflow workflow = workflowFacade.getCurrentWorkflow();

		GModelRoot root = createEmptyRoot();
		modelState.setRoot(root);
		if (intial) {
			for (TreeIterator<EObject> iterator = workflow.eAllContents(); iterator.hasNext();) {
				EObject semanticElement = iterator.next();
				GModelIdAdpater.addGModelIdAdpater(semanticElement);
			}
		}
		workflowFacade.initializeNotation(workflow);

		Map<Node, GNode> nodeMapping = new HashMap<>();
		for (Node node : workflow.getNodes()) {
			workflowFacade.findDiagramElement(node, Shape.class) //
					.flatMap(shape -> toGNode(node, shape, modelState)) //
					.ifPresent(gnode -> {
						nodeMapping.put(node, gnode);
						root.getChildren().add(gnode);
					});
		}

		for (Flow flow : workflow.getFlows()) {
			workflowFacade.findDiagramElement(flow, Edge.class)
					.flatMap(edge -> toGEdge(flow, edge, nodeMapping, modelState)) //
					.ifPresent(root.getChildren()::add);
		}
		return new MappedGModelRoot(root, nodeMapping);
	}

	private static GModelRoot createEmptyRoot() {
		return new GGraphBuilder(DefaultTypes.GRAPH)//
				.id(ROOT_ID) //
				.build();
	}

	private static Optional<GEdge> toGEdge(Flow flow, Edge edge, Map<Node, GNode> nodeMapping,
			GraphicalModelState modelState) {
		String gModelId = getOrThrow(GModelIdAdpater.getGModelId(flow),
				"Could not retrieve GModel id for semanatic element: " + flow);
		GEdge gedge = flow instanceof WeightedFlow
				? createWeightedEdge((WeightedFlow) flow, edge, nodeMapping, modelState, gModelId)
				: createEdge(flow, edge, nodeMapping, modelState, gModelId);
		return Optional.ofNullable(gedge);
	}

	private static Optional<GNode> toGNode(Node node, DiagramElement shape, GraphicalModelState modelState) {
		String gModelId = getOrThrow(GModelIdAdpater.getGModelId(node),
				"Could not retrieve GModel id for semanatic element: " + node);
		GNode gnode = node instanceof Task //
				? createTaskNode((Task) node, (Shape) shape, modelState, gModelId)
				: createActivityNode(node, (Shape) shape, modelState, gModelId);
		return Optional.ofNullable(gnode);
	}

	private static WeightedEdge createWeightedEdge(WeightedFlow flow, Edge edge, Map<Node, GNode> nodeMapping,
			GraphicalModelState modelState, String gModelId) {
		WeightedEdgeBuilder builder = new WeightedEdgeBuilder() //
				.id(gModelId)//
				.probability(flow.getProbability().getName()) //
				.source(nodeMapping.get(flow.getSource())) //
				.target(nodeMapping.get(flow.getTarget()));

		edge.getBendPoints().forEach(bendPoint -> builder.addRoutingPoint(bendPoint.getX(), bendPoint.getY()));
		return builder.build();
	}

	private static GEdge createEdge(Flow flow, Edge edge, Map<Node, GNode> nodeMapping,
			GraphicalModelState modelState, String gModelId) {
		GEdgeBuilder builder = new GEdgeBuilder()
				.id(gModelId)//
				.source(nodeMapping.get(flow.getSource()))//
				.target(nodeMapping.get(flow.getTarget()));
		edge.getBendPoints().forEach(bendPoint -> builder.addRoutingPoint(bendPoint.getX(), bendPoint.getY()));
		return builder.build();
	}

	private static TaskNode createTaskNode(Task task, Shape shape, GraphicalModelState modelState, String gModelId) {
		String type = CoffeeTypeUtil.toType(task);
		String nodeType = CoffeeTypeUtil.toNodeType(task);
		TaskNodeBuilder builder = new TaskNodeBuilder(type, task.getName(), nodeType, task.getDuration())
				.id(gModelId);
		if (shape.getPosition() != null) {
			builder.position(shape.getPosition().getX(), shape.getPosition().getY());
		}
		if (shape.getSize() != null) {
			builder.size(shape.getSize().getWidth(), shape.getSize().getHeight());
		}
		return builder.build();
	}

	private static ActivityNode createActivityNode(Node node, Shape shape, GraphicalModelState modelState,
			String gModelId) {
		String type = CoffeeTypeUtil.toType(node);
		String nodeType = CoffeeTypeUtil.toNodeType(node);
		ActivityNodeBuilder builder = new ActivityNodeBuilder(type, nodeType).id(gModelId);
		if (shape.getPosition() != null) {
			builder.position(shape.getPosition().getX(), shape.getPosition().getY());
		}
		if (shape.getSize() != null) {
			builder.size(shape.getSize().getWidth(), shape.getSize().getHeight());
		}
		return builder.build();
	}

}
