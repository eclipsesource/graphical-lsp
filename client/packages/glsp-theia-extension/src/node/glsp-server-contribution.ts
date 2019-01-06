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
import { BaseLanguageServerContribution, LanguageServerContribution } from '@theia/languages/lib/node';
import { injectable } from 'inversify';
export const GLSPServerContribution = Symbol.for('GLSPServerContribution')
export interface GLSPServerContribution extends LanguageServerContribution { }

@injectable()
export abstract class BaseGLSPServerContribution extends BaseLanguageServerContribution implements GLSPServerContribution {

}