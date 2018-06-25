package at.tortmayr.chillisp.server;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IModelFactory;
import at.tortmayr.chillisp.api.actions.RequestModelAction;
import at.tortmayr.chillisp.server.schema.ActivityNode;
import at.tortmayr.chillisp.server.schema.Icon;
import at.tortmayr.chillisp.server.schema.TaskNode;
import at.tortmayr.chillisp.server.schema.WeightedEdge;
import io.typefox.sprotty.api.LayoutOptions;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SEdge;
import io.typefox.sprotty.api.SGraph;
import io.typefox.sprotty.api.SLabel;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;
import io.typefox.sprotty.api.SNode;

public class WorkflowModelFactory implements IModelFactory {
	private static int nodeCounter = 0;
	private static int edgeCouner = 0;

	@Override
	public SModelRoot loadModel(IGraphicalLanguageServer server, RequestModelAction action) {
		TaskNode task1 = this.createTaskNode("Push", false, 30, null, 10, 200);
		TaskNode task2 = this.createTaskNode("ChkWt", true, 10, "ControlUnit", 200, 200);
		TaskNode task3 = this.createTaskNode("RflWt", false, 200, null, 400, 150);
		TaskNode task4 = this.createTaskNode("WtOK", true, 20, "WaterTank", 400, 250);
		TaskNode task5 = this.createTaskNode("ChkTp", false, 100, "ControlUnit", 600, 200);
		TaskNode task6 = this.createTaskNode("PreHeat", false, 1000, "BrewingUnit", 600, 400);
		TaskNode task7 = this.createTaskNode("KeepTp", false, 50, "BrewingUnit", 600, 500);
		TaskNode task8 = this.createTaskNode("Brew", false, 1750, "Brew", 400, 450);
		ActivityNode dec1 = this.createActivityNode("decisionNode", 350, 216);
		ActivityNode merge1 = this.createActivityNode("mergeNode", 550, 216);
		ActivityNode dec2 = this.createActivityNode("decisionNode", 650, 316);
		ActivityNode merge2 = this.createActivityNode("mergeNode", 550, 466);

		SModelRoot model = new SGraph();
		model.setType("graph");
		model.setId("sprotty");
		model.setChildren(new ArrayList<SModelElement>());
		model.getChildren().addAll(Arrays.asList(task1, task2, task3, task4, task5, task6, task7, task8, dec1, dec2,
				merge1, merge2, createEdge(task1.getId(), task2.getId()), createEdge(task2.getId(), dec1.getId()),
				createWeightedEdge(dec1.getId(), task3.getId(), "high"),
				createWeightedEdge(dec1.getId(), task4.getId(), "high"), createEdge(task3.getId(), merge1.getId()),
				createEdge(task4.getId(), merge1.getId()), createEdge(merge1.getId(), task5.getId()),
				createEdge(task5.getId(), dec2.getId()), createWeightedEdge(dec2.getId(), task6.getId(), "high"),
				createWeightedEdge(dec2.getId(), task7.getId(), "low"), createEdge(task6.getId(), merge2.getId()),
				createEdge(task7.getId(), merge2.getId()), createEdge(merge2.getId(), task8.getId())));
		return model;
	}

	protected TaskNode createTaskNode(String name, boolean automated, int duration, String reference, int x, int y) {
		nodeCounter++;
		TaskNode taskNode = new TaskNode();
		taskNode.setId("task" + nodeCounter);
		taskNode.setName(name);
		taskNode.setDuration(duration);
		taskNode.setReference(reference);
		taskNode.setTaskType(automated ? "automated" : "manual");
		taskNode.setPosition(new Point(x, y));
		taskNode.setLayout("vbox");
		taskNode.setChildren(new ArrayList<SModelElement>());

		SNode compHeader = new SNode();
		compHeader.setId("task" + nodeCounter + "_header");
		compHeader.setType("comp:header");
		compHeader.setLayout("hbox");
		compHeader.setChildren(new ArrayList<SModelElement>());
		Icon icon = new Icon();
		icon.setId("task" + nodeCounter + "_icon");
		icon.setLayout("stack");
		LayoutOptions layoutOptions = new LayoutOptions();
		layoutOptions.setHAlign("center");
		layoutOptions.setResizeContainer(false);
		icon.setLayoutOptions(layoutOptions);
		icon.setChildren(new ArrayList<SModelElement>());
		SLabel iconLabel = new SLabel();
		iconLabel.setType("label:icon");
		iconLabel.setId("task" + nodeCounter + "_ticon");
		iconLabel.setText("" + taskNode.getTaskType().toUpperCase().charAt(0));
		icon.getChildren().add(iconLabel);

		SLabel heading = new SLabel();
		heading.setId("task" + nodeCounter + "_classname");
		heading.setType("label:heading");
		heading.setText(name);
		compHeader.getChildren().add(icon);
		compHeader.getChildren().add(heading);
		taskNode.getChildren().add(compHeader);

		return taskNode;

	}

	protected SEdge createEdge(String source, String target) {
		edgeCouner++;
		SEdge edge = new SEdge();
		edge.setId("edge" + edgeCouner);
		edge.setType("edge");
		edge.setSourceId(source);
		edge.setTargetId(target);
		return edge;
	}

	protected SEdge createWeightedEdge(String source, String target, String prob) {
		edgeCouner++;
		WeightedEdge weightedEdge = new WeightedEdge();
		weightedEdge.setId("edge" + edgeCouner);
		weightedEdge.setSourceId(source);
		weightedEdge.setTargetId(target);
		weightedEdge.setProbability(prob);
		return weightedEdge;
	}

	protected ActivityNode createActivityNode(String nodeType, int x, int y) {
		nodeCounter++;
		ActivityNode node = new ActivityNode();
		node.setId("activityNode" + nodeCounter);
		node.setNodeType(nodeType);
		node.setPosition(new Point(x, y));
		return node;

	}

}
