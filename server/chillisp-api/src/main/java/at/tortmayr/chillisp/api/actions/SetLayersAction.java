package at.tortmayr.chillisp.api.actions;

import java.util.Arrays;

import at.tortmayr.chillisp.api.ActionRegistry;
import at.tortmayr.chillisp.api.type.Layer;

public class SetLayersAction extends Action {
	private Layer[] layers;

	public SetLayersAction() {
		super(ActionRegistry.Kind.SET_LAYERS);
	}

	public SetLayersAction(Layer[] layers) {
		this();
		this.layers = layers;
	}

	public Layer[] getLayers() {
		return layers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(layers);
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
		SetLayersAction other = (SetLayersAction) obj;
		if (!Arrays.equals(layers, other.layers))
			return false;
		return true;
	}

}
