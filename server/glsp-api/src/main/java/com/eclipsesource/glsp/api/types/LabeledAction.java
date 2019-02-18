/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.api.types;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.eclipsesource.glsp.api.action.Action;

public class LabeledAction {
	private String label;
	private List<Action> actions;
	
	public LabeledAction(String label, List<Action> actions) {
		this.label = label;
		setActions(actions);
	}
	
	public LabeledAction(String label, Action... actions) {
		this.label = label;
		setActions(Arrays.asList(actions));
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setActions(List<Action> actions) {
		if(actions == null || actions.isEmpty()) {
			throw new IllegalArgumentException("Invalid LabeledAction: Require at least one action to execute.");
		}
		this.actions = actions;
	}
	
	public Collection<Action> getActions() {
		return actions;
	}
}
