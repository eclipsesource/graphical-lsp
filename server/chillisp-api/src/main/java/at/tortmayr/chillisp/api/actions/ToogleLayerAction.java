package at.tortmayr.chillisp.api.actions;

import at.tortmayr.chillisp.api.ActionRegistry;

public class ToogleLayerAction extends Action {
	private String layerName;
	private boolean newState;

	public ToogleLayerAction() {
		super(ActionRegistry.Kind.TOOGLE_LAYER);

	}

	public ToogleLayerAction(String layerName, boolean newState) {
		this();
		this.layerName = layerName;
		this.newState = newState;
	}

	public String getLayerName() {
		return layerName;
	}

	public boolean isNewState() {
		return newState;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((layerName == null) ? 0 : layerName.hashCode());
		result = prime * result + (newState ? 1231 : 1237);
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
		ToogleLayerAction other = (ToogleLayerAction) obj;
		if (layerName == null) {
			if (other.layerName != null)
				return false;
		} else if (!layerName.equals(other.layerName))
			return false;
		if (newState != other.newState)
			return false;
		return true;
	}

}
