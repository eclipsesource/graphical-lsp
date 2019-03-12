package com.eclipsesource.glsp.ecore.model;

import java.util.ArrayList;

import org.eclipse.sprotty.SNode;

public class ClassNode extends SNode {

	public ClassNode() {
		setType(ModelTypes.ECLASS);
		setCssClasses(new ArrayList<String>());
	}

	private boolean expanded;
	private double strokeWidth;

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public double getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(double strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
}