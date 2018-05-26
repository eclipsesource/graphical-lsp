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
	private static ActionRegistry INSTANCE;
	private Map<String, Class<? extends Action>> actionKinds;

	public static ActionRegistry getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ActionRegistry();
		}
		return INSTANCE;
	}

	private ActionRegistry() {
		actionKinds = new HashMap<>();
		registerDefaultActions();
	}


	private void registerDefaultActions() {
		registerActionKind(Kind.REQUEST_MODEL, RequestModelAction.class);
		registerActionKind(Kind.SET_MODEL, SetModelAction.class);
		registerActionKind(Kind.CENTER, CenterAction.class);
		registerActionKind(Kind.COLLAPSE_EXPAND, CollapseExpandAction.class);
		registerActionKind(Kind.COLLAPSE_EXPAND_ALL, CollapseExpandAllAction.class);
		registerActionKind(Kind.COMPUTED_BOUNDS, ComputedBoundsAction.class);
		registerActionKind(Kind.EXECUTE_NODE_CREATION_TOOL, ExecuteNodeCreationToolAction.class);
		registerActionKind(Kind.EXECUTE_TOOL, ExecuteToolAction.class);
		registerActionKind(Kind.REQUEST_BOUNDS_CHANGE_HINTS, RequestBoundsChangeHintsAction.class);
		registerActionKind(Kind.SET_BOUNDS_CHANGE_HINTS, SetBoundsChangeHintsAction.class);
		registerActionKind(Kind.CHANGE_BOUNDS_ACTION, ChangeBoundsAction.class);
		registerActionKind(Kind.REQUEST_MOVE_HINTS, RequestMoveHintsAction.class);
		registerActionKind(Kind.SET_MOVE_HINTS, SetMoveHintsAction.class);
		registerActionKind(Kind.MOVE, MoveAction.class);
		registerActionKind(Kind.EXPORT_SVG, ExportSVGAction.class);
		registerActionKind(Kind.FIT_TO_SCREEN, FitToScreenAction.class);
		registerActionKind(Kind.OPEN, OpenAction.class);
		registerActionKind(Kind.REQUEST_BOUNDS, RequestBoundsAction.class);
		registerActionKind(Kind.REQUEST_EXPORT_SVG, RequestExportSvgAction.class);
		registerActionKind(Kind.REQUEST_LAYERS, RequestLayersAction.class);
		registerActionKind(Kind.REQUEST_POPUP_MODEL, RequestPopupModelAction.class);
		registerActionKind(Kind.REQUEST_TOOLS, RequestToolsAction.class);
		registerActionKind(Kind.SELECT, SelectAction.class);
		registerActionKind(Kind.SERVER_STATUS, ServerStatusAction.class);
		registerActionKind(Kind.SET_BOUNDS, SetBoundsAction.class);
		registerActionKind(Kind.SET_LAYERS, SetLayersAction.class);
		registerActionKind(Kind.SET_POPUP_MODEL, SetPopupModelAction.class);
		registerActionKind(Kind.SET_TOOLS, SetToolsAction.class);
		registerActionKind(Kind.TOOGLE_LAYER, ToogleLayerAction.class);
		registerActionKind(Kind.UPDATE_MODEL, UpdateModelAction.class);
		registerActionKind(Kind.SELECT_ALL_ACTION, SelectAction.class);
	}

	public Class<? extends Action> getActionClass(String kind) {
		return actionKinds.get(kind);
	}

	public void registerActionKind(String kind, Class<? extends Action> clazz) {
		actionKinds.put(kind, clazz);
	}

	public void unregisterActionKind(String kind) {
		actionKinds.remove(kind);
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
