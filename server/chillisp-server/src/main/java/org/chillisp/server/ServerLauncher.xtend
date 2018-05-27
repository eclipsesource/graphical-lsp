package org.chillisp.server

import at.tortmayr.chillisp.api.ActionMessage
import at.tortmayr.chillisp.api.IGraphicalLanguageServer.Provider
import java.util.Objects
import javax.websocket.CloseReason
import javax.websocket.EndpointConfig
import javax.websocket.Session
import javax.websocket.server.ServerEndpointConfig
import org.chillisp.websocket.GraphicalLanguageServerEndpoint
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer
import org.chillisp.server.ServerLauncher.TestServerEndpoint
import at.tortmayr.chillisp.api.impl.DefaultGraphicalLanguageServer

class ServerLauncher {
	static class TestServerEndpoint extends GraphicalLanguageServerEndpoint {
		override void onOpen(Session session, EndpointConfig config) {
			println('''Opened connection [«session.getId()»]''')
			super.onOpen(session, config)
		}

		override void onClose(Session session, CloseReason closeReason) {
			println('''Closed connection [«session.getId()»]''')
			super.onClose(session, closeReason)
		}

		override void accept(ActionMessage message) {
			println('''SERVER:«message»''')
			super.accept(message)
		}

		override protected void fireMessageReceived(ActionMessage message) {
			println('''CLIENT:«message»''')
			super.fireMessageReceived(message)
		}

	}

	static class TestEndpointConfigurator extends ServerEndpointConfig.Configurator {

		override <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {

			val provider = new Provider {

				override getGraphicalLanguageServer(String clientId) {
					val server = new DefaultGraphicalLanguageServer(clientId)
//					server.modelFactory = new ExampleModelFactory
					return server
				}

			}
			super.getEndpointInstance(endpointClass) => [ instance |
				val endpoint = instance as TestServerEndpoint
				endpoint.graphicalLanguageServerProvider = provider
				endpoint.exceptionHandler = [e|println(e)]
			]
		}
	}

	def static void main(String[] args) {
		val server = new Server(8080)

		val context = new ServletContextHandler(ServletContextHandler.SESSIONS)
		context.setContextPath("/")
		server.setHandler(context)

		val container = WebSocketServerContainerInitializer.configureContext(context);

		var builder = ServerEndpointConfig.Builder.create(TestServerEndpoint, "/diagram");
		builder = builder.configurator(new TestEndpointConfigurator)
		val config = builder.build
		container.addEndpoint(config)

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
