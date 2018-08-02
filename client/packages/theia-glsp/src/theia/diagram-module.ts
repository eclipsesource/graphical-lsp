/*
 * Copyright (C) 2017 TypeFox and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License") you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 */

import { ContainerModule } from 'inversify'
import { DiagramWidgetRegistry } from "./diagram-widget-registry"
import { DiagramConfigurationRegistry } from "./diagram-configuration"
import { TheiaFileSaver } from "../sprotty/theia-file-saver"
import { DiagramCommandContribution, DiagramMenuContribution } from './diagram-commands'
import { CommandContribution, MenuContribution } from '@theia/core/lib/common'
import { DiagramKeybindingContext, DiagramKeybindingContribution } from './diagram-keybinding'
import { KeybindingContext, KeybindingContribution } from '@theia/core/lib/browser';

export default new ContainerModule(bind => {
    bind(DiagramWidgetRegistry).toSelf().inSingletonScope()
    bind(DiagramConfigurationRegistry).toSelf().inSingletonScope()
    bind(TheiaFileSaver).toSelf().inSingletonScope()
    bind(CommandContribution).to(DiagramCommandContribution).inSingletonScope()
    bind(MenuContribution).to(DiagramMenuContribution).inSingletonScope()
    bind(DiagramKeybindingContext).toSelf().inSingletonScope()
    bind(KeybindingContext).to(DiagramKeybindingContext).inSingletonScope()
    bind(KeybindingContribution).to(DiagramKeybindingContribution).inSingletonScope()
})
