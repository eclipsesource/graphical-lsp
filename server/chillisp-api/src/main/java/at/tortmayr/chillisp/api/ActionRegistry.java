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
package at.tortmayr.chillisp.api;

import java.util.HashMap;
import java.util.Map;

import at.tortmayr.chillisp.api.actions.Action;
import at.tortmayr.chillisp.api.actions.CenterAction;
import at.tortmayr.chillisp.api.actions.ChangeBoundsAction;
import at.tortmayr.chillisp.api.actions.CollapseExpandAction;
import at.tortmayr.chillisp.api.actions.CollapseExpandAllAction;
import at.tortmayr.chillisp.api.actions.ComputedBoundsAction;
import at.tortmayr.chillisp.api.actions.ExecuteNodeCreationToolAction;
import at.tortmayr.chillisp.api.actions.ExecuteToolAction;
import at.tortmayr.chillisp.api.actions.ExportSVGAction;
import at.tortmayr.chillisp.api.actions.FitToScreenAction;
import at.tortmayr.chillisp.api.actions.MoveAction;
import at.tortmayr.chillisp.api.actions.OpenAction;
import at.tortmayr.chillisp.api.actions.RequestBoundsAction;
import at.tortmayr.chillisp.api.actions.RequestBoundsChangeHintsAction;
import at.tortmayr.chillisp.api.actions.RequestExportSvgAction;
import at.tortmayr.chillisp.api.actions.RequestLayersAction;
import at.tortmayr.chillisp.api.actions.RequestModelAction;
import at.tortmayr.chillisp.api.actions.RequestMoveHintsAction;
import at.tortmayr.chillisp.api.actions.RequestPopupModelAction;
import at.tortmayr.chillisp.api.actions.RequestToolsAction;
import at.tortmayr.chillisp.api.actions.SelectAction;
import at.tortmayr.chillisp.api.actions.SelectAllAction;
import at.tortmayr.chillisp.api.actions.ServerStatusAction;
import at.tortmayr.chillisp.api.actions.SetBoundsAction;
import at.tortmayr.chillisp.api.actions.SetBoundsChangeHintsAction;
import at.tortmayr.chillisp.api.actions.SetLayersAction;
import at.tortmayr.chillisp.api.actions.SetModelAction;
import at.tortmayr.chillisp.api.actions.SetMoveHintsAction;
import at.tortmayr.chillisp.api.actions.SetPopupModelAction;
import at.tortmayr.chillisp.api.actions.SetToolsAction;
import at.tortmayr.chillisp.api.actions.ToogleLayerAction;
import at.tortmayr.chillisp.api.actions.UpdateModelAction;

public class ActionRegistry {
	private static Map<String, Class<? extends Action>> actionKinds = new HashMap<String, Class<? extends Action>>() {

		private static final long serialVersionUID = 1L;

		{
			put(Kind.REQUEST_MODEL, RequestModelAction.class);
			put(Kind.SET_MODEL, SetModelAction.class);
			put(Kind.CENTER, CenterAction.class);
			put(Kind.COLLAPSE_EXPAND, CollapseExpandAction.class);
			put(Kind.COLLAPSE_EXPAND_ALL, CollapseExpandAllAction.class);
			put(Kind.COMPUTED_BOUNDS, ComputedBoundsAction.class);
			put(Kind.EXECUTE_NODE_CREATION_TOOL, ExecuteNodeCreationToolAction.class);
			put(Kind.EXECUTE_TOOL, ExecuteToolAction.class);
			put(Kind.REQUEST_BOUNDS_CHANGE_HINTS, RequestBoundsChangeHintsAction.class);
			put(Kind.SET_BOUNDS_CHANGE_HINTS, SetBoundsChangeHintsAction.class);
			put(Kind.CHANGE_BOUNDS_ACTION, ChangeBoundsAction.class);
			put(Kind.REQUEST_MOVE_HINTS, RequestMoveHintsAction.class);
			put(Kind.SET_MOVE_HINTS, SetMoveHintsAction.class);
			put(Kind.MOVE, MoveAction.class);
			put(Kind.EXPORT_SVG, ExportSVGAction.class);
			put(Kind.FIT_TO_SCREEN, FitToScreenAction.class);
			put(Kind.OPEN, OpenAction.class);
			put(Kind.REQUEST_BOUNDS, RequestBoundsAction.class);
			put(Kind.REQUEST_EXPORT_SVG, RequestExportSvgAction.class);
			put(Kind.REQUEST_LAYERS, RequestLayersAction.class);
			put(Kind.REQUEST_POPUP_MODEL, RequestPopupModelAction.class);
			put(Kind.REQUEST_TOOLS, RequestToolsAction.class);
			put(Kind.SELECT, SelectAction.class);
			put(Kind.SERVER_STATUS, ServerStatusAction.class);
			put(Kind.SET_BOUNDS, SetBoundsAction.class);
			put(Kind.SET_LAYERS, SetLayersAction.class);
			put(Kind.SET_POPUP_MODEL, SetPopupModelAction.class);
			put(Kind.SET_TOOLS, SetToolsAction.class);
			put(Kind.TOOGLE_LAYER, ToogleLayerAction.class);
			put(Kind.UPDATE_MODEL, UpdateModelAction.class);
			put(Kind.SELECT_ALL_ACTION, SelectAllAction.class);
		}
	};

	public static Class<? extends Action> getActionClass(String kind) {
		return actionKinds.get(kind);
	}

	private ActionRegistry() {
	}

	public static class Kind {
		public static final String REQUEST_MODEL = "requestModel";
		public static final String SET_MODEL = "setModel";
		public static final String CENTER = "center";
		public static final String COLLAPSE_EXPAND = "collapseExpand";
		public static final String COLLAPSE_EXPAND_ALL = "collapseExpandAll";
		public static final String COMPUTED_BOUNDS = "computedBounds";
		public static final String EXECUTE_NODE_CREATION_TOOL = "executeNodeCreationTool";
		public static final String EXECUTE_TOOL = "executeTool";
		public static final String REQUEST_BOUNDS_CHANGE_HINTS = "requestBoundsChangeHints";
		public static final String SET_BOUNDS_CHANGE_HINTS = "setBoundsChangeHints";
		public static final String CHANGE_BOUNDS_ACTION = "changeBounds";
		public static final String REQUEST_MOVE_HINTS = "requestMoveHints";
		public static final String SET_MOVE_HINTS = "setMoveHints";
		public static final String MOVE = "move";
		public static final String EXPORT_SVG = "exportSvg";
		public static final String FIT_TO_SCREEN = "fit";
		public static final String OPEN = "open";
		public static final String REQUEST_BOUNDS = "requestBounds";
		public static final String REQUEST_EXPORT_SVG = "requestExportSvg";
		public static final String REQUEST_LAYERS = "requestLayers";
		public static final String REQUEST_POPUP_MODEL = "requestPopupModel";
		public static final String REQUEST_TOOLS = "requestTools";
		public static final String SELECT = "elementSelected";
		public static final String SERVER_STATUS = "serverStatus";
		public static final String SET_BOUNDS = "setBounds";
		public static final String SET_LAYERS = "setLayers";
		public static final String SET_POPUP_MODEL = "setPopupModel";
		public static final String SET_TOOLS = "setTools";
		public static final String TOOGLE_LAYER = "toggleLayer";
		public static final String UPDATE_MODEL = "updateModel";
		public static final String SELECT_ALL_ACTION = "allSelected";

		private Kind() {

		}

	}

}
