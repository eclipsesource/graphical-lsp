package at.tortmayr.chillisp.api.actions;

import java.util.Arrays;

import at.tortmayr.chillisp.api.ActionRegistry;

public class ServerStatusAction extends Action {
	private String severity;
	private String message;
	private String[] elementIds;

	public ServerStatusAction() {
		super(ActionRegistry.Kind.SERVER_STATUS);
	}

	public ServerStatusAction(String severity, String message, String[] elementIds) {
		this();
		this.severity = severity;
		this.message = message;
		this.elementIds = elementIds;
	}

	public String getSeverity() {
		return severity;
	}

	public String getMessage() {
		return message;
	}

	public String[] getElementIds() {
		return elementIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(elementIds);
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((severity == null) ? 0 : severity.hashCode());
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
		ServerStatusAction other = (ServerStatusAction) obj;
		if (!Arrays.equals(elementIds, other.elementIds))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (severity == null) {
			if (other.severity != null)
				return false;
		} else if (!severity.equals(other.severity))
			return false;
		return true;
	}

}
