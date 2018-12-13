/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Martin Fleck - initial API and implementation
 ******************************************************************************/
import { injectable } from "inversify";
import { VNode } from "snabbdom/vnode";
import { Action, KeyListener, KeyTool, on, SModelElement, SModelRoot } from "sprotty/lib";

@injectable()
export class ExtendedKeyTool extends KeyTool {
    protected handleExtendedKeyListenerEvent<K extends keyof ExtendedKeyListener>(methodName: K, model: SModelRoot, event: KeyboardEvent) {
        const actions = this.keyListeners
            .filter(l => l instanceof ExtendedKeyListener)
            .map(listener => (listener as ExtendedKeyListener)[methodName].apply(listener, [model, event]))
            .reduce((a, b) => a.concat(b));
        if (actions.length > 0) {
            event.preventDefault();
            this.actionDispatcher.dispatchAll(actions);
        }
    }

    keyUp(element: SModelRoot, event: KeyboardEvent): void {
        this.handleExtendedKeyListenerEvent('keyUp', element, event);
    }

    decorate(vnode: VNode, element: SModelElement): VNode {
        if (element instanceof SModelRoot) {
            this.setEventListener(vnode, element, 'focus', this.focus.bind(this));
            this.setEventListener(vnode, element, 'keydown', this.keyDown.bind(this));
            this.setEventListener(vnode, element, 'keyup', this.keyUp.bind(this));
        }
        return vnode;
    }

    setEventListener(vnode: VNode, element: SModelElement, event: string, listener: (model: SModelElement, event: Event) => void) {
        if (!hasEventListener(vnode, event)) {
            // only set listener if no other listener is already present
            on(vnode, event, listener, element);
        }
    }
}

function hasEventListener(vnode: VNode, event: string) {
    return vnode.data && vnode.data.on && vnode.data.on[event];
}

@injectable()
export class ExtendedKeyListener extends KeyListener {
    keyUp(element: SModelElement, event: KeyboardEvent): Action[] {
        return [];
    }
}