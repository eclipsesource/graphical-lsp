package at.tortmayr.chillisp.api.type;

import io.typefox.sprotty.api.SModelElement;

public class Match {
	private SModelElement left;
	private SModelElement right;
	private String leftParentId;
	private String rightParentId;

	public Match() {

	}

	public Match(SModelElement left, SModelElement right, String leftParentId, String rightParentId) {
		super();
		this.left = left;
		this.right = right;
		this.leftParentId = leftParentId;
		this.rightParentId = rightParentId;
	}

	public SModelElement getLeft() {
		return left;
	}

	public SModelElement getRight() {
		return right;
	}

	public String getLeftParentId() {
		return leftParentId;
	}

	public String getRightParentId() {
		return rightParentId;
	}

	public void setLeft(SModelElement left) {
		this.left = left;
	}

	public void setRight(SModelElement right) {
		this.right = right;
	}

	public void setLeftParentId(String leftParentId) {
		this.leftParentId = leftParentId;
	}

	public void setRightParentId(String rightParentId) {
		this.rightParentId = rightParentId;
	}

}
