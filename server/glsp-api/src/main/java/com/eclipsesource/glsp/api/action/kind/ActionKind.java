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
package com.eclipsesource.glsp.api.action.kind;

import com.eclipsesource.glsp.api.operations.OperationKind;

public final class ActionKind {

	public static final String REQUEST_MODEL = "requestModel";
	public static final String SET_MODEL = "setModel";
	public static final String CENTER = "center";
	public static final String COLLAPSE_EXPAND = "collapseExpand";
	public static final String COLLAPSE_EXPAND_ALL = "collapseExpandAll";
	public static final String COMPUTED_BOUNDS = "computedBounds";
	public static final String EXECUTE_OPERATION = "executeOperation";
	public static final String REQUEST_BOUNDS_CHANGE_HINTS = "requestBoundsChangeHints";
	public static final String SET_BOUNDS_CHANGE_HINTS = "setBoundsChangeHints";
	public static final String CHANGE_BOUNDS = "changeBounds";
	public static final String REQUEST_MOVE_HINTS = "requestMoveHints";
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
	public static final String CREATE_CONNECTION_OPERATION = EXECUTE_OPERATION + "_" + OperationKind.CREATE_CONNECTION;
	public static final String CREATE_NODE_OPERATION = EXECUTE_OPERATION + "_" + OperationKind.CREATE_NODE;
	public static final String DELETE_ELEMENT_OPERATION = EXECUTE_OPERATION + "_" + OperationKind.DELETE_ELEMENT;
	public static final String MOVE_OPERATION = EXECUTE_OPERATION + "_" + OperationKind.MOVE;

	private ActionKind() {
		// prevent instantiation for class only holding constants.
	}

}