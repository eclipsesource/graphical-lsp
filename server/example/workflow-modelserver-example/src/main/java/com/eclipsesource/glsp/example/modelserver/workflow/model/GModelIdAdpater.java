/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.example.modelserver.workflow.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

public class GModelIdAdpater extends AdapterImpl {

	public static void addGModelIdAdpater(Notifier target) {
		if (getGModelIdAdapter(target) == null) {
			target.eAdapters().add(new GModelIdAdpater(target));
		}
	}

	public static GModelIdAdpater getGModelIdAdapter(Notifier notifier) {
		List<Adapter> adapters = notifier.eAdapters();
		for (int i = 0, size = adapters.size(); i < size; ++i) {
			Object adapter = adapters.get(i);
			if (adapter instanceof GModelIdAdpater) {
				return (GModelIdAdpater) adapter;
			}
		}
		return null;
	}

	public static Optional<String> getGModelId(Notifier notifier) {
		return Optional.ofNullable(getGModelIdAdapter(notifier)).map(GModelIdAdpater::getGModelId);
	}

	private final String gModelId;

	public GModelIdAdpater(Notifier target) {
		setTarget(target);
		gModelId = generateId(target);
	}

	public String getGModelId() {
		return gModelId;
	}

	protected String generateId(Notifier target) {
		return UUID.randomUUID().toString();
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return type instanceof EObject;
	}

}
