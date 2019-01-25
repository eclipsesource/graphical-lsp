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
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channels;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
	private static Logger log= Logger.getLogger(ServerLauncher.class);
	private String host;
	private int port;
	private ServerModule module;

	public ServerLauncher(String host, int port, ServerModule module) {
		this.module = module;
		this.host = host;
		this.port = port;
	}

	public void run() throws IOException, InterruptedException, ExecutionException {
		Injector injector = Guice.createInjector(module);
		GsonConfigurator gsonConf = injector.getInstance(GsonConfigurator.class);
		AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open()
				.bind(new InetSocketAddress(host, port));
		ExecutorService threadPool = Executors.newCachedThreadPool();
		log.info("The graphical server launcher is ready to accept new client requests");
		while (true) {
			AsynchronousSocketChannel socketChannel = serverSocket.accept().get();
			InputStream in = Channels.newInputStream(socketChannel);
			OutputStream out = Channels.newOutputStream(socketChannel);

			Consumer<GsonBuilder> configureGson = (GsonBuilder builder) -> gsonConf.configureGsonBuilder(builder);
			Function<MessageConsumer, MessageConsumer> wrapper = (MessageConsumer it) -> {
				return it;
			};
			GLSPServer languageServer = injector.getInstance(GLSPServer.class);
			Launcher<GLSPClient> launcher = Launcher.createIoLauncher(languageServer,
					GLSPClient.class, in, out, threadPool, wrapper, configureGson);

			languageServer.connect(launcher.getRemoteProxy());
			launcher.startListening();
			log.info("Started language server for client " + socketChannel.getRemoteAddress());
		}

	}
}
