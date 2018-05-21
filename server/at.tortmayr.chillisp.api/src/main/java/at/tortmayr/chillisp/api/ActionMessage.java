package at.tortmayr.chillisp.api;

public class ActionMessage {

	private Action action;
	private String clientId;

	public ActionMessage(String clientId, Action action) {
		this.clientId = clientId;
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	public String getClientId() {
		return clientId;
	}

}
