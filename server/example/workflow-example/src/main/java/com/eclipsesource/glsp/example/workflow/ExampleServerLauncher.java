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
package com.eclipsesource.glsp.example.workflow;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.BasicConfigurator;

import com.eclipsesource.glsp.server.ServerLauncher;


public class ExampleServerLauncher {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		ServerLauncher launcher=new ServerLauncher("localhost", 5007, new WorkflowServerRuntimeModule());
		try {
			launcher.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
