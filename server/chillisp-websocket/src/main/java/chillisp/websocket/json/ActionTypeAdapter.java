package chillisp.websocket.json;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import at.tortmayr.chillisp.api.IAction;
import at.tortmayr.chillisp.api.actions.Action;
import at.tortmayr.chillisp.api.actions.RequestModelAction;
import at.tortmayr.chillisp.api.actions.SetModelAction;
import io.typefox.sprotty.server.json.EnumTypeAdapter;
import io.typefox.sprotty.server.json.PropertyBasedTypeAdapter;

public class ActionTypeAdapter extends PropertyBasedTypeAdapter<IAction> {

	public static GsonBuilder configureGson(GsonBuilder gsonBuilder) {
		gsonBuilder.registerTypeAdapterFactory(new ActionTypeAdapter.Factory())
				.registerTypeAdapterFactory(new EnumTypeAdapter.Factory());
		return gsonBuilder;
	}

	private final Map<String, Class<? extends IAction>> actionKinds;

	public ActionTypeAdapter(Gson gson, Map<String, Class<? extends IAction>> actionKinds) {
		super(gson, "kind");
		this.actionKinds = actionKinds;
	}

	@Override
	protected IAction createInstance(String kind) {
		Class<? extends IAction> clazz = actionKinds.get(kind);
		if (clazz == null)
			throw new IllegalArgumentException("Unknown action kind: " + kind);
		try {
			Constructor<? extends IAction> constructor = clazz.getConstructor();
			return constructor.newInstance();
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Action class does not have a default constructor.", e);
		} catch (Exception e) {
			throw new RuntimeException("Unable to invoke action constructor", e);
		}
	}

	public static class Factory implements TypeAdapterFactory {

		private final Map<String, Class<? extends IAction>> actionKinds = new HashMap<>();

		public Factory() {
			addDefaultActionKinds();
		}

		protected void addDefaultActionKinds() {
			addActionKind(Action.Kind.REQUEST_MODEL, RequestModelAction.class);
			addActionKind(Action.Kind.SET_MODEL, SetModelAction.class);
		}

		public void addActionKind(String kind, Class<? extends IAction> clazz) {
			actionKinds.put(kind, clazz);
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
			if (!IAction.class.isAssignableFrom(typeToken.getRawType()))
				return null;
			return (TypeAdapter<T>) new ActionTypeAdapter(gson, actionKinds);
		}

	}

}
