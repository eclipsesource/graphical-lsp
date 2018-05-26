package at.tortmayr.chillisp.api.actions;

import java.util.Map;

import at.tortmayr.chillisp.api.ActionRegistry;

public class RequestModelAction extends Action {

	private Map<String, String> options;

	public RequestModelAction() {
		super(ActionRegistry.Kind.REQUEST_MODEL);
	}

	public RequestModelAction(Map<String, String> options) {
		this();
		this.options = options;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestModelAction other = (RequestModelAction) obj;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		return true;
	}

}
