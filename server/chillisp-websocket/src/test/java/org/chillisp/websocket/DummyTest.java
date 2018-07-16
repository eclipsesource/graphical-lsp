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
package org.chillisp.websocket;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import at.tortmayr.chillisp.api.ActionMessage;
import at.tortmayr.chillisp.api.actions.RequestModelAction;
import at.tortmayr.chillisp.api.json.ActionTypeAdapter;

public class DummyTest {

	public static void main(String[] args) {
		GsonBuilder builder = new GsonBuilder();
		ActionTypeAdapter.configureGson(builder);
		Gson gson = builder.create();
		Map<String, String> options = new HashMap<>();
		options.put("needsClientLayout", "true");
		RequestModelAction action = new RequestModelAction(options);
		ActionMessage msg = new ActionMessage("test", action);
		System.out.println(gson.toJson(msg, ActionMessage.class));
	}

}
