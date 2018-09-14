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

import com.eclipsesource.glsp.api.action.kind.CollapseExpandAction;
import com.eclipsesource.glsp.api.action.kind.CollapseExpandAllAction;

public interface ModelExpansionListener {
	void expansionChanged(CollapseExpandAction action);
	void expansionChanged(CollapseExpandAllAction action);
	
	public static class NullImpl implements ModelExpansionListener{

		@Override
		public void expansionChanged(CollapseExpandAction action) {
		}

		@Override
		public void expansionChanged(CollapseExpandAllAction action) {	
		}

		
	}
}
