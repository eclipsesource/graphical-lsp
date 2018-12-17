/*******************************************************************************
 * Copyright (c) 2018 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *  Philip Langer - initial API and implementation
 ******************************************************************************/

import { Command, CommandExecutionContext, CommandResult, SModelRoot } from "sprotty/lib";

export abstract class FeedbackCommand extends Command {

    abstract execute(context: CommandExecutionContext): SModelRoot;

    undo(context: CommandExecutionContext): CommandResult {
        return context.root;
    }

    redo(context: CommandExecutionContext): CommandResult {
        return context.root;
    }
}

export function applyCssClassesToRoot(context: CommandExecutionContext, cssClasses: string[]): SModelRoot {
    const root = context.root;
    addCssClasses(root, cssClasses);
    return root;
}

export function unapplyCssClassesToRoot(context: CommandExecutionContext, cssClasses: string[]): SModelRoot {
    const root = context.root;
    removeCssClasses(root, cssClasses);
    return root;
}

function addCssClasses(root: SModelRoot, cssClasses: string[]) {
    if (root.cssClasses === undefined) {
        root.cssClasses = [];
    }
    for (const cssClass of cssClasses) {
        // TODO check if class already exists
        root.cssClasses.push(cssClass);
    }
}

function removeCssClasses(root: SModelRoot, cssClasses: string[]) {
    if (root.cssClasses === undefined || root.cssClasses.length === 0) {
        return;
    }
    for (const cssClass of cssClasses) {
        const index = root.cssClasses.indexOf(cssClass);
        if (index !== -1) {
            root.cssClasses.splice(root.cssClasses.indexOf(cssClass), 1);
        }
    }
}
