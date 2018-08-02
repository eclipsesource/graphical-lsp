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
package org.chillisp.server;

import at.tortmayr.glsp.api.utils.Tool;
import at.tortmayr.glsp.example.workflow.tools.AutomatedTaskTool;

public class DummyTest {

	public static void main(String[] args) {
		Tool tool= new AutomatedTaskTool();
		System.out.println(tool.getId());
	}
}
