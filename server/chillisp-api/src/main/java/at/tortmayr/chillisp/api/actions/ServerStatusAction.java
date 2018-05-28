package at.tortmayr.chillisp.api.actions;

import at.tortmayr.chillisp.api.ActionRegistry;
import io.typefox.sprotty.api.ServerStatus;

public class ServerStatusAction extends Action {
	private String severity;
	private String message;


	public ServerStatusAction() {
		super(ActionRegistry.Kind.SERVER_STATUS);
	}

	public ServerStatusAction(ServerStatus status) {
		this();
		this.severity = status.getSeverity().toString();
		this.message = status.getMessage();
	}

	public String getSeverity() {
		return severity;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
