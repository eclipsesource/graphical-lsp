package com.eclipsesource.glsp.example.workflow;

import java.util.Arrays;
import java.util.List;

import com.eclipsesource.glsp.server.model.JSONSModelLoader;

public class WorkflowModelLoader extends JSONSModelLoader {

	@Override
	public List<String> getExtensions() {
		return Arrays.asList(".wf");
	}

}
