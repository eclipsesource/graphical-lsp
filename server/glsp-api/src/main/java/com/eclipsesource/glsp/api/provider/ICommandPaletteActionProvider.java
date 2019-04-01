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
package com.eclipsesource.glsp.api.provider;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.types.LabeledAction;

@FunctionalInterface
public interface ICommandPaletteActionProvider {
	Set<LabeledAction> getActions(SModelRoot model, List<String> selectedElementsIDs);
	
	public static class NullImpl implements ICommandPaletteActionProvider {
		@Override
		public Set<LabeledAction> getActions(SModelRoot model, List<String> selectedElementsIDs) {
			return Collections.emptySet();
		}
	}
}
