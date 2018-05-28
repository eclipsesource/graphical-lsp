package org.chillisp.server;

import java.util.ArrayList;
import java.util.List;

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IModelFactory;
import io.typefox.sprotty.api.Dimension;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SGraph;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.api.SNode;

public class ExampleModelFactory implements IModelFactory {

	@Override
	public SModelRoot loadModel(IGraphicalLanguageServer server) {
		SModelRoot model = new SGraph();
		model.setType("graph");
		model.setId("sprotty");
		SNode node = new SNode();
		node.setId("first");
		node.setType("node");
		node.setLayout("vbox");
		node.setPosition(new Point(100, 100));
		node.setSize(new Dimension(25, 25));
		List<SModelElement> children = new ArrayList<>();
		children.add(node);
		model.setChildren(children);
		return model;
	}

}
