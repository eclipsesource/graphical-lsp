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

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.eclipse.sprotty.SModelRoot;

import com.eclipsesource.glsp.api.model.IModelSaver;
import com.eclipsesource.glsp.api.provider.IModelTypeConfigurationProvider;
import com.google.gson.Gson;
import com.google.inject.Inject;

public class SModelSaver implements IModelSaver<SModelRoot> {
	private static final String SCHEME_FILE = "file";
	private static Logger LOGGER = Logger.getLogger(SModelSaver.class);
	@Inject
	private IModelTypeConfigurationProvider modelTypeConfigurationProvider;

	@Override
	public boolean saveModel(String fileURI, SModelRoot model) {
		URI uri = URI.create(fileURI);
		if (uri.getScheme().equalsIgnoreCase(SCHEME_FILE)) {
			try {
				File file = new File(uri.getSchemeSpecificPart());
				Gson gson = modelTypeConfigurationProvider.configureGSON().create();
				FileUtils.writeStringToFile(file, gson.toJson(model, GLSPGraph.class), "UTF8");
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}
		return false;
	}
}
