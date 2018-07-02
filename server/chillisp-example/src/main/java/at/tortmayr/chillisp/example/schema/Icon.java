package at.tortmayr.chillisp.example.schema;

import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SShapeElement;

public class Icon extends SShapeElement{
	private String layout;
	
	public Icon() {
		setType("icon");
		setPosition(new Point());
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}
	
	
}
