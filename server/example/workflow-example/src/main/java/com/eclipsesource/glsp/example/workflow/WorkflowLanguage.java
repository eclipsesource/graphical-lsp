package com.eclipsesource.glsp.example.workflow;

import java.util.Arrays;
import java.util.List;

import com.eclipsesource.glsp.api.language.IGraphicaLanguage;

public class WorkflowLanguage implements IGraphicaLanguage {
	private String id = "workflow";
	private String name = "Workflow";
	private String label = "Workflow diagram";
	private String diagramType = "workflow-diagram";
	private List<String> fileExtensions = Arrays.asList("wf");

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getLabel() {
		return label;
	}	

	@Override
	public String getDiagramType() {
		return diagramType;
	}
	
	@Override
	public List<String> getFileExtensions(){
		return fileExtensions;
	}

}
