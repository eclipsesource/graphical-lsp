/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.model;

import java.util.Set;

import com.eclipsesource.glsp.api.utils.ModelOptions.ParsedModelOptions;
import com.eclipsesource.glsp.api.utils.SModelIndex;

import io.typefox.sprotty.api.SModelRoot;

public interface ModelState {

	ParsedModelOptions getOptions();

	String getClientId();

	SModelRoot getCurrentModel();

	void setCurrentModel(SModelRoot newRoot);

	Set<String> getExpandedElements();

	Set<String> getSelectedElements();

	void setExpandedElements(Set<String> expandedElements);

	void setSelectedElemetns(Set<String> selectedElements);

	void setOptions(ParsedModelOptions options);

	boolean needsClientLayout();

	void setNeedsClientLayout(boolean value);
	
	SModelIndex getCurrentModelIndex();
	

}
