/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.chillisp.api;

import at.tortmayr.chillisp.api.actions.RequestPopupModelAction;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelRoot;

public interface IPopupModelFactory {
	SModelRoot createPopuModel(SModelElement element, RequestPopupModelAction action, IGraphicalLanguageServer server);

	public static class NullImpl implements IPopupModelFactory {

		@Override
		public SModelRoot createPopuModel(SModelElement element, RequestPopupModelAction action,
				IGraphicalLanguageServer server) {
			return null;
		}

	}
}
