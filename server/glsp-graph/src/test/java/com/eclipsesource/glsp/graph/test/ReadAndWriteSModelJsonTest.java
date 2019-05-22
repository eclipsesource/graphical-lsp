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
package com.eclipsesource.glsp.graph.test;

import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GEDGE;
import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GGRAPH;
import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GNODE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.eclipsesource.glsp.graph.GEdge;
import com.eclipsesource.glsp.graph.GGraph;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.glsp.graph.gson.GGraphGsonConfigurator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

class ReadAndWriteSModelJsonTest {

	private static final String RESOURCE_PATH = "src/test/resources/";

	private GGraphGsonConfigurator gsonConfigurator;

	@BeforeEach
	void initializeGsonConfigurator() {
		gsonConfigurator = new GGraphGsonConfigurator().withDefaultTypeMap();
	}

	@Test
	void testLoadingGraphWithCustomTypeMap() throws IOException {
		Map<String, EClass> customTypes = new HashMap<>();
		customTypes.put("mygraph", GGRAPH);
		customTypes.put("mynode", GNODE);
		customTypes.put("myedge", GEDGE);
		gsonConfigurator.withTypes(customTypes);

		GGraph graph = (GGraph) loadResource("graphWithCustomTypeMap.graph");
		assertTrue(graph.getChildren().get(0) instanceof GNode);
		assertTrue(graph.getChildren().get(1) instanceof GEdge);
	}

	@Test
	void testLoadingGraphWithTwoNodesAndOneEdge() throws IOException {
		GGraph graph = loadResource("graphWithTwoNodesAndOneEdge.graph");
		assertEquals(42, graph.getRevision());
		assertEquals("graphId", graph.getId());
		assertEquals(3, graph.getChildren().size());

		GNode node1 = (GNode) graph.getChildren().get(0);
		assertEquals("node1", node1.getId());
		assertEquals(10.0, node1.getPosition().getX());
		assertEquals(20.0, node1.getPosition().getY());

		GNode node2 = (GNode) graph.getChildren().get(1);
		assertEquals("node2", node2.getId());
		assertEquals(30.0, node2.getPosition().getX());
		assertEquals(40.0, node2.getPosition().getY());

		GEdge edge = (GEdge) graph.getChildren().get(2);
		assertEquals("edge12", edge.getId());
		assertEquals("node1", edge.getSourceId());
		assertEquals("node2", edge.getTargetId());
	}

	private GGraph loadResource(String file) throws IOException {
		Gson gson = gsonConfigurator.configureGsonBuilder(new GsonBuilder()).create();
		JsonReader jsonReader = new JsonReader(new FileReader(RESOURCE_PATH + file));
		return gson.fromJson(jsonReader, GGraph.class);
	}

}
