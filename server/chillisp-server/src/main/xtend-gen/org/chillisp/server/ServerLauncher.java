package org.chillisp.server;

import at.tortmayr.chillisp.api.ActionMessage;
import at.tortmayr.chillisp.api.IGraphicalLanguageServer;
import at.tortmayr.chillisp.api.impl.DefaultGraphicalLanguageServer;
import java.util.function.Consumer;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpointConfig;
import org.chillisp.websocket.GraphicalLanguageServerEndpoint;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class ServerLauncher {
  public static class TestServerEndpoint extends GraphicalLanguageServerEndpoint {
    @Override
    public void onOpen(final Session session, final EndpointConfig config) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Opened connection [");
      String _id = session.getId();
      _builder.append(_id);
      _builder.append("]");
      InputOutput.<String>println(_builder.toString());
      super.onOpen(session, config);
    }
    
    @Override
    public void onClose(final Session session, final CloseReason closeReason) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("Closed connection [");
      String _id = session.getId();
      _builder.append(_id);
      _builder.append("]");
      InputOutput.<String>println(_builder.toString());
      super.onClose(session, closeReason);
    }
    
    @Override
    public void accept(final ActionMessage message) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("SERVER:");
      _builder.append(message);
      InputOutput.<String>println(_builder.toString());
      super.accept(message);
    }
    
    @Override
    protected void fireMessageReceived(final ActionMessage message) {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("CLIENT:");
      _builder.append(message);
      InputOutput.<String>println(_builder.toString());
      super.fireMessageReceived(message);
    }
  }
  
  public static class TestEndpointConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public <T extends Object> T getEndpointInstance(final Class<T> endpointClass) throws InstantiationException {
      T _xblockexpression = null;
      {
        final IGraphicalLanguageServer.Provider provider = new IGraphicalLanguageServer.Provider() {
          @Override
          public IGraphicalLanguageServer getGraphicalLanguageServer(final String clientId) {
            final DefaultGraphicalLanguageServer server = new DefaultGraphicalLanguageServer(clientId);
            return server;
          }
        };
        T _endpointInstance = super.<T>getEndpointInstance(endpointClass);
        final Procedure1<T> _function = (T instance) -> {
          final ServerLauncher.TestServerEndpoint endpoint = ((ServerLauncher.TestServerEndpoint) instance);
          endpoint.setGraphicalLanguageServerProvider(provider);
          final Consumer<Exception> _function_1 = (Exception e) -> {
            InputOutput.<Exception>println(e);
          };
          endpoint.setExceptionHandler(_function_1);
        };
        _xblockexpression = ObjectExtensions.<T>operator_doubleArrow(_endpointInstance, _function);
      }
      return _xblockexpression;
    }
  }
  
  public static void main(final String[] args) {
    try {
      final Server server = new Server(8080);
      final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
      context.setContextPath("/");
      server.setHandler(context);
      final ServerContainer container = WebSocketServerContainerInitializer.configureContext(context);
      ServerEndpointConfig.Builder builder = ServerEndpointConfig.Builder.create(ServerLauncher.TestServerEndpoint.class, "/diagram");
      ServerLauncher.TestEndpointConfigurator _testEndpointConfigurator = new ServerLauncher.TestEndpointConfigurator();
      builder = builder.configurator(_testEndpointConfigurator);
      final ServerEndpointConfig config = builder.build();
      container.addEndpoint(config);
      try {
        server.start();
        server.join();
      } catch (final Throwable _t) {
        if (_t instanceof Exception) {
          final Exception e = (Exception)_t;
          e.printStackTrace();
        } else {
          throw Exceptions.sneakyThrow(_t);
        }
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
