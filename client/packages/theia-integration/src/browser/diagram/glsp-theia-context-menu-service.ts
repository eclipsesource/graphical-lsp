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
import { Anchor, IContextMenuService, MenuItem } from "@glsp/sprotty-client/lib";
import { Command, CommandHandler, CommandRegistry, Disposable, MenuAction, MenuModelRegistry, MenuPath } from "@theia/core";
import { ContextMenuRenderer } from "@theia/core/lib/browser";
import { inject, injectable } from "inversify";
import { IActionDispatcher } from "sprotty";

export namespace TheiaGlspContextMenu {
    export const CONTEXT_MENU: MenuPath = ['glsp-context-menu'];
}

@injectable()
export class TheiaContextMenuService implements IContextMenuService {

    @inject(ContextMenuRenderer)
    protected readonly contextMenuRenderer: ContextMenuRenderer;

    @inject(MenuModelRegistry)
    protected readonly menuProvider: MenuModelRegistry;

    @inject(CommandRegistry)
    protected readonly commandRegistry: CommandRegistry;

    protected actionDispatcher?: IActionDispatcher;

    connect(actionDispatcher: IActionDispatcher) {
        this.actionDispatcher = actionDispatcher;
    }

    show(items: MenuItem[], anchor: Anchor, onHide?: () => void): void {
        const disposables = this.register(TheiaGlspContextMenu.CONTEXT_MENU, items);
        const renderOptions = {
            menuPath: TheiaGlspContextMenu.CONTEXT_MENU, anchor: anchor,
            onHide: () => { if (onHide) onHide(); window.setTimeout(() => this.cleanUp(disposables), 500); }
        };
        this.contextMenuRenderer.render(renderOptions);
    }

    protected register(menuPath: string[], items: MenuItem[]): DisposableItem[] {
        const disposables: DisposableItem[] = [];
        for (const item of items) {
            if (item.children && item.children.length > 0) {
                const menuPathOfItem = item.group ? [...menuPath, item.group] : menuPath;
                disposables.push(this.registerSubmenu(menuPathOfItem, item));
                disposables.push(...this.register([...menuPathOfItem, item.id], item.children));
            } else {
                disposables.push(this.registerCommand(menuPath, item));
                disposables.push(this.registerMenuAction(menuPath, item));
            }
        }
        return disposables;
    }

    protected registerSubmenu(menuPath: string[], item: MenuItem): DisposableItem {
        return this.menuProvider.registerSubmenu([...menuPath, item.id], item.label);
    }

    protected registerCommand(menuPath: string[], item: MenuItem): DisposableItem {
        const command: Command = { id: commandId(menuPath, item), label: item.label, iconClass: item.icon };
        const disposable = this.commandRegistry.registerCommand(command, new GlspCommandHandler(item, this.actionDispatcher));
        return new DisposableCommand(command, disposable);
    }

    protected registerMenuAction(menuPath: string[], item: MenuItem): DisposableItem {
        const menuAction = { label: item.label, order: item.sortString, commandId: commandId(menuPath, item) };
        const menuPathOfItem = item.group ? [...menuPath, item.group] : menuPath;
        const disposable = this.menuProvider.registerMenuAction(menuPathOfItem, menuAction);
        return new DisposableMenuAction(menuAction, disposable);
    }

    protected cleanUp(disposables: DisposableItem[]) {
        disposables.forEach(disposable => disposable.dispose(this.menuProvider, this.commandRegistry));
    }
}

class GlspCommandHandler implements CommandHandler {

    constructor(readonly menuItem: MenuItem, readonly actionDispatcher?: IActionDispatcher) { }

    execute(...args: any[]) {
        if (this.actionDispatcher && this.menuItem.actions) {
            this.actionDispatcher.dispatchAll(this.menuItem.actions);
        }
    }

    isEnabled(...args: any[]): boolean {
        return getBooleanValue(this.menuItem.isEnabled, true);
    }

    isVisible(...args: any[]): boolean {
        return getBooleanValue(this.menuItem.isVisible, true);
    }

    isToggled(...args: any[]): boolean {
        return getBooleanValue(this.menuItem.isToggled, false);
    }
}

interface DisposableItem {
    dispose(menuProvider: MenuModelRegistry, commandRegistry: CommandRegistry): void;
}

class DisposableMenuAction implements DisposableItem {
    constructor(protected readonly menuAction: MenuAction, protected readonly disposable: Disposable) { }
    dispose(menuProvider: MenuModelRegistry, commandRegistry: CommandRegistry): void {
        menuProvider.unregisterMenuAction(this.menuAction);
        this.disposable.dispose();
    }
}

class DisposableCommand implements DisposableItem {
    constructor(protected readonly command: Command, protected readonly disposable: Disposable) { }
    dispose(menuProvider: MenuModelRegistry, commandRegistry: CommandRegistry): void {
        commandRegistry.unregisterCommand(this.command);
        this.disposable.dispose();
    }
}

function commandId(menuPath: string[], item: any): string {
    return menuPath.join(".") + "." + item.id;
}

function getBooleanValue(value: any, defaultValue: boolean) {
    let returnVal = defaultValue;
    if (isFunction(value)) {
        returnVal = value();
    } else if (isBoolean(value)) {
        returnVal = value;
    }
    return returnVal;
}

function isFunction(value: () => boolean | boolean): value is () => boolean {
    return !!(value && value.constructor && value.call && value.apply);
}

function isBoolean(value: () => boolean | boolean): boolean {
    return typeof value === "boolean";
}
