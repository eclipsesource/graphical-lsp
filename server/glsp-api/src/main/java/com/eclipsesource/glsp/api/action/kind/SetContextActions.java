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
package com.eclipsesource.glsp.api.action.kind;

import java.util.Arrays;
import java.util.Set;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.types.LabeledAction;

public class SetContextActions extends Action {

	private Set<LabeledAction> actions;
	private String[] args;

	public SetContextActions() {
		super(Action.Kind.SET_CONTEXT_ACTIONS);
	}

	public SetContextActions(Set<LabeledAction> actions, String... args) {
		this();
		this.actions = actions;
		this.args = args;
	}

	public Set<LabeledAction> getActions() {
		return actions;
	}

	public void setActions(Set<LabeledAction> commandPaletteActions) {
		this.actions = commandPaletteActions;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((actions == null) ? 0 : actions.hashCode());
		result = prime * result + Arrays.hashCode(args);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetContextActions other = (SetContextActions) obj;
		if (actions == null) {
			if (other.actions != null)
				return false;
		} else if (!actions.equals(other.actions))
			return false;
		if (!Arrays.equals(args, other.args))
			return false;
		return true;
	}

}
