package at.tortmayr.chillisp.api.actions;

import at.tortmayr.chillisp.api.ActionRegistry;

public class MoveAction extends Action {
	private String movedElementId;
	private String targetContainerId;

	public MoveAction() {
		super(ActionRegistry.Kind.MOVE);
		// TODO Auto-generated constructor stub
	}

	public MoveAction(String movedElementId, String targetContainerId) {
		this();
		this.movedElementId = movedElementId;
		this.targetContainerId = targetContainerId;
	}

	public String getMovedElementId() {
		return movedElementId;
	}

	public String getTargetContainerId() {
		return targetContainerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((movedElementId == null) ? 0 : movedElementId.hashCode());
		result = prime * result + ((targetContainerId == null) ? 0 : targetContainerId.hashCode());
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
		MoveAction other = (MoveAction) obj;
		if (movedElementId == null) {
			if (other.movedElementId != null)
				return false;
		} else if (!movedElementId.equals(other.movedElementId))
			return false;
		if (targetContainerId == null) {
			if (other.targetContainerId != null)
				return false;
		} else if (!targetContainerId.equals(other.targetContainerId))
			return false;
		return true;
	}

}
