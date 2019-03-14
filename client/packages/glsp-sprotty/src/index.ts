/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
export * from 'sprotty/lib';
export * from './base/command-stack';
export * from './base/diagram-ui-extension/diagram-ui-extension';
export * from './base/edit-config/edit-config';
export * from './base/model/update-model-command';
export * from './base/tool-manager/tool-manager-action-handler';
export * from './features/change-bounds/model';
export * from './features/command-palette/action-definitions';
export * from './features/command-palette/action-provider';
export * from './features/command-palette/command-palette';
export * from './features/execute/execute-command';
export * from './features/execute/model';
export * from './features/hints/action-definition';
export * from './features/hints/type-hints-action-initializer';
export * from './features/nameable/model';
export * from './features/operation/operation-actions';
export * from './features/operation/set-operations';
export * from './features/request-response/action-definitions';
export * from './features/request-response/support';
export * from './features/save/model';
export * from './features/save/save';
export * from './features/tool-feedback/creation-tool-feedback';
export * from './features/tool-feedback/model';
export * from './features/tool-palette/tool-palette';
export * from './features/tools/change-bounds-tool';
export * from './features/tools/creation-tool';
export * from './features/tools/default-tools';
export * from './features/tools/delete-tool';
export * from './lib/model';
export * from './types';
export * from './utils/array-utils';
export * from './utils/smodel-util';
export * from './utils/viewpoint-util';
export { saveModule, executeModule, paletteModule, toolFeedbackModule, defaultGLSPModule, modelHintsModule, commandPaletteModule, requestResponseModule };

import commandPaletteModule from './features/command-palette/di.config';
import defaultGLSPModule from './base/di.config';
import executeModule from './features/execute/di.config';
import modelHintsModule from './features/hints/di.config';
import paletteModule from './features/tool-palette/di.config';
import requestResponseModule from './features/request-response/di.config';
import saveModule from './features/save/di.config';
import toolFeedbackModule from './features/tool-feedback/di.config';

