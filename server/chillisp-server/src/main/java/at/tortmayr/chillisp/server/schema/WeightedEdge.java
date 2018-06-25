package at.tortmayr.chillisp.server.schema;

import io.typefox.sprotty.api.SEdge;

public class WeightedEdge extends SEdge {
	public WeightedEdge() {
		setType("edge:weighted");
	}

	private String probability;

	public String getProbability() {
		return probability;
	}

	public void setProbability(String probability) {
		this.probability = probability;
	}

}
