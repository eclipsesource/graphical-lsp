package at.tortmayr.chillisp.api.di;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.google.inject.Binder;
import com.google.inject.Module;

public abstract class AbstractGenericModule implements Module {
	public static final Logger LOGGER = Logger.getLogger(AbstractGenericModule.class);

	@Override
	public void configure(Binder binder) {
		Module compound = getBindings();
		compound.configure(binder);
	}

	public final CompoundModule getBindings() {
		Method[] methods = this.getClass().getMethods();
		CompoundModule compoundModule = new CompoundModule();
		Arrays.stream(methods).forEach(method -> {
			try {
				if (method.getName().startsWith("bind") || method.getName().startsWith("inject")) {
					compoundModule.add(new MethodBasedModule(method, this) {
					});
				}
			} catch (Exception e) {
				LOGGER.warn("Trying to use method " + method.toGenericString() + " for binding failed");
			}
		});
		return compoundModule;
	}

}
