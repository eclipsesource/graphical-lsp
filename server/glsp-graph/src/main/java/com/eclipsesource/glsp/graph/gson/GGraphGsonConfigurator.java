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
package com.eclipsesource.glsp.graph.gson;

import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GCOMPARTMENT;
import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GEDGE;
import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GGRAPH;
import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GLABEL;
import static com.eclipsesource.glsp.graph.GraphPackage.Literals.GNODE;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

import com.eclipsesource.glsp.graph.GraphFactory;
import com.eclipsesource.glsp.graph.GraphPackage;
import com.google.gson.GsonBuilder;

public class GGraphGsonConfigurator {

	public static final String DEFAULT_TYPE_ATT = "type";

	private Map<String, EClass> typeMap = new HashMap<>();

	public GGraphGsonConfigurator withDefaultTypeMap() {
		Map<String, EClass> defaultTypes = new HashMap<>();
		defaultTypes.put("graph", GGRAPH);
		defaultTypes.put("node", GNODE);
		defaultTypes.put("edge", GEDGE);
		defaultTypes.put("comp", GCOMPARTMENT);
		defaultTypes.put("label", GLABEL);
		return withTypes(defaultTypes);
	}

	public GGraphGsonConfigurator withTypes(Map<String, EClass> types) {
		typeMap.putAll(types);
		return this;
	}

	public GsonBuilder configureGsonBuilder(GsonBuilder gsonBuilder) {
		gsonBuilder.registerTypeAdapterFactory(new GModelElementTypeAdapter.Factory(typeMap));
		for (EClassifier classifier : GraphPackage.eINSTANCE.getEClassifiers()) {
			if (classifier instanceof EClass && !((EClass) classifier).isAbstract()) {
				// use implementation class instead of interface
				gsonBuilder.registerTypeAdapter(classifier.getInstanceClass(),
						new ClassBasedDeserializer(getImplementationClass(classifier)));
			}
		}
		return gsonBuilder;
	}

	private Class<? extends EObject> getImplementationClass(EClassifier classifier) {
		return GraphFactory.eINSTANCE.create((EClass) classifier).getClass();
	}

}
