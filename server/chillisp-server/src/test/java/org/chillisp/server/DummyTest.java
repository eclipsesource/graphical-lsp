package org.chillisp.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import at.tortmayr.chillisp.server.SModelElementTypeAdapter;
import at.tortmayr.chillisp.server.schema.TaskNode;

public class DummyTest {

	public static void main(String[] args) {
		String json=  "{\"name\": \"Push\", \"expanded\": false, \"duration\": 30, \"taskType\": \"manual\", \"layout\": \"vbox\", \"position\": {\"x\": 10.0, \"y\": 200.0 }, \"type\": \"node:task\", \"id\": \"task1\", \"children\": [{\"layout\": \"hbox\", \"type\": \"comp:header\", \"id\": \"task1_header\", \"children\": [{\"layout\": \"stack\", \"position\": {\"x\": 0.0, \"y\": 0.0 }, \"layoutOptions\": {\"resizeContainer\": false, \"hAlign\": \"center\"}, \"type\": \"icon\", \"id\": \"task1_icon\", \"children\": [{\"text\": \"M\", \"type\": \"label:icon\", \"id\": \"task1_ticon\"} ] }, {\"text\": \"Push\", \"type\": \"label:heading\", \"id\": \"task1_classname\"} ] } ] }";
		GsonBuilder builder = new GsonBuilder();
		SModelElementTypeAdapter.configureGson(builder);
		Gson gson = builder.create();
	
		Object test=gson.fromJson(json, TaskNode.class);
		System.out.println(test);
	}
}
