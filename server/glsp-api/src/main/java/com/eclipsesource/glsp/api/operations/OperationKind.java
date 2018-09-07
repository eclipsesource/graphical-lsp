/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.operations;

public class OperationKind {
	
	public static final String CREATE_NODE = "create-node";
	public static final String CREATE_CONNECTION = "create-connection";
	public static final String DELETE_ELEMENT = "delete-node";
	public static final String MOVE = "move";
	
	private OperationKind() {
		// prevent instantiation for class only holding constants.
	}
	
}
