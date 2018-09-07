/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Camille Letavernier - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.action.kind;

import com.eclipsesource.glsp.api.action.Action;

public class DeleteAction extends Action {
	
	private String elementId;
	
	public DeleteAction() {
		super(ActionKind.DELETE);
	}

	public DeleteAction(String sourceElement) {
		this();
		this.elementId = sourceElement;
	}

	public String getElementId() {
		return elementId;
	}
	
}
