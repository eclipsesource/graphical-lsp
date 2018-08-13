/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *    
 *  Contributors:
 *  	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.glsp.server;

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

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;

import com.google.gson.GsonBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;

import at.tortmayr.glsp.api.GraphicalLanguageClient;
import at.tortmayr.glsp.api.GraphicalLanguageServer;
import at.tortmayr.glsp.api.json.ActionTypeAdapter;
import at.tortmayr.glsp.server.di.ServerModule;

public class ServerLauncher {

	private String host;
	private int port;
	private ServerModule[] modules;

	public ServerLauncher(String host, int port, ServerModule... modules) {
		this.modules = modules;
		this.host = host;
		this.port = port;
	}

	public void run() throws IOException, InterruptedException, ExecutionException {
		Injector injector = Guice.createInjector(modules);
		AsynchronousServerSocketChannel serverSocket = AsynchronousServerSocketChannel.open()
				.bind(new InetSocketAddress(host, port));
		ExecutorService threadPool = Executors.newCachedThreadPool();
		System.out.println("The graphical server launcher is ready to accept new client requests");
		while (true) {
			AsynchronousSocketChannel socketChannel = serverSocket.accept().get();
			InputStream in = Channels.newInputStream(socketChannel);
			OutputStream out = Channels.newOutputStream(socketChannel);

			Consumer<GsonBuilder> configureGson = (GsonBuilder builder) -> ActionTypeAdapter.configureGson(builder);
			Function<MessageConsumer, MessageConsumer> wrapper = (MessageConsumer it) -> {
				return it;
			};
			GraphicalLanguageServer languageServer = injector.getInstance(GraphicalLanguageServer.class);
			Launcher<GraphicalLanguageClient> launcher = Launcher.createIoLauncher(languageServer,
					GraphicalLanguageClient.class, in, out, threadPool, wrapper, configureGson);

			languageServer.connect(launcher.getRemoteProxy());
			launcher.startListening();
			System.out.println("Started language server for client " + socketChannel.getRemoteAddress());
		}

	}
}
