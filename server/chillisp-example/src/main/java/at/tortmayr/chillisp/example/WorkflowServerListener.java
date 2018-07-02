package at.tortmayr.chillisp.example;

import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.IGraphicalModelExpansionListener;
import at.tortmayr.chillisp.api.IGraphicalModelSelectionListener;
import at.tortmayr.chillisp.api.IModelElementOpenListener;
import at.tortmayr.chillisp.api.actions.CollapseExpandAction;
import at.tortmayr.chillisp.api.actions.CollapseExpandAllAction;
import at.tortmayr.chillisp.api.actions.OpenAction;
import at.tortmayr.chillisp.api.actions.SelectAction;
import at.tortmayr.chillisp.api.actions.SelectAllAction;

public class WorkflowServerListener
		implements IGraphicalModelSelectionListener, IGraphicalModelExpansionListener, IModelElementOpenListener {

	@Override
	public void elementOpened(OpenAction action, IGraphicalLanguageServer server) {
		System.out.println("HANDLE: OpenAction for element: " + action.getElementId());

	}

	@Override
	public void expansionChanged(CollapseExpandAction action, IGraphicalLanguageServer server) {
		System.out.println("HANDLE: CollapseExpandAction for elements: " + action.getCollapseIds());

	}

	@Override
	public void expansionChanged(CollapseExpandAllAction action, IGraphicalLanguageServer server) {
		System.out.println("HANDLE: CollapseExpandAllAction");

	}

	@Override
	public void selectionChanged(SelectAction action, IGraphicalLanguageServer server) {
		System.out.println("HANDLE: SelectAction for elements:" + action.getSelectedElementsIDs());

	}

	@Override
	public void selectionChanged(SelectAllAction action, IGraphicalLanguageServer server) {
		System.out.println("HANDLE: SelectAllAction");

	}

}
