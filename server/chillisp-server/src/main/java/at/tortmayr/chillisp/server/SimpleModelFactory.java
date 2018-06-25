package at.tortmayr.chillisp.server;

import java.util.ArrayList;
import java.util.List;

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IModelFactory;
import at.tortmayr.chillisp.api.actions.RequestModelAction;
import io.typefox.sprotty.api.Dimension;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.api.SNode;

public class SimpleModelFactory implements IModelFactory {

	@Override
	public SModelRoot loadModel(IGraphicalLanguageServer server, RequestModelAction action) {
		SNode node = new SNode();
		node.setId("first");
		node.setType("node");
		node.setLayout("vbox");
		node.setPosition(new Point(100, 100));
		node.setSize(new Dimension(25, 25));
		SModelRoot currentRoot = new SModelRoot();
		currentRoot.setType("graph");
		currentRoot.setId("sprotty");
		List<SModelElement> children = new ArrayList<>();
		children.add(node);
		currentRoot.setChildren(children);
		return currentRoot;
	}

}
