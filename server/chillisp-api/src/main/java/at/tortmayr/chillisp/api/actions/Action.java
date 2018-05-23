package at.tortmayr.chillisp.api.actions;

import at.tortmayr.chillisp.api.IAction;

public abstract class Action implements IAction {

	public static class Kind {
		public static final String REQUEST_MODEL = "requestModel";
		public static final String SET_MODEL = "setModel";
		public static final String CENTER = "center";
		public static final String COLLAPSE_EXPAND = "collapseExpand";
		public static final String COLLAPSE_EXPAND_ALL = "collapseExpandAll";
		public static final String COMPUTE_BOUNDS = "computedBounds";
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

		private Kind() {

		}

	}

	private String kind;

	public Action(String kind) {
		super();
		this.kind = kind;
	}

	@Override
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

}
