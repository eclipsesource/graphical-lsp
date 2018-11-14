export * from 'sprotty/lib';
export * from './features/execute/execute-command';
export * from './features/execute/model';
export * from './features/operation/set-operations';
export * from './features/save/model';
export * from './features/save/save';
export * from './features/tools/creation-tool';
export * from './features/tools/delete-tool';
export * from './features/tools/execute-tool';
export * from './features/tools/move-tool';
export * from './features/tools/tool-manager';
export * from './lib/model';
export * from './utils/operation';
export { saveModule, toolManagerModule, executeModule };

import executeModule from './features/execute/di.config';
import saveModule from './features/save/di.config';
import toolManagerModule from './features/tools/di.config';
