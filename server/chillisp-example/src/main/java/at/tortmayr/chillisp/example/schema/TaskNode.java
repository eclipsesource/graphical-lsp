package at.tortmayr.chillisp.example.schema;

import io.typefox.sprotty.api.SNode;

public class TaskNode extends SNode {
	private String name;
	private boolean expanded;
	private int duration;
	private String taskType;
	private String reference;

	public TaskNode() {
		setType("node:task");
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

}
