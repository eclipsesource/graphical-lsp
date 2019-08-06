/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.example.modelserver.workflow.model;

import static com.eclipsesource.glsp.api.utils.ServerStatusUtil.getDetails;
import static com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerModelFactory.OPTION_WORKFLOW_INDEX;
import static com.eclipsesource.glsp.example.modelserver.workflow.model.WorkflowModelServerModelFactory.WORKFLOW_INDEX_DEFAULT;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.jetbrains.annotations.NotNull;

import com.eclipsesource.glsp.api.action.ActionDispatcher;
import com.eclipsesource.glsp.api.action.kind.RequestBoundsAction;
import com.eclipsesource.glsp.api.action.kind.ServerStatusAction;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.types.ServerStatus;
import com.eclipsesource.glsp.api.types.ServerStatus.Severity;
import com.eclipsesource.glsp.api.utils.ClientOptions;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement;
import com.eclipsesource.modelserver.client.Response;
import com.eclipsesource.modelserver.client.SubscriptionListener;
import com.google.common.collect.Lists;

public class WorkflowSubscriptionListener implements SubscriptionListener {
	private static Logger LOG = Logger.getLogger(WorkflowSubscriptionListener.class);
	private ActionDispatcher actionDispatcher;
	private WorkflowModelServerAccess modelServerAccess;
	private GraphicalModelState modelState;

	public WorkflowSubscriptionListener(GraphicalModelState modelState, WorkflowModelServerAccess modelServerAccess,
			ActionDispatcher actionDispatcher) {
		this.actionDispatcher = actionDispatcher;
		this.modelServerAccess = modelServerAccess;
		this.modelState = modelState;
	}

	@Override
	public void onOpen(Response<String> response) {
	}

	@Override
	public void onMessage(String response) {
		LOG.debug("Update from model server received");
		WorkflowFacade facade = modelServerAccess.getWorkflowFacade();
		Resource semanticResource = facade.getSemanticResource();
		Resource notationResource = facade.getNotationResource();

		// Update the resource with the model information received from the server
		if (!updateResource(semanticResource, response)) {
			return;
		}

		// Clear outdated resolved proxies of notation resource
		Lists.newArrayList(notationResource.getAllContents()).stream().filter(DiagramElement.class::isInstance)
				.map(DiagramElement.class::cast).forEach(e -> e.getSemanticElement().setResolvedElement(null));

		// Set the corresponding workflow
		Optional<Integer> givenWorkflowIndex = ClientOptions.getIntValue(modelState.getClientOptions(),
				OPTION_WORKFLOW_INDEX);
		int workflowIndex = givenWorkflowIndex.orElse(WORKFLOW_INDEX_DEFAULT);
		facade.setCurrentWorkflowIndex(workflowIndex);

		// Re-populate GModel and initiate a client model update
		MappedGModelRoot mappedGModelRoot = WorkflowModelServerModelFactory
				.populate(modelServerAccess.getWorkflowFacade(), modelState);
		modelServerAccess.setNodeMapping(mappedGModelRoot.getMapping());
		actionDispatcher.send(modelState.getClientId(), new RequestBoundsAction(modelState.getRoot()));

	}

	private boolean updateResource(Resource semanticResource, String modelAsXmi) {
		try {
			semanticResource.unload();
			semanticResource.load(IOUtils.toInputStream(modelAsXmi, "UTF8"), Collections.emptyMap());
			return true;

		} catch (IOException e) {
			String errorMsg = "Error occured during update process of resource with URI: " + semanticResource.getURI();
			LOG.error(errorMsg, e);
			actionDispatcher.dispatch(modelState.getClientId(),
					new ServerStatusAction(new ServerStatus(Severity.ERROR, errorMsg)));
			return false;
		}
	}

	@Override
	public void onClosing(int code, @NotNull String reason) {
	}

	@Override
	public void onFailure(Throwable t) {
		String errorMsg = "Subscribtion connection to modelserver failed!";
		actionDispatcher.send(modelState.getClientId(),
				new ServerStatusAction(new ServerStatus(Severity.ERROR, errorMsg, getDetails(t))));
		LOG.error(errorMsg, t);
	}

	@Override
	public void onClosed(int code, @NotNull String reason) {
		String errorMsg = "Subscribtion connection to modelserver has been closed!";
		actionDispatcher.send(modelState.getClientId(),
				new ServerStatusAction(new ServerStatus(Severity.ERROR, errorMsg, reason)));
		LOG.error(errorMsg + "\n" + reason);
	}

	@Override
	public void onFailure(Throwable t, Response<String> response) {
		String errorMsg = "Subscribtion connection to modelserver failed:" + "\n" + response;
		actionDispatcher.send(modelState.getClientId(),
				new ServerStatusAction(new ServerStatus(Severity.ERROR, errorMsg, getDetails(t))));
		LOG.error(errorMsg, t);
	}

	public static Stream<?> toStream(Iterable<?> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false);
	}

}
