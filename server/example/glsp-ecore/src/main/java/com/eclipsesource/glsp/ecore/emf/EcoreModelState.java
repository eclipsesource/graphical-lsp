package com.eclipsesource.glsp.ecore.emf;

import org.eclipse.emf.ecore.EObject;

public class EcoreModelState {
	private EObject currentModel;
	private EcoreModelIndex index;
	private boolean dirty;

	public EcoreModelState(EObject currentModel) {
		this.currentModel = currentModel;
		index= new EcoreModelIndex(currentModel);
	}

	public EObject getCurrentModel() {
		return currentModel;
	}

	public void setCurrentModel(EObject currentModel) {
		this.currentModel = currentModel;
	}

	public EcoreModelIndex getIndex() {
		return index;
	}

	public void setIndex(EcoreModelIndex index) {
		this.index = index;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

}
