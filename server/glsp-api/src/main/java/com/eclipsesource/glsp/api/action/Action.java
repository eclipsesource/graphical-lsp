/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.action;

import com.eclipsesource.glsp.api.operations.Operation;

public abstract class Action {
	private String kind;

	public Action(String kind) {
		super();
		this.kind = kind;
	}

	public String getKind() {
		return kind;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Action [kind=");
		builder.append(kind);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Action other = (Action) obj;
		if (kind == null) {
			if (other.kind != null)
				return false;
		} else if (!kind.equals(other.kind))
			return false;
		return true;
	}

	public static class Kind {
		public static final String REQUEST_MODEL = "requestModel";
		public static final String SET_MODEL = "setModel";
		public static final String CENTER = "center";
		public static final String COLLAPSE_EXPAND = "collapseExpand";
		public static final String COLLAPSE_EXPAND_ALL = "collapseExpandAll";
		public static final String COMPUTED_BOUNDS = "computedBounds";
		public static final String EXECUTE_OPERATION = "executeOperation";
		public static final String REQUEST_TYPE_HINTS = "requestTypeHints";
		public static final String SET_TYPE_HINTS = "setTypeHints";
		public static final String SET_MOVE_HINTS = "setMoveHints";
		public static final String EXPORT_SVG = "exportSvg";
		public static final String FIT_TO_SCREEN = "fit";
		public static final String OPEN = "open";
		public static final String REQUEST_BOUNDS = "requestBounds";
		public static final String REQUEST_EXPORT_SVG = "requestExportSvg";
		public static final String REQUEST_LAYERS = "requestLayers";
		public static final String REQUEST_POPUP_MODEL = "requestPopupModel";
		public static final String REQUEST_OPERATIONS = "requestOperations";
		public static final String SELECT = "elementSelected";
		public static final String SERVER_STATUS = "serverStatus";
		public static final String SET_BOUNDS = "setBounds";
		public static final String SET_LAYERS = "setLayers";
		public static final String SET_POPUP_MODEL = "setPopupModel";
		public static final String SET_OPERATIONS = "setOperations";
		public static final String TOOGLE_LAYER = "toggleLayer";
		public static final String UPDATE_MODEL = "updateModel";
		public static final String SELECT_ALL = "allSelected";
		public static final String SAVE_MODEL = "saveModel";
		public static final String CREATE_CONNECTION_OPERATION = Operation.Kind.CREATE_CONNECTION;
		public static final String CREATE_NODE_OPERATION = Operation.Kind.CREATE_NODE;
		public static final String DELETE_ELEMENT_OPERATION = Operation.Kind.DELETE_ELEMENT;
		public static final String CHANGE_BOUNDS_OPERATION = Operation.Kind.CHANGE_BOUNDS;
		public static final String CHANGE_CONTAINER_OPERATION= Operation.Kind.CHANGE_CONTAINER;
		public static final String GENERIC_OPERATION = Operation.Kind.GENERIC;
		public static final String EXECUTE_SERVER_COMMAND = "executeServerCommand";
	}

}
