/*
 * Copyright (C) 2017 TypeFox and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License") you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 */

import { DiagramCommands } from './diagram-commands'
import { DiagramWidget } from './diagram-widget'
import { injectable, inject } from 'inversify'
import { FrontendApplication } from '@theia/core/lib/browser/frontend-application'
import { CommonCommands, KeybindingContext, Keybinding, KeybindingContribution, KeybindingRegistry } from '@theia/core/lib/browser'

@injectable()
export class DiagramKeybindingContext implements KeybindingContext {

    constructor(@inject(FrontendApplication) protected readonly application: FrontendApplication) {
    }

    id = 'diagram.keybinding.context'

    isEnabled(arg?: Keybinding) {
        return this.application.shell.currentWidget instanceof DiagramWidget
    }
}

@injectable()
export class DiagramKeybindingContribution implements KeybindingContribution {

    constructor(@inject(DiagramKeybindingContext) protected readonly diagramKeybindingContext: DiagramKeybindingContext) { }

    registerKeybindings(registry: KeybindingRegistry): void {
        [
            {
                command: DiagramCommands.CENTER,
                context: this.diagramKeybindingContext.id,
                keybinding: 'alt+c'
            },
            {
                command: DiagramCommands.FIT,
                context: this.diagramKeybindingContext.id,
                keybinding: 'alt+f'
            },
            {
                command: DiagramCommands.EXPORT,
                context: this.diagramKeybindingContext.id,
                keybinding: 'alt+e'
            },
            {
                command: DiagramCommands.SELECT_ALL,
                context: this.diagramKeybindingContext.id,
                keybinding: 'ctrlcmd+a'
            },
            {
                command: CommonCommands.UNDO.id,
                context: this.diagramKeybindingContext.id,
                keybinding: 'ctrlcmd+z'
            },
            {
                command: CommonCommands.REDO.id,
                context: this.diagramKeybindingContext.id,
                keybinding: 'ctrlcmd+shift+z'
            }
        ].forEach(binding => {
            registry.registerKeybinding(binding)
        })
    }
}
