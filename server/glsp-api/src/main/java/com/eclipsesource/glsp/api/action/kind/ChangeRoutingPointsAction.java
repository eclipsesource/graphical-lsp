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

import java.util.List;

import com.eclipsesource.glsp.api.action.Action;
import com.eclipsesource.glsp.api.model.ElementAndRoutingPoints;

public class ChangeRoutingPointsAction extends Action{
	
	private List<ElementAndRoutingPoints> newRoutingPoints;

	public ChangeRoutingPointsAction() {
		super(Action.Kind.CHANGE_ROUTING_POINTS);
	}
	
	public ChangeRoutingPointsAction(List<ElementAndRoutingPoints> elementAndRoutingPoints) {
		this();
		this.newRoutingPoints = elementAndRoutingPoints;
	}

	public List<ElementAndRoutingPoints> getNewRoutingPoints() {
		return newRoutingPoints;
	}

	public void setNewRoutingPoints(List<ElementAndRoutingPoints> newRoutingPoints) {
		this.newRoutingPoints = newRoutingPoints;
	}

	@Override
	public String toString() {
		return "ChangeRoutingPointsAction [newRoutingPoints=" + newRoutingPoints + "]";
	}
}
