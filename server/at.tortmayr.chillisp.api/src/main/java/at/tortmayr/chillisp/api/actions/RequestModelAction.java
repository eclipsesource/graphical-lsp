package at.tortmayr.chillisp.api.actions;

import java.util.Map;

import at.tortmayr.chillisp.api.Action;

public class RequestModelAction implements Action {
	public static final String KIND = "requestModel";

	private Map<String, String> options;
	private String kind = KIND;

	public RequestModelAction() {
		
	}
	public RequestModelAction(Map<String, String> options, String kind) {
		this.options = options;
	}

	@Override
	public String getKind() {
		return kind;
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
