package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.CenterAction;
import at.tortmayr.chillisp.api.actions.ChangeBoundsAction;
import at.tortmayr.chillisp.api.actions.CollapseExpandAction;
import at.tortmayr.chillisp.api.actions.CollapseExpandAllAction;
import at.tortmayr.chillisp.api.actions.ComputedBoundsAction;
import at.tortmayr.chillisp.api.actions.ExecuteNodeCreationToolAction;
import at.tortmayr.chillisp.api.actions.ExecuteToolAction;
import at.tortmayr.chillisp.api.actions.FitToScreenAction;
import at.tortmayr.chillisp.api.actions.MoveAction;
import at.tortmayr.chillisp.api.actions.OpenAction;
import at.tortmayr.chillisp.api.actions.RequestBoundsChangeHintsAction;
import at.tortmayr.chillisp.api.actions.RequestExportSvgAction;
import at.tortmayr.chillisp.api.actions.RequestLayersAction;
import at.tortmayr.chillisp.api.actions.RequestModelAction;
import at.tortmayr.chillisp.api.actions.RequestMoveHintsAction;
import at.tortmayr.chillisp.api.actions.RequestPopupModelAction;
import at.tortmayr.chillisp.api.actions.RequestToolsAction;
import at.tortmayr.chillisp.api.actions.SelectAction;
import at.tortmayr.chillisp.api.actions.SelectAllAction;
import at.tortmayr.chillisp.api.actions.SetBoundsAction;
import at.tortmayr.chillisp.api.actions.ToogleLayerAction;

public interface IRequestActionHandler {
	void setLanguageServer(IGraphicalLanguageServer server);
	
	IGraphicalModelState getModelState();

	void handle(RequestModelAction action);

	void handle(CenterAction action);

	void handle(CollapseExpandAction action);

	void handle(CollapseExpandAllAction action);

	void handle(ComputedBoundsAction action);

	void handle(ExecuteNodeCreationToolAction action);

	void handle(ExecuteToolAction action);

	void handle(RequestBoundsChangeHintsAction action);

	void handle(ChangeBoundsAction action);

	void handle(RequestMoveHintsAction action);

	void handle(MoveAction action);

	void handle(FitToScreenAction action);

	void handle(OpenAction action);

	void handle(RequestExportSvgAction action);

	void handle(RequestLayersAction action);

	void handle(RequestPopupModelAction action);

	void handle(SetBoundsAction action);

	void handle(RequestToolsAction action);

	void handle(SelectAction action);

	void handle(SelectAllAction action);

	void handle(ToogleLayerAction action);
}
