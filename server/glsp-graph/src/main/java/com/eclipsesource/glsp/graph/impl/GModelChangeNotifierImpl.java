package com.eclipsesource.glsp.graph.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EContentAdapter;

import com.eclipsesource.glsp.graph.GModelChangeNotifier;
import com.eclipsesource.glsp.graph.GModelListener;

public class GModelChangeNotifierImpl extends EContentAdapter implements GModelChangeNotifier {

	private List<GModelListener> listeners = new CopyOnWriteArrayList<>();
	
	public GModelChangeNotifierImpl(EObject target) {
		target.eAdapters().add(this);
	}

	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		listeners.forEach(listener -> listener.notifyChanged(notification));
	}

	@Override
	public void addListener(GModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(GModelListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public boolean isAdapterForType(Object type) {
		return GModelChangeNotifierImpl.class.equals(type);
	}

}
