package at.tortmayr.glsp.api.action.kind;

import at.tortmayr.glsp.api.action.Action;

public class CreateConnectionAction extends Action {
	
	private String toolId;
	private String sourceElement;
	private String targetElement;
	
	public CreateConnectionAction() {
		super(ActionKind.CREATE_CONNECTION);
	}
	
	public CreateConnectionAction(String typeId, String source, String target) {
		this();
		this.toolId = typeId;
		this.sourceElement = source;
		this.targetElement = target;
	}

	public String getToolId() {
		return toolId;
	}

	public String getSourceElement() {
		return sourceElement;
	}

	public String getTargetElement() {
		return targetElement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((sourceElement == null) ? 0 : sourceElement.hashCode());
		result = prime * result + ((targetElement == null) ? 0 : targetElement.hashCode());
		result = prime * result + ((toolId == null) ? 0 : toolId.hashCode());
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
		CreateConnectionAction other = (CreateConnectionAction) obj;
		if (sourceElement == null) {
			if (other.sourceElement != null)
				return false;
		} else if (!sourceElement.equals(other.sourceElement))
			return false;
		if (targetElement == null) {
			if (other.targetElement != null)
				return false;
		} else if (!targetElement.equals(other.targetElement))
			return false;
		if (toolId == null) {
			if (other.toolId != null)
				return false;
		} else if (!toolId.equals(other.toolId))
			return false;
		return true;
	}

}
