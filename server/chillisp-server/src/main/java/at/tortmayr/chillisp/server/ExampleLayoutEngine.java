package at.tortmayr.chillisp.server;

import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.elk.alg.layered.options.NodePlacementStrategy;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.options.Direction;

import io.typefox.sprotty.api.SGraph;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.layout.ElkLayoutEngine;
import io.typefox.sprotty.layout.SprottyLayoutConfigurator;

public class ExampleLayoutEngine extends ElkLayoutEngine {
	public ExampleLayoutEngine() {

	}

	@Override
	public void layout(SModelRoot root) {
		if (root instanceof SGraph) {
			SGraph graph = (SGraph) root;
			SprottyLayoutConfigurator configurator = new SprottyLayoutConfigurator();
			configurator.configureByType("node").setProperty(CoreOptions.DIRECTION, Direction.DOWN)
					.setProperty(CoreOptions.SPACING_NODE_NODE, 40.0).setProperty(CoreOptions.SPACING_EDGE_NODE, 25.0)
					.setProperty(LayeredOptions.SPACING_EDGE_NODE_BETWEEN_LAYERS, 20.0)
					.setProperty(LayeredOptions.SPACING_NODE_NODE_BETWEEN_LAYERS, 30.0)
					.setProperty(LayeredOptions.NODE_PLACEMENT_STRATEGY, NodePlacementStrategy.NETWORK_SIMPLEX);

			layout(graph, configurator);
		}
	}
}
