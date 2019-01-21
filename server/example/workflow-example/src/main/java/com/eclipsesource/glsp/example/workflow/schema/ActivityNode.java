/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.example.workflow.schema;

import org.eclipse.sprotty.SNode;

public class ActivityNode extends SNode {
	public static final String BASE_TYPE = "activityNode";
	
	public ActivityNode() {
		setType(ActivityNode.BASE_TYPE);
	}

	private String nodeType;

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
		setType(BASE_TYPE+":"+nodeType);
	}

}
