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
package com.eclipsesource.glsp.example.workflow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.nio.channels.CompletionHandler;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;

import com.eclipsesource.glsp.api.action.kind.RequestModelAction;
import com.eclipsesource.glsp.api.di.GLSPModule;
import com.eclipsesource.glsp.api.factory.GraphGsonConfiguratorFactory;
import com.eclipsesource.glsp.api.factory.ModelFactory;
import com.eclipsesource.glsp.api.json.GsonConfigurator;
import com.eclipsesource.glsp.api.jsonrpc.GLSPClient;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.api.model.ModelStateProvider;
import com.eclipsesource.glsp.api.utils.ClientOptions;
import com.eclipsesource.glsp.graph.GGraph;
import com.eclipsesource.glsp.graph.GModelRoot;
import com.eclipsesource.glsp.server.launch.DefaultGLSPServerLauncher;
import com.eclipsesource.glsp.server.launch.GLSPServerLauncher;
import com.google.gson.GsonBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;

import io.javalin.Javalin;

public class RestGLSPServerLauncher extends GLSPServerLauncher {
	private static Logger log = Logger.getLogger(DefaultGLSPServerLauncher.class);

	private ExecutorService threadPool;
	private AsynchronousServerSocketChannel serverSocket;
	private CompletableFuture<Void> onShutdown;

	public RestGLSPServerLauncher(GLSPModule module) {
		super(module);
	}

	@Override
	public void run(String hostname, int port) {
		Future<Void> onClose;
		try {
			onClose = asyncRun(hostname, port);
			onClose.get();
			log.info("Stopped language server");
		} catch (IOException | InterruptedException | ExecutionException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

	}

	public Future<Void> asyncRun(String hostname, int port)
			throws IOException, InterruptedException, ExecutionException {
		onShutdown = new CompletableFuture<Void>();

		serverSocket = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(hostname, port));
		threadPool = Executors.newCachedThreadPool();

		CompletionHandler<AsynchronousSocketChannel, Void> handler = new CompletionHandler<AsynchronousSocketChannel, Void>() {
			@Override
			public void completed(AsynchronousSocketChannel result, Void attachment) {
				serverSocket.accept(null, this); // Prepare for the next connection
				RestGLSPServerLauncher.this.createClientConnection(result);
			}

			@Override
			public void failed(Throwable exc, Void attachment) {
				log.error("Client Connection Failed: " + exc.getMessage(), exc);
			}
		};

		serverSocket.accept(null, handler);
		log.info("The graphical server launcher is ready to accept new client requests");

		return onShutdown;
	}

	private void createClientConnection(AsynchronousSocketChannel socketChannel) {
		Injector injector = Guice.createInjector(getGLSPModule());
		GsonConfigurator gsonConf = injector.getInstance(GsonConfigurator.class);

		InputStream in = Channels.newInputStream(socketChannel);
		OutputStream out = Channels.newOutputStream(socketChannel);

		Consumer<GsonBuilder> configureGson = (GsonBuilder builder) -> gsonConf.configureGsonBuilder(builder);
		Function<MessageConsumer, MessageConsumer> wrapper = Function.identity();
		GLSPServer languageServer = injector.getInstance(GLSPServer.class);

		startRESTServer(injector, 7000);

		Launcher<GLSPClient> launcher = Launcher.createIoLauncher(languageServer, GLSPClient.class, in, out, threadPool,
				wrapper, configureGson);
		languageServer.connect(launcher.getRemoteProxy());
		launcher.startListening();

		try {
			SocketAddress remoteAddress = socketChannel.getRemoteAddress();
			log.info("Started language server for client " + remoteAddress);
		} catch (IOException ex) {
			log.error("Failed to get the remoteAddress for the new client connection: " + ex.getMessage(), ex);
		}
	}

	private void startRESTServer(Injector injector, int port) {
		Javalin app = Javalin.create().start(port);
		app.get("/workflow", ctx -> {
			String clientId = ctx.queryParam("client");
			String sourceUri = ctx.queryParam("uri");

			ModelStateProvider modelStateProvider = injector.getInstance(ModelStateProvider.class);
			ModelFactory modelFactory = injector.getInstance(ModelFactory.class);

			Optional<GraphicalModelState> state = modelStateProvider.getModelState(clientId);
			if (state.isEmpty()) {
				ctx.json("{ error: Could not retrieve state for client " + clientId + " }");
				return;
			}
			RequestModelAction action = new RequestModelAction(new HashMap<>());
			action.getOptions().put(ClientOptions.SOURCE_URI, sourceUri);
			GModelRoot root = modelFactory.loadModel(action, state.get());

			GraphGsonConfiguratorFactory factory = injector.getInstance(GraphGsonConfiguratorFactory.class);
			String json = factory.configureGson().create().toJson(root, GGraph.class);

			ctx.json(json);
		});
	}

	@Override
	public void shutdown() {
		log.info("Stopping all connections to the language server...");
		if (serverSocket.isOpen()) {
			try {
				serverSocket.close();
			} catch (IOException e) {
				log.error("Failed to close serverSocket: " + e.getMessage(), e);

			}
		}

		threadPool.shutdown();
		onShutdown.complete(null);
		log.info("Stopped language server");
	}
}
