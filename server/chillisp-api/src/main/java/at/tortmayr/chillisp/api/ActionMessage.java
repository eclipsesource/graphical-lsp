package at.tortmayr.chillisp.api;

public class ActionMessage {

	private IAction action;
	private String clientId;

	public ActionMessage(String clientId, IAction action) {
		this.clientId = clientId;
		this.action = action;
	}

	public IAction getAction() {
		return action;
	}

	public String getClientId() {
		return clientId;
	}

	@Override
	public String toString() {
		return "ActionMessage [action=" + action.getKind() + ", clientId=" + clientId + "]";
	}
	
	

}
