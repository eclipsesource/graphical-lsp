package com.eclipsesource.glsp.ecore.model;

import org.eclipse.sprotty.SGraph;

public class EcoreGraph extends SGraph {
	
	public EcoreGraph() {
		setType("graph");
	}
	private boolean needsInitialLayout = true;

	public boolean isNeedsInitialLayout() {
		return needsInitialLayout;
	}

	public void setNeedsInitialLayout(boolean needsInitialLayout) {
		this.needsInitialLayout = needsInitialLayout;
	}
	
	
}
