package at.tortmayr.chillisp.example;

import java.util.HashMap;
import java.util.Map;

import at.tortmayr.chillisp.api.impl.FileBasedModelFactory;
import at.tortmayr.chillisp.example.schema.ActivityNode;
import at.tortmayr.chillisp.example.schema.Icon;
import at.tortmayr.chillisp.example.schema.TaskNode;
import at.tortmayr.chillisp.example.schema.WeightedEdge;
import io.typefox.sprotty.api.HtmlRoot;
import io.typefox.sprotty.api.PreRenderedElement;
import io.typefox.sprotty.api.SCompartment;
import io.typefox.sprotty.api.SEdge;
import io.typefox.sprotty.api.SGraph;
import io.typefox.sprotty.api.SLabel;
import io.typefox.sprotty.api.SModelElement;

public class WorkflowModelFactory extends FileBasedModelFactory {

	@Override
	protected Map<String, Class<? extends SModelElement>> getModelTypeSchema() {
		return new HashMap<String, Class<? extends SModelElement>>() {
			private static final long serialVersionUID = 1L;
			{
				put("graph", SGraph.class);
				put("label:heading", SLabel.class);
				put("label:text", SLabel.class);
				put("comp:comp", SCompartment.class);
				put("comp:header", SCompartment.class);
				put("label:icon", SLabel.class);
				put("edge", SEdge.class);
				put("html", HtmlRoot.class);
				put("pre-rendered", PreRenderedElement.class);
				put("edge:weighted", WeightedEdge.class);
				put("icon", Icon.class);
				put("node:activity", ActivityNode.class);
				put("node:task", TaskNode.class);
			}
		};

	}

}
