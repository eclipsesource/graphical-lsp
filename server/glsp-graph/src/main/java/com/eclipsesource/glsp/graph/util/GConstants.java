package com.eclipsesource.glsp.graph.util;

public final class GConstants {

	public static final class RouterKind {
		public static final String POLYLINE = "polyline";
		public static final String MANHATTAN = "manhattan";
	}

	public static final class EdgeSide {
		public static final String LEFT = "left";
		public static final String RIGHT = "right";
		public static final String TOP = "top";
		public static final String BOTTOM = "bottom";
		public static final String ON = "on";
	}

	public static final class Layout {
		public static final String VBOX = "vbox";
		public static final String HBOX = "hbox";
		public static final String STACK = "stack";
	}

	public static final class VAlign {
		public static final String TOP = "top";
		public static final String CENTER = "center";
		public static final String BOTTOM = "bottom";
	}

	public static final class HAlign {
		public static final String LEFT = "left";
		public static final String CENTER = "center";
		public static final String RIGHT = "right";
	}

	private GConstants() {

	}
}
