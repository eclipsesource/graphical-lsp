package at.tortmayr.chillisp.api;

import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;

public interface GraphicalLanguageClient {

	@JsonNotification("client/process")
	void process(ActionMessage message);
}
