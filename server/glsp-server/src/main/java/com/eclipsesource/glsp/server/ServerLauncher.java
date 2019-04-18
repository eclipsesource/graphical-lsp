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
package com.eclipsesource.glsp.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.nio.channels.CompletionHandler;
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

import com.eclipsesource.glsp.api.json.GsonConfigurator;
import com.eclipsesource.glsp.api.jsonrpc.GLSPClient;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.google.gson.GsonBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ServerLauncher {
	private static Logger log = Logger.getLogger(ServerLauncher.class);
	private String host;
	private int port;
	private DefaultGLSPModule module;

	private ExecutorService threadPool;
	private AsynchronousServerSocketChannel serverSocket;
	private CompletableFuture<Void> onShutdown;

	public ServerLauncher(String host, int port, DefaultGLSPModule module) {
		this.module = module;
		this.host = host;
		this.port = port;
	}

	public void run() throws IOException, InterruptedException, ExecutionException {
		Future<Void> onClose = asyncRun();
		onClose.get();
		log.info("Stopped language server");
	}

	public Future<Void> asyncRun() throws IOException, InterruptedException, ExecutionException {
		onShutdown = new CompletableFuture<Void>();

		serverSocket = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(host, port));
		threadPool = Executors.newCachedThreadPool();

		CompletionHandler<AsynchronousSocketChannel, Void> handler = new CompletionHandler<AsynchronousSocketChannel, Void>() {
			public void completed(AsynchronousSocketChannel result, Void attachment) {
				serverSocket.accept(null, this); // Prepare for the next connection
				ServerLauncher.this.createClientConnection(result);
			}

			public void failed(Throwable exc, Void attachment) {
				log.error("Client Connection Failed: " + exc.getMessage(), exc);
			}
		};

		serverSocket.accept(null, handler);
		log.info("The graphical server launcher is ready to accept new client requests");

		return onShutdown;
	}

	private void createClientConnection(AsynchronousSocketChannel socketChannel) {
		Injector injector = Guice.createInjector(module);
		GsonConfigurator gsonConf = injector.getInstance(GsonConfigurator.class);

		InputStream in = Channels.newInputStream(socketChannel);
		OutputStream out = Channels.newOutputStream(socketChannel);

		Consumer<GsonBuilder> configureGson = (GsonBuilder builder) -> gsonConf.configureGsonBuilder(builder);
		Function<MessageConsumer, MessageConsumer> wrapper = Function.identity();
		GLSPServer languageServer = injector.getInstance(GLSPServer.class);

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

	public void stop() throws IOException {
		log.info("Stopping all connections to the language server...");
		serverSocket.close();
		threadPool.shutdown();
		onShutdown.complete(null);
		log.info("Stopped language server");
	}
}
