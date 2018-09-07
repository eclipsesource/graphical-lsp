/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.example.workflow.schema;

import io.typefox.sprotty.api.SEdge;

public class WeightedEdge extends SEdge {
	public static final String TYPE = "edge:weighted";

	public WeightedEdge() {
		setType(WeightedEdge.TYPE);
	}

	private String probability;

	public String getProbability() {
		return probability;
	}

	public void setProbability(String probability) {
		this.probability = probability;
	}

}
