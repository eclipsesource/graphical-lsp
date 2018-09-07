/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { BaseLanguageServerContribution, LanguageServerContribution } from '@theia/languages/lib/node';
import { injectable } from 'inversify';
export const GraphicalLanguageServerContribution = Symbol('GraphicalLanguageServerContribution')
export interface GraphicalLanguageServerContribution extends LanguageServerContribution { }

@injectable()
export abstract class BaseGraphicalLanguageServerContribution extends BaseLanguageServerContribution implements GraphicalLanguageServerContribution {

}