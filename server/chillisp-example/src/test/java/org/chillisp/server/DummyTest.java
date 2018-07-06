package org.chillisp.server;

import at.tortmayr.chillisp.api.type.Tool;
import at.tortmayr.chillisp.example.tools.AutomatedTaskTool;

public class DummyTest {

	public static void main(String[] args) {
		Tool tool= new AutomatedTaskTool();
		System.out.println(tool.getId());
	}
}
