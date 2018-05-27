package at.tortmayr.chillisp.api;

import java.util.Map;
import java.util.Set;

import io.typefox.sprotty.api.SModelRoot;

public interface IGraphicalModelState {

	Map<String, String> getOptions();

	String getClientId();

	SModelRoot getCurrentModel();

	void setCurrentModel(SModelRoot newRoot);

	Set<String> getExpandedElements();

	Set<String> getSelectedElements();

	void setExpandedElements(Set<String> expandedElements);

	void setSelectedElemetns(Set<String> selectedElements);

	void setOptions(Map<String, String> options);;

	boolean needsClientLayout();

	void setNeedsClientLayout(boolean value);

}
