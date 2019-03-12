package com.eclipsesource.glsp.ecore.util;

public class ThreadUtil {
	public static void runDeferred(Runnable runnable) {
		new Thread(runnable).run();
	}
}
