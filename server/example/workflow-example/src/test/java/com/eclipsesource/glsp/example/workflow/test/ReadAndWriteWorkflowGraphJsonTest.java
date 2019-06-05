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
package com.eclipsesource.glsp.example.workflow.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.eclipsesource.glsp.example.workflow.WorkflowGsonConfiguratorFactory;
import com.eclipsesource.glsp.example.workflow.wfgraph.AutomatedTask;
import com.eclipsesource.glsp.example.workflow.wfgraph.Icon;
import com.eclipsesource.glsp.example.workflow.wfgraph.LabelIcon;
import com.eclipsesource.glsp.example.workflow.wfgraph.ManualTask;
import com.eclipsesource.glsp.example.workflow.wfgraph.WfgraphFactory;
import com.eclipsesource.glsp.graph.GCompartment;
import com.eclipsesource.glsp.graph.GGraph;
import com.eclipsesource.glsp.graph.GLayoutOptions;
import com.eclipsesource.glsp.graph.GModelElement;
import com.eclipsesource.glsp.graph.GPoint;
import com.eclipsesource.glsp.graph.GraphFactory;
import com.eclipsesource.glsp.graph.gson.GGraphGsonConfigurator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

class ReadAndWriteWorkflowGraphJsonTest {

	private static final String RESOURCE_PATH = "src/test/resources/";

	private GGraphGsonConfigurator gsonConfigurator;

	@BeforeEach
	void initializeGsonConfigurator() {
		gsonConfigurator = new WorkflowGsonConfiguratorFactory().create();
	}

	@Test
	void testReadingExampleWorkflowGraph() throws IOException {
		GGraph graph = (GGraph) loadResource("example.wf");

		ManualTask push = (ManualTask) getChildById(graph, "task1").get();
		assertEquals("Push", push.getName());
		assertEquals(30, push.getDuration());

		GCompartment pushHeader = (GCompartment) getChildById(push, "task1_header").get();
		Icon iconCompartement = (Icon) getChildById(pushHeader, "task1_icon").get();
		assertEquals("simulate-command", iconCompartement.getCommandId());
		assertEquals("center", iconCompartement.getLayoutOptions().getHAlign());
		assertEquals(false, iconCompartement.getLayoutOptions().isResizeContainer());

		LabelIcon labelIcon = (LabelIcon) getChildById(iconCompartement, "task1_ticon").get();
		assertEquals("M", labelIcon.getText());

		AutomatedTask chkWt = (AutomatedTask) getChildById(graph, "task2").get();
		assertEquals("ChkWt", chkWt.getName());
		assertEquals(10, chkWt.getDuration());
		assertEquals(200.0, chkWt.getPosition().getX());
		assertEquals(200.0, chkWt.getPosition().getY());
	}

	private Optional<GModelElement> getChildById(GModelElement element, String id) {
		return element.getChildren().stream().filter(e -> id.equals(e.getId())).findFirst();
	}

	private GGraph loadResource(String file) throws IOException {
		Gson gson = gsonConfigurator.configureGsonBuilder(new GsonBuilder()).create();
		JsonReader jsonReader = new JsonReader(new FileReader(RESOURCE_PATH + file));
		return gson.fromJson(jsonReader, GGraph.class);
	}

	@Test
	void testWritingExampleWorkflowGraph() throws IOException {
		GGraph graph = GraphFactory.eINSTANCE.createGGraph();
		graph.setRevision(42);
		graph.setId("graphId");

		ManualTask push = WfgraphFactory.eINSTANCE.createManualTask();
		push.setId("task1");
		push.setName("Push");
		push.setExpanded(true);
		push.setDuration(30);
		push.setTaskType("manual");
		push.setLayout("vbox");
		GPoint pos = GraphFactory.eINSTANCE.createGPoint();
		pos.setX(10);
		pos.setY(200);
		push.setPosition(pos);

		GCompartment compHeader = GraphFactory.eINSTANCE.createGCompartment();
		compHeader.setLayout("hbox");
		compHeader.setId("task1_header");

		Icon icon = WfgraphFactory.eINSTANCE.createIcon();
		icon.setLayout("stack");
		GLayoutOptions layoutOptions = GraphFactory.eINSTANCE.createGLayoutOptions();
		layoutOptions.setResizeContainer(false);
		layoutOptions.setHAlign("center");
		icon.setLayoutOptions(layoutOptions);
		compHeader.getChildren().add(icon);

		push.getChildren().add(compHeader);

		graph.getChildren().add(push);

		String expectedJson = "{"//
				+ "\"id\":\"graphId\","//
				+ "\"children\":"//
				+ "["//
				+ "{"//
				+ "\"name\":\"Push\","//
				+ "\"expanded\":true,"//
				+ "\"duration\":30,"//
				+ "\"taskType\":\"manual\","//
				+ "\"id\":\"task1\","//
				+ "\"children\":"//
				+ "["//
				+ "{"//
				+ "\"id\":\"task1_header\","//
				+ "\"children\":"//
				+ "["//
				+ "{"//
				+ "\"layout\":\"stack\","//
				+ "\"layoutOptions\":"//
				+ "{"//
				+ "\"resizeContainer\":false,"//
				+ "\"hAlign\":\"center\""//
				+ "},"//
				+ "\"type\":\"icon\""//
				+ "}"//
				+ "],"//
				+ "\"layout\":\"hbox\","//
				+ "\"type\":\"comp:header\""//
				+ "}"//
				+ "],"//
				+ "\"position\":"//
				+ "{"//
				+ "\"x\":10.0,"//
				+ "\"y\":200.0"//
				+ "},"//
				+ "\"layout\":\"vbox\","//
				+ "\"type\":\"task:manual\""//
				+ "}"//
				+ "],"//
				+ "\"revision\":42,"//
				+ "\"type\":\"graph\"}";
		JsonObject jsonGraph = writeToJson(graph).getAsJsonObject();
		assertEquals(new JsonParser().parse(expectedJson), jsonGraph);
	}

	private JsonElement writeToJson(GGraph graph) {
		Gson gson = gsonConfigurator.configureGsonBuilder(new GsonBuilder()).create();
		return gson.toJsonTree(graph);
	}

}
