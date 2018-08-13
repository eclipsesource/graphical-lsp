/*
 * Copyright (C) 2017 TypeFox and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License") you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 */

import {
    CenterAction,
    FitToScreenAction,
    RequestExportSvgAction,
    UndoAction,
    RedoAction,
    SelectAllAction
} from 'sprotty/lib'
import { DiagramWidget } from './diagram-widget'
import { DiagramManagerImpl } from './diagram-manager'
import { injectable, inject } from 'inversify'
import { MAIN_MENU_BAR, MenuContribution, MenuModelRegistry, CommandContribution,
         CommandHandler, CommandRegistry, MenuPath } from '@theia/core/lib/common'
import { ApplicationShell, OpenerService, CommonCommands } from '@theia/core/lib/browser'
import { EDITOR_CONTEXT_MENU, EditorManager } from "@theia/editor/lib/browser"

export namespace DiagramCommands {
    export const CENTER = 'diagram:center'
    export const FIT = 'diagram:fit'
    export const EXPORT = 'diagram:export'
    export const SELECT_ALL = 'diagram.selectAll'
    export const OPEN_IN_DIAGRAM = 'diagram.open'
}

export namespace DiagramMenus {
    export const DIAGRAM: MenuPath = MAIN_MENU_BAR.concat("3_diagram")
}

@injectable()
export class DiagramMenuContribution implements MenuContribution {

    registerMenus(registry: MenuModelRegistry) {
        registry.registerSubmenu(DiagramMenus.DIAGRAM, "Diagram")

        registry.registerMenuAction(DiagramMenus.DIAGRAM, {
            commandId: DiagramCommands.CENTER
        })
        registry.registerMenuAction(DiagramMenus.DIAGRAM, {
            commandId: DiagramCommands.FIT
        })
        registry.registerMenuAction(DiagramMenus.DIAGRAM, {
            commandId: DiagramCommands.EXPORT
        })
        registry.registerMenuAction(EDITOR_CONTEXT_MENU, {
            commandId: DiagramCommands.OPEN_IN_DIAGRAM
        })
    }
}

export class DiagramCommandHandler implements CommandHandler {

    constructor(protected readonly shell: ApplicationShell,
                protected readonly doExecute: (diagram: DiagramWidget) => any) {
    }

    execute(...args: any[]) {
        return this.isEnabled()
            ? this.doExecute(this.shell.currentWidget as DiagramWidget)
            : undefined
    }

    isEnabled(): boolean {
        return this.shell.currentWidget instanceof DiagramWidget
    }
}

export class OpenInDiagramHandler implements CommandHandler {

    constructor(protected readonly editorManager: EditorManager,
                protected readonly openerService: OpenerService) {
    }

    execute(...args: any[]) {
        const editor = this.editorManager.currentEditor
        if (editor !== undefined) {
            const uri = editor.editor.uri
            const openers = this.openerService.getOpeners(uri)
            openers.then(openers => {
                const opener = openers.find(opener => opener instanceof DiagramManagerImpl)
                if (opener !== undefined)
                    opener.open(uri)
            })
        }
    }
}

@injectable()
export class DiagramCommandContribution implements CommandContribution {
    constructor(@inject(ApplicationShell) protected readonly shell: ApplicationShell,
                @inject(EditorManager) protected readonly editorManager: EditorManager,
                @inject(OpenerService) protected readonly openerService: OpenerService) {
    }

    registerCommands(registry: CommandRegistry): void {
        registry.registerCommand({
            id: DiagramCommands.CENTER,
            label: 'Center'
        })
        registry.registerCommand({
            id: DiagramCommands.FIT,
            label: 'Fit to screen'
        })
        registry.registerCommand({
            id: DiagramCommands.EXPORT,
            label: 'Export'
        })
        registry.registerCommand({
            id: DiagramCommands.SELECT_ALL,
            label: 'Select all'
        })
        registry.registerCommand({
            id: DiagramCommands.OPEN_IN_DIAGRAM,
            label: 'Open in diagram'
        })

        registry.registerHandler(
            DiagramCommands.CENTER,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new CenterAction([]))
            )
        )
        registry.registerHandler(
            DiagramCommands.FIT,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new FitToScreenAction([]))
            )
        )
        registry.registerHandler(
            DiagramCommands.EXPORT,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new RequestExportSvgAction())
            )
        )
        registry.registerHandler(
            DiagramCommands.SELECT_ALL,
            new DiagramCommandHandler(this.shell, widget => {
                const action = new SelectAllAction(true)
                widget.actionDispatcher.dispatch(action)
            })
        )
        registry.registerHandler(
            DiagramCommands.OPEN_IN_DIAGRAM,
            new OpenInDiagramHandler(this.editorManager, this.openerService)
        )
        registry.registerHandler(
            CommonCommands.UNDO.id,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new UndoAction())
            )
        )
        registry.registerHandler(
            CommonCommands.REDO.id,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new RedoAction())
            )
        )
    }
}