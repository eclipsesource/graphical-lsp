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
export * from 'sprotty/lib';
export * from './base/command-stack';
export * from './base/edit-config/edit-config';
export * from './features/command-palette/action-provider';
export * from './features/command-palette/command-palette';
export * from './features/execute/execute-command';
export * from './features/execute/model';
export * from './features/hints/action-definition';
export * from './features/hints/type-hints-action-initializer';
export * from './features/nameable/model';
export * from './features/operation/operation-actions';
export * from './features/operation/set-operations';
export * from './features/resize/model';
export * from './features/resize/resize';
export * from './features/save/model';
export * from './features/save/save';
export * from './features/tool-feedback/creation-tool-feedback';
export * from './features/tool-feedback/model';
export * from './features/tool-manager/tool';
export * from './features/tool-manager/tool-manager';
export * from './features/tools/change-bounds-tool';
export * from './features/tools/creation-tool';
export * from './features/tools/default-tools';
export * from './features/tools/delete-tool';
export * from './lib/model';
export * from './types';
export * from './utils/array-utils';
export * from './utils/smodel-util';
export * from './utils/viewpoint-util';
export { saveModule, toolManagerModule, executeModule, toolFeedbackModule, defaultGLSPModule, modelHintsModule, resizeCommandModule, commandPaletteModule };


import defaultGLSPModule from './base/di.config';
import commandPaletteModule from './features/command-palette/di.config';
import executeModule from './features/execute/di.config';
import modelHintsModule from './features/hints/di.config';
import resizeCommandModule from './features/resize/di.config';
import saveModule from './features/save/di.config';
import toolFeedbackModule from './features/tool-feedback/di.config';
import toolManagerModule from './features/tool-manager/di.config';


