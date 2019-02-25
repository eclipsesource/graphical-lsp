package com.eclipsesource.glsp.ecore.diagram;

import org.eclipse.elk.alg.layered.options.EdgeLabelSideSelection;
import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.core.options.EdgeLabelPlacement;
import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.sprotty.SGraph;
import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;
import org.eclipse.sprotty.layout.ElkLayoutEngine;
import org.eclipse.sprotty.layout.SprottyLayoutConfigurator;


public class EcoreLayoutEngine extends ElkLayoutEngine {

	@Override
	public void layout(SModelRoot root) {
		if (root instanceof SGraph) {
			SprottyLayoutConfigurator configurator = new SprottyLayoutConfigurator();
			configurator.configureByType("graph")
			.setProperty(LayeredOptions.EDGE_LABELS_PLACEMENT, EdgeLabelPlacement.CENTER)
			.setProperty(LayeredOptions.EDGE_LABELS_SIDE_SELECTION, EdgeLabelSideSelection.ALWAYS_UP)	;
			this.layout((SGraph) root, configurator);
		}
	}


	
	protected boolean shouldInclude(SModelElement element, SModelElement sParent, ElkGraphElement elkParent, LayoutContext context) {
		if (element.getType().equals("label:icon")) {
			return false;
		}
		return super.shouldInclude(element, sParent, elkParent, context);
	}
	
}
