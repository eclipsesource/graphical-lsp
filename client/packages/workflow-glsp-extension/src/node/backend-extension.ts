/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { LanguageServerContribution } from "@theia/languages/lib/node";
import { ContainerModule } from "inversify";
import { WorkflowGLServerContribution } from "./workflow-gl-server-contribution";

export default new ContainerModule(bind => {
    bind(LanguageServerContribution).to(WorkflowGLServerContribution).inSingletonScope()
})