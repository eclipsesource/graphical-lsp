package at.tortmayr.chillisp.api.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import at.tortmayr.chillisp.api.IGraphicalModelState;
import io.typefox.sprotty.api.SModelRoot;

public class ModelState implements IGraphicalModelState {
	private Map<String, String> options;
	private String clientId;
	private SModelRoot currentModel;
	private Set<String> expandedElements;
	private Set<String> selectedElements;
	private boolean needsClientLayout;

	public ModelState() {
		expandedElements = new HashSet<>();
		selectedElements = new HashSet<>();
	}

	@Override
	public Map<String, String> getOptions() {
		return options;
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public SModelRoot getCurrentModel() {
		return currentModel;
	}

	@Override
	public void setCurrentModel(SModelRoot newRoot) {
		this.currentModel = newRoot;
	}

	@Override
	public Set<String> getExpandedElements() {
		return expandedElements;
	}

	@Override
	public Set<String> getSelectedElements() {
		return selectedElements;
	}

	@Override
	public void setOptions(Map<String, String> options) {
		this.options = options;

	}

	@Override
	public boolean needsClientLayout() {
		return needsClientLayout;
	}

	@Override
	public void setNeedsClientLayout(boolean value) {
		this.needsClientLayout = value;

	}

	@Override
	public void setExpandedElements(Set<String> expandedElements) {
		this.expandedElements = expandedElements;

	}

	@Override
	public void setSelectedElemetns(Set<String> selectedElements) {
		this.selectedElements = selectedElements;
	}

}
