package at.tortmayr.chillisp.api.tools;

import java.util.Arrays;
import java.util.stream.Collectors;

import at.tortmayr.chillisp.api.type.Tool;

public abstract class CreationTool implements Tool {
	public static final String TYPE = "creation-tool";

	@Override
	public String getId() {
		return generateId();
	}

	protected String generateId() {
		String className = this.getClass().getSimpleName();
		// Split camel case
		String id = Arrays.stream(className.split("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])"))
				.collect(Collectors.joining("-"));
		return id;
	}

	@Override
	public String getToolType() {
		return CreationTool.TYPE;
	}

}
