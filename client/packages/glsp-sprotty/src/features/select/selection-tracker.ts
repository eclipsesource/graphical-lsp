/*******************************************************************************
 * Copyright (c) 2018 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *  Martin Fleck - initial API and implementation
 ******************************************************************************/
import { inject, optional } from "inversify";
import { Action, ButtonHandlerRegistry, KeyListener, MouseListener, SelectAction, SelectKeyboardListener, SelectMouseListener, SModelElement } from "sprotty/lib";

/**
 * A mouse and key listener that tracks the currently selected elements in an accessible manner by extending the selection implementation of Sprotty.
 */
export class SelectionTracker extends MouseListener implements KeyListener {
    private selectMouseListener: SelectMouseListener;
    private selectKeyboardListener: SelectKeyboardListener;
    private selectedElementIDs: Set<string> = new Set();

    constructor(@inject(ButtonHandlerRegistry) @optional() protected buttonHandlerRegistry: ButtonHandlerRegistry) {
        super();
        this.selectMouseListener = new SelectMouseListener(buttonHandlerRegistry);
        this.selectKeyboardListener = new SelectKeyboardListener();
    }

    mouseDown(target: SModelElement, event: MouseEvent): Action[] {
        return this.handleResult(this.selectMouseListener.mouseDown(target, event));
    }

    mouseUp(target: SModelElement, event: MouseEvent): Action[] {
        return this.handleResult(this.selectMouseListener.mouseUp(target, event));
    }

    keyDown(element: SModelElement, event: KeyboardEvent): Action[] {
        // please note that the DiagramKeybindingContribution which registers 'ctrlcmd+a' for Select All currently prevents the key listener from getting any notifications
        return this.handleResult(this.selectKeyboardListener.keyDown(element, event));
    }

    keyUp(element: SModelElement, event: KeyboardEvent): Action[] {
        return [];
    }

    private handleResult(result: Action[]): Action[] {
        for (const action of result.filter(isSelectAction).map(asSelectAction)) {
            this.handleSelectAction(action);
        }
        return [];
    }

    private handleSelectAction(action: SelectAction) {
        for (const id of action.selectedElementsIDs) {
            this.selectedElementIDs.add(id);
        }
        for (const id of action.deselectedElementsIDs) {
            this.selectedElementIDs.delete(id);
        }
    }

    getSelectedElementIDs(): Set<string> {
        return this.selectedElementIDs;
    }

    hasSelectedElements(): boolean {
        return this.selectedElementIDs.size > 0;
    }

    isSingleSelection(): boolean {
        return this.selectedElementIDs.size === 1;
    }

    isMultiSelection(): boolean {
        return this.selectedElementIDs.size > 1;
    }
}

function isSelectAction(action: Action) {
    return action instanceof SelectAction;
}

function asSelectAction(action: Action) {
    return action as SelectAction;
}