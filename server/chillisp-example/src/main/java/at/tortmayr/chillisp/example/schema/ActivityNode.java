package at.tortmayr.chillisp.example.schema;

import io.typefox.sprotty.api.SNode;

public class ActivityNode extends SNode {
	public ActivityNode() {
		setType("node:activity");
	}
	private String nodeType;

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
}
