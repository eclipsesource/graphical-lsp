package com.eclipsesource.glsp.ecore.model;

import java.util.ArrayList;

import org.eclipse.sprotty.SEdge;

public class EcoreEdge extends SEdge {
	private String multiplicitySource;
	private String multiplicityTarget;

	public EcoreEdge() {
		setCssClasses(new ArrayList<String>());
	}

	public String getMultiplicitySource() {
		return multiplicitySource;
	}

	public void setMultiplicitySource(String multiplicitySource) {
		this.multiplicitySource = multiplicitySource;
	}

	public String getMultiplicityTarget() {
		return multiplicityTarget;
	}

	public void setMultiplicityTarget(String multiplicityTarget) {
		this.multiplicityTarget = multiplicityTarget;
	}
}
