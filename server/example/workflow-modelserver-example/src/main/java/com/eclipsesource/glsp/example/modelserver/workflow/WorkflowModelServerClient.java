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
package com.eclipsesource.glsp.example.modelserver.workflow;

import java.net.MalformedURLException;
import java.util.concurrent.CompletableFuture;

import com.eclipsesource.modelserver.client.ModelServerClient;
import com.eclipsesource.modelserver.client.Response;
import com.eclipsesource.modelserver.client.SubscriptionListener;

import okhttp3.OkHttpClient;

public class WorkflowModelServerClient extends ModelServerClient {
	private String workspaceRoot;

	public WorkflowModelServerClient(String baseUrl, String workspaceRoot) throws MalformedURLException {
		super(baseUrl);
		this.workspaceRoot = workspaceRoot != null ? workspaceRoot : "";
	}

	public WorkflowModelServerClient(OkHttpClient client, String baseUrl, String workspaceRoot)
			throws MalformedURLException {
		super(client, baseUrl);
		this.workspaceRoot = workspaceRoot;
	}

	@Override
	public CompletableFuture<Response<String>> get(String modelUri) {
		modelUri = toRelativeFileUrl(modelUri);
		return super.get(modelUri);
	}

	@Override
	public CompletableFuture<Response<Boolean>> delete(String modelUri) {
		modelUri = toRelativeFileUrl(modelUri);
		return super.delete(modelUri);
	}

	@Override
	public CompletableFuture<Response<String>> update(String modelUri, String updatedModel) {
		modelUri = toRelativeFileUrl(modelUri);
		return super.update(modelUri, updatedModel);
	}

	@Override
	public CompletableFuture<Response<String>> getSchema(String modelUri) {
		modelUri = toRelativeFileUrl(modelUri);
		return super.getSchema(modelUri);
	}

	@Override
	public void subscribe(String modelUri, SubscriptionListener subscriptionListener) {
		modelUri = toRelativeFileUrl(modelUri);
		super.subscribe(modelUri, subscriptionListener);
	}

	@Override
	public boolean unsubscribe(String modelUri) {
		modelUri = toRelativeFileUrl(modelUri);
		return super.unsubscribe(modelUri);
	}

	private String toRelativeFileUrl(String fileUrl) {
		String relativeUrl = fileUrl.replaceAll(workspaceRoot, "");
		if (relativeUrl.charAt(0) == '/') {
			relativeUrl = relativeUrl.substring(1);
		}
		return relativeUrl;
	}

}
