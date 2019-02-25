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
package com.eclipsesource.glsp.server.model;


import java.net.URI;
import java.util.List;

import org.eclipse.sprotty.SModelRoot;

/**
 * A generator that can handle files with specific extensions and generate the
 * corresponding SModel from an URI
 * 
 * @author Tobias Ortmayr<tortmayr@eclipsesource.com>
 *
 */
public interface IModelLoader {
	default int getPriority() {
		return 0;
	}

	default boolean handles(URI uri) {
		return getExtensions().stream().anyMatch(ext -> uri.toString().endsWith(ext));
	}

	List<String> getExtensions();

	SModelRoot generate(URI uri);
}
