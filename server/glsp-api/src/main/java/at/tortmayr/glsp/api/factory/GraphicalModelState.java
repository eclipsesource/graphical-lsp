/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.glsp.api.factory;

import java.util.Map;
import java.util.Set;

import io.typefox.sprotty.api.SModelRoot;

public interface GraphicalModelState {

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
