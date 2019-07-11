package com.eclipsesource.glsp.server.command;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.command.BasicCommandStack;

public class GModelCommandStack extends BasicCommandStack {
	
	private static Logger LOG = Logger.getLogger(GModelCommandStack.class);

	@Override
	protected void handleError(Exception exception) {
		LOG.error("Error while executing command", exception);
	}

}
