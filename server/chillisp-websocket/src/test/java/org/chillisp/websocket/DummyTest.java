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
