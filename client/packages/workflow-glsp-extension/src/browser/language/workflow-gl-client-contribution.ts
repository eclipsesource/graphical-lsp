/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { BaseGraphicalLanguageClientContribution } from "glsp-theia-extension/lib/browser";
import { injectable } from "inversify";
import { WorkflowLanguage } from "../../common/workflow-language";
@injectable()
export class WorkflowGLClientContribution extends BaseGraphicalLanguageClientContribution {
    readonly id = WorkflowLanguage.Id
    readonly name = WorkflowLanguage.Name
}