package org.chillisp.server;

import java.util.ArrayList;
import java.util.Arrays;

import org.chillisp.server.schema.TaskNode;

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IPopupModelFactory;
import at.tortmayr.chillisp.api.actions.RequestPopupModelAction;
import io.typefox.sprotty.api.HtmlRoot;
import io.typefox.sprotty.api.PreRenderedElement;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;

public class WorkflowPopupFactory implements IPopupModelFactory {

	private String generateTitle(TaskNode task) {
		return String.format("Task %s", task.getName());
	}

	private String generateBody(TaskNode task) {
		return String.format("Properties:" + NL + "Type: %s" + NL + "Duration: %s" + NL + " Reference: %s" + NL,
				task.getTaskType(), task.getDuration(), task.getReference());
	}

	private static final String NL = "<br>";

	@Override
	public SModelRoot createPopuModel(SModelElement element, RequestPopupModelAction action,
			IGraphicalLanguageServer server) {
		if (element != null && element.getType().equals("node:task")) {
			TaskNode task = (TaskNode) element;
			HtmlRoot root = new HtmlRoot();
			root.setCanvasBounds(action.getBounds());
			root.setType("html");
			root.setId("popup");
			root.setChildren(new ArrayList<SModelElement>());
			PreRenderedElement p1 = new PreRenderedElement();
			p1.setType("pre-rendered");
			p1.setId("popup-title");
			p1.setCode("<div class=\"sprotty-popup-title\">" + generateTitle(task) + "</div>");
			
			PreRenderedElement p2 = new PreRenderedElement();
			p2.setType("pre-rendered");
			p2.setId("popup-body");
			p2.setCode("<div class=\"sprotty-popup-body\">" + generateBody(task) + "</div>");
			root.getChildren().addAll(Arrays.asList(p1, p2));
			return root;
		}
		return null;

	}

}
