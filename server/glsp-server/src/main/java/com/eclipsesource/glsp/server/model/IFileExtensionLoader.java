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

import java.util.List;
import java.util.Optional;

import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.handler.IHandler;

/**
 * A model loader class for files with matching extensions. The file can either
 * represent an SModel directly or any other related model (semantic, different
 * notation etc.) from which the SModel can be generated.
 *
 * @author Tobias Ortmayr<tortmayr@eclipsesource.com>
 *
 */
public interface IFileExtensionLoader<T> extends IHandler<String> {

	default boolean handles(String sourceURI) {
		return getExtensions().stream().anyMatch(ext -> sourceURI.endsWith(ext));
	}

	List<String> getExtensions();

	Optional<T> loadFromFile(String fileURI, String clientId);

	SModelRoot generate(T sourceModel);

	default SModelRoot loadAndGenerate(String fileURI, String clientId) {
		Optional<T> model = loadFromFile(fileURI,clientId);
		if (model.isPresent()) {
			return generate(model.get());
		}
		return FileBasedModelFactory.emptyRoot();
	}
}
