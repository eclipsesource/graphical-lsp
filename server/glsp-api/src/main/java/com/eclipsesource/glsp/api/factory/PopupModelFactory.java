/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.glsp.api.factory;

import org.eclipse.sprotty.SModelElement;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.action.kind.RequestPopupModelAction;

public interface PopupModelFactory {
	SModelRoot createPopuModel(SModelElement element, RequestPopupModelAction action);

	public static class NullImpl implements PopupModelFactory {

		@Override
		public SModelRoot createPopuModel(SModelElement element, RequestPopupModelAction action) {
			return null;
		}

	}
}
