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
import { Action } from "sprotty/lib";
import { ActionHandlerRegistry } from "sprotty/lib";
import { CommandExecutionContext } from "sprotty/lib";
import { CommandResult } from "sprotty/lib";
import { GLSP_TYPES } from "../../types";
import { IActionHandler } from "sprotty/lib";
import { IActionHandlerInitializer } from "sprotty/lib";
import { ICommand } from "sprotty/lib";
import { IDiagramUIExtension } from "./diagram-ui-extension";
import { InstanceRegistry } from "sprotty/lib";
import { SystemCommand } from "sprotty/lib";

import { inject } from "inversify";
import { injectable } from "inversify";
import { multiInject } from "inversify";
import { optional } from "inversify";
import { toArray } from "sprotty/lib/utils/iterable";

/**
 * Convinience class for classes that implement both `IActionHandler` and `IActionHandlerInitializer`
 */
@injectable()
export abstract class SelfInitializingActionHandler implements IActionHandler, IActionHandlerInitializer {

    initialize(registry: ActionHandlerRegistry) {
        this.handledActionKinds.forEach(kind => registry.register(kind, this));
    }

    abstract handle(action: Action): ICommand | Action | void;
    abstract handledActionKinds: string[];
}

/**
 * Action requesting to show the diagram UI extension with specified id.
 */
export class ShowDiagramUIExtensionAction implements Action {
    static KIND = "showDiagramUIExtension";
    readonly kind = ShowDiagramUIExtensionAction.KIND;
    constructor(public readonly extensionId: string, public readonly selectedElementIds: string[]) { }
}

/**
 * Action requesting to hide the diagram UI extension with specified id.
 */
export class HideDiagramUIExtensionAction implements Action {
    static KIND = "hideDiagramUIExtension";
    readonly kind = HideDiagramUIExtensionAction.KIND;
    constructor(public readonly extensionId: string) { }
}

/**
 * The diagram UI extension registry maintains all available diagram UI extensions and makes them retrievable by id
 */
@injectable()
export class DiagramUIExtensionRegistry extends InstanceRegistry<IDiagramUIExtension>  {

    constructor(@multiInject(GLSP_TYPES.IDiagramUIExtension) @optional() extensions: (IDiagramUIExtension)[] = []) {
        super();
        extensions.forEach(
            extension => this.register(extension.id, extension)
        );
    }
}
/**
 * Initalizer and handler for DiagramUIExension actions.
 */
@injectable()
export class DiagramUIExtensionActionHandlerInitializer extends SelfInitializingActionHandler {
    @inject(GLSP_TYPES.DiagramUIExtensionRegistry) protected readonly registry: DiagramUIExtensionRegistry;

    readonly handledActionKinds = [ShowDiagramUIExtensionAction.KIND, HideDiagramUIExtensionAction.KIND];

    handle(action: Action): void | ICommand | Action {
        if (action instanceof ShowDiagramUIExtensionAction) {
            return new DiagramUIExtensionActionCommand((context) => {
                const index = context.root.index;
                const selectedElements = toArray(index.all()
                    .filter(e => action.selectedElementIds.indexOf(e.id) >= 0));
                const extension = this.registry.get(action.extensionId);
                if (extension) {
                    extension.show(selectedElements);
                }
            });
        } else if (action instanceof HideDiagramUIExtensionAction) {
            return new DiagramUIExtensionActionCommand((context) => {
                const extension = this.registry.get(action.extensionId);
                if (extension) {
                    extension.hide();
                }
            });
        }
    }
}

export type CommandEffect = (context: CommandExecutionContext) => void;

/**
 * A system command that doesn't change the model but just performs a specified `effect`.
 */
@injectable()
export class DiagramUIExtensionActionCommand extends SystemCommand {

    constructor(readonly effect: CommandEffect) {
        super();
    }

    execute(context: CommandExecutionContext): CommandResult {
        this.effect(context);
        context.root.index;
        return context.root;
    }

    undo(context: CommandExecutionContext): CommandResult {
        return context.root;
    }

    redo(context: CommandExecutionContext): CommandResult {
        return context.root;
    }
}
