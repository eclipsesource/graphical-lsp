package at.tortmayr.chillisp.api.actions;

import java.util.Arrays;

import at.tortmayr.chillisp.api.ActionRegistry;
import at.tortmayr.chillisp.api.type.Tool;

public class SetToolsAction extends Action {
	private Tool[] tools;

	public SetToolsAction() {
		super(ActionRegistry.Kind.SET_TOOLS);
	}

	public SetToolsAction(Tool[] tools) {
		this();
		this.tools = tools;
	}

	public Tool[] getTools() {
		return tools;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(tools);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetToolsAction other = (SetToolsAction) obj;
		if (!Arrays.equals(tools, other.tools))
			return false;
		return true;
	}

}
