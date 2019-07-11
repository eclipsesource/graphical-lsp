package com.eclipsesource.glsp.server.launch;

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

import com.eclipsesource.glsp.api.di.GLSPModule;
import com.eclipsesource.glsp.api.json.GsonConfigurator;
import com.eclipsesource.glsp.api.jsonrpc.GLSPClient;
import com.eclipsesource.glsp.api.jsonrpc.GLSPServer;
import com.google.gson.GsonBuilder;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class DefaultGLSPServerLauncher extends GLSPServerLauncher {
	private static Logger log = Logger.getLogger(DefaultGLSPServerLauncher.class);

	private ExecutorService threadPool;
	private AsynchronousServerSocketChannel serverSocket;
	private CompletableFuture<Void> onShutdown;

	public DefaultGLSPServerLauncher(GLSPModule module) {
		super(module);
	}

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
			public void completed(AsynchronousSocketChannel result, Void attachment) {
				serverSocket.accept(null, this); // Prepare for the next connection
				DefaultGLSPServerLauncher.this.createClientConnection(result);
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
		Injector injector = Guice.createInjector(getGLSPModule());
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