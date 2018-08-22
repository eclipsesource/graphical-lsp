/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.glsp.api.utils;

public class Tool {
	private String id;
	private String name;
	private String toolType;

	public Tool() {
	}

	public Tool(String id, String name, String toolType) {
		super();
		this.id = id;
		this.name = name;
		this.toolType = toolType;
	}

	String getId() {
		return id;
	}

	String getName() {
		return name;
	}

	String getToolType() {
		return toolType;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

}
