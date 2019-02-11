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
import { inject, injectable, multiInject, optional } from "inversify";
import { Action, CommandExecutionContext, CommandResult, InstanceRegistry, SystemCommand, TYPES } from "sprotty/lib";
import { toArray } from "sprotty/lib/utils/iterable";
import { GLSP_TYPES } from "../../types";
import { IDiagramUIExtension } from "./diagram-ui-extension";

/**
 * The diagram UI extension registry maintains all available diagram UI extensions and makes them retrievable by id.
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
 * Action requesting to show the diagram UI extension with specified id.
 */
export class ShowDiagramUIExtensionAction implements Action {
    readonly kind = ShowDiagramUIExtensionCommand.KIND;
    constructor(public readonly extensionId: string, public readonly selectedElementIds: string[]) { }
}

/**
 * Action requesting to hide the diagram UI extension with specified id.
 */
export class HideDiagramUIExtensionAction implements Action {
    readonly kind = HideDiagramUIExtensionCommand.KIND;
    constructor(public readonly extensionId: string) { }
}

abstract class DiagramUiExtensionCommand extends SystemCommand {
    execute(context: CommandExecutionContext): CommandResult {
        this.doExecute(context);
        return context.root;
    }
    undo(context: CommandExecutionContext): CommandResult {
        return context.root;
    }
    redo(context: CommandExecutionContext): CommandResult {
        return context.root;
    }
    abstract doExecute(context: CommandExecutionContext): void;
}

@injectable()
export class ShowDiagramUIExtensionCommand extends DiagramUiExtensionCommand {

    static KIND = "showDiagramUIExtension";

    constructor(@inject(TYPES.Action) protected action: ShowDiagramUIExtensionAction,
        @inject(GLSP_TYPES.DiagramUIExtensionRegistry) protected readonly registry: DiagramUIExtensionRegistry) {
        super();
    }

    doExecute(context: CommandExecutionContext) {
        const index = context.root.index;
        const selectedElements = toArray(index.all()
            .filter(e => this.action.selectedElementIds.indexOf(e.id) >= 0));
        const extension = this.registry.get(this.action.extensionId);
        if (extension) {
            extension.show(selectedElements);
        }
    }
}

@injectable()
export class HideDiagramUIExtensionCommand extends DiagramUiExtensionCommand {

    static KIND = "hideDiagramUIExtension";

    constructor(@inject(TYPES.Action) protected action: ShowDiagramUIExtensionAction,
        @inject(GLSP_TYPES.DiagramUIExtensionRegistry) protected readonly registry: DiagramUIExtensionRegistry) {
        super();
    }

    doExecute(context: CommandExecutionContext) {
        const extension = this.registry.get(this.action.extensionId);
        if (extension) {
            extension.hide();
        }
    }
}
