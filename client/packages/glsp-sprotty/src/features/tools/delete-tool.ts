/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/
import { inject, injectable } from "inversify";
import { Action, isCtrlOrCmd, isSelectable, KeyListener, KeyTool, MouseListener, MouseTool, SModelElement, SModelRoot } from "sprotty/lib";
import { matchesKeystroke } from "sprotty/lib/utils/keyboard";
import { DeleteElementOperationAction } from "../operation/operation-actions";
import { EnableStandardToolsAction, Tool } from "../tool-manager/tool";

/**
 * Deletes selected elements when hitting the `Del` key.
 */
@injectable()
export class DelKeyDeleteTool implements Tool {
    static ID = "glsp.delete-keyboard";
    readonly id = DelKeyDeleteTool.ID;

    protected deleteKeyListener: DeleteKeyListener = new DeleteKeyListener();;

    constructor(@inject(KeyTool) protected readonly keytool: KeyTool) { }

    enable() {
        this.keytool.register(this.deleteKeyListener);
    }

    disable() {
        this.keytool.deregister(this.deleteKeyListener);
    }
}

@injectable()
export class DeleteKeyListener extends KeyListener {
    keyDown(element: SModelElement, event: KeyboardEvent): Action[] {
        if (matchesKeystroke(event, 'Delete')) {
            const deleteElementIds = Array.from(element.root.index.all().filter(e => isSelectable(e) && e.selected)
                .filter(e => e.id !== e.root.id).map(e => e.id))
            return [new DeleteElementOperationAction(deleteElementIds)]
        }
        return [];
    }
}

/**
 * Deletes selected elements when clicking on them.
 */
@injectable()
export class MouseDeleteTool implements Tool {

    static ID = "glsp.delete-mouse";
    readonly id = MouseDeleteTool.ID;

    protected deleteToolMouseListener: DeleteToolMouseListener = new DeleteToolMouseListener();;

    constructor(@inject(MouseTool) protected readonly mouseTool: MouseTool) { }

    enable() {
        this.mouseTool.register(this.deleteToolMouseListener);
    }

    disable() {
        this.mouseTool.deregister(this.deleteToolMouseListener);
    }
}

@injectable()
export class DeleteToolMouseListener extends MouseListener {
    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        if (target instanceof SModelRoot) {
            return [];
        }

        const result: Action[] = [];
        result.push(new DeleteElementOperationAction([target.id]));
        if (!isCtrlOrCmd(event)) {
            result.push(new EnableStandardToolsAction());
        }
        return result;
    }
}
