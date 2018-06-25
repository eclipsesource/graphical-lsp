
package at.tortmayr.chillisp.server;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import at.tortmayr.chillisp.server.schema.ActivityNode;
import at.tortmayr.chillisp.server.schema.Icon;
import at.tortmayr.chillisp.server.schema.TaskNode;
import at.tortmayr.chillisp.server.schema.WeightedEdge;
import io.typefox.sprotty.api.HtmlRoot;
import io.typefox.sprotty.api.PreRenderedElement;
import io.typefox.sprotty.api.SCompartment;
import io.typefox.sprotty.api.SEdge;
import io.typefox.sprotty.api.SGraph;
import io.typefox.sprotty.api.SLabel;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.server.json.EnumTypeAdapter;
import io.typefox.sprotty.server.json.PropertyBasedTypeAdapter;

/**
 * Gson type adapter for sprotty actions.
 */
public class SModelElementTypeAdapter extends PropertyBasedTypeAdapter<SModelElement> {

	/**
	 * Configure a Gson builder with the default sprotty actions. If you need to
	 * register your own action classes, create an instance of {@link Factory}
	 * instead and call {@link Factory#addModelKind(String, Class)}.
	 */
	public static GsonBuilder configureGson(GsonBuilder gsonBuilder) {
		gsonBuilder.registerTypeAdapterFactory(new SModelElementTypeAdapter.Factory())
				.registerTypeAdapterFactory(new EnumTypeAdapter.Factory());
		return gsonBuilder;
	}

	/**
	 * Type adapter factory for sprotty actions. Action classes are registered via
	 * their {@code kind} attribute using {@link #addModelKind(String, Class)}.
	 */
	public static class Factory implements TypeAdapterFactory {

		private final Map<String, Class<? extends SModelElement>> modelTypes = new HashMap<>();

		public Factory() {
			addDefaultModelTypes();
		}

		protected void addDefaultModelTypes() {
			addModelKind("graph", SGraph.class);
			addModelKind("label:heading", SLabel.class);
			addModelKind("label:text", SLabel.class);
			addModelKind("comp:comp", SCompartment.class);
			addModelKind("comp:header", SCompartment.class);
			addModelKind("label:icon", SLabel.class);
			addModelKind("edge", SEdge.class);
			addModelKind("html", HtmlRoot.class);
			addModelKind("pre-rendered", PreRenderedElement.class);
			addModelKind("edge:weighted", WeightedEdge.class);
			addModelKind("icon", Icon.class);
			addModelKind("node:activity", ActivityNode.class);
			addModelKind("node:task", TaskNode.class);
		}

		public void addModelKind(String kind, Class<? extends SModelElement> clazz) {
			modelTypes.put(kind, clazz);
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
			if (!SModelElement.class.isAssignableFrom(typeToken.getRawType()))
				return null;
			return (TypeAdapter<T>) new SModelElementTypeAdapter(gson, modelTypes);
		}
	}

	private final Map<String, Class<? extends SModelElement>> modelKinds;

	public SModelElementTypeAdapter(Gson gson, Map<String, Class<? extends SModelElement>> modelKinds) {
		super(gson, "type");
		this.modelKinds = modelKinds;
	}

	@Override
	protected SModelElement createInstance(String type) {
		Class<? extends SModelElement> clazz = modelKinds.get(type);
		if (clazz == null)
			throw new IllegalArgumentException("Unknown model type: " + type);
		try {
			Constructor<? extends SModelElement> constructor = clazz.getConstructor();
			SModelElement element= constructor.newInstance();
			element.setType(type);
			return element;
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("SModelElement class does not have a default constructor.", e);
		} catch (Exception e) {
			throw new RuntimeException("Unable to invoke SModelElement constructor", e);
		}
	}

}