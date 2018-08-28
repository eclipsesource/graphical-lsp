
export * from 'sprotty/lib';

export * from './features/save/model'
export * from './features/save/save'
export * from './features/palette/connection-tool'

export * from './lib/model'

export * from './features/tool/set-tools'
export * from './features/tool/execute-tool'

export * from './utils/tool'
import saveModule from './features/save/di.config'
import paletteModule from './features/palette/di.config'
import moveToolModule from './features/tool/di.config'

export { saveModule, paletteModule, moveToolModule }