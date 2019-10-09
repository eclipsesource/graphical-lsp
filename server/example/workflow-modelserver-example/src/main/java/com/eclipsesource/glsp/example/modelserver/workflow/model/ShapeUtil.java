package com.eclipsesource.glsp.example.modelserver.workflow.model;

import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Dimension;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationFactory;
import com.eclipsesource.glsp.graph.GDimension;
import com.eclipsesource.glsp.graph.GPoint;

public class ShapeUtil {

	public static Point point(GPoint location) {
		return point(location.getX(), location.getY());
	}

	public static Point point(double x, double y) {
		Point point = WfnotationFactory.eINSTANCE.createPoint();
		point.setX(x);
		point.setY(y);
		return point;
	}

	public static Dimension dimension(double width, double height) {
		Dimension dimension = WfnotationFactory.eINSTANCE.createDimension();
		dimension.setWidth(width);
		dimension.setHeight(height);
		return dimension;
	}

	public static Dimension dimension(GDimension size) {
		return dimension(size.getWidth(), size.getHeight());
	}

}
