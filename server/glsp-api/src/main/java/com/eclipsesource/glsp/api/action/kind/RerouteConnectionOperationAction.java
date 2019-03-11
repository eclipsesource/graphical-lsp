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

import org.eclipse.sprotty.Point;

import com.eclipsesource.glsp.api.action.Action;

public class RerouteConnectionOperationAction extends AbstractOperationAction {

	private String connectionElementId;
	private Point[] routingPoints;

	public RerouteConnectionOperationAction() {
		super(Action.Kind.REROUTE_CONNECTION_OPERATION);
	}

	public RerouteConnectionOperationAction(String connectionElementId, Point[] routingPoints) {
		this();
		this.connectionElementId = connectionElementId;
		this.routingPoints = routingPoints;
	}

	public String getConnectionElementId() {
		return connectionElementId;
	}

	public Point[] getRoutingPoints() {
		return routingPoints;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((connectionElementId == null) ? 0 : connectionElementId.hashCode());
		result = prime * result + Arrays.hashCode(routingPoints);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof RerouteConnectionOperationAction)) {
			return false;
		}
		RerouteConnectionOperationAction other = (RerouteConnectionOperationAction) obj;
		if (connectionElementId == null) {
			if (other.connectionElementId != null) {
				return false;
			}
		} else if (!connectionElementId.equals(other.connectionElementId)) {
			return false;
		}
		if (!Arrays.equals(routingPoints, other.routingPoints)) {
			return false;
		}
		return true;
	}
}
