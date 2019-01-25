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
