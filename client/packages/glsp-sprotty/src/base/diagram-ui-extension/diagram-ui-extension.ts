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
import { inject, injectable } from "inversify";
import { Action, IActionDispatcherProvider, ILogger, SModelElement, TYPES, ViewerOptions } from "sprotty/lib";

/**
 * An extension with togglable visbility  that can display additional (UI) information on top of a sprotty diagram
 */
export interface IDiagramUIExtension {
    readonly id: string
    show(selectedElements: SModelElement[]): void
    hide(): void
}

/**
 * Abstract base class for Diagram UI extensions that provides commonly used functionality
 */
@injectable()
export abstract class BaseDiagramUIExtension implements IDiagramUIExtension {
    abstract readonly id: string;
    abstract readonly containerClass: string
    protected containerElement: HTMLElement;

    constructor(
        @inject(TYPES.ViewerOptions) protected options: ViewerOptions,
        @inject(TYPES.IActionDispatcherProvider) protected actionDispatcherProvider: IActionDispatcherProvider,
        @inject(TYPES.ILogger) protected logger: ILogger) {
    }

    show(selectedElements: SModelElement[]): void {
        if (!this.containerElement) {
            const initializeSuccessful = this.initialize();
            if (!initializeSuccessful) return;
        }
        this.updatePosition(selectedElements);
        this.setContainerVisible(true)
    }

    hide(): void {
        this.setContainerVisible(false)
        this.restoreFocus()
    }

    protected restoreFocus() {
        // restore focus of sprotty's svg element
        // _sprotty: svg container id as specified in DiagramManagerImpl
        const sprottyDiv = document.getElementById(this.options.baseDiv + "_sprotty");
        if (sprottyDiv) {
            sprottyDiv.focus();
        }
    }

    protected initialize(): boolean {
        const baseDiv = document.getElementById(this.options.baseDiv);
        if (!baseDiv) {
            this.logger.warn(this, 'Could not obtain sprotty base container for showing command palette');
            return false;
        }
        this.containerElement = this.getOrCreateContainer()
        this.createUIElements()
        if (baseDiv) {
            baseDiv.insertBefore(this.containerElement, baseDiv.firstChild);
        }
        return true
    }

    protected getOrCreateContainer(): HTMLElement {
        let container = document.getElementById(this.id)
        if (container === null) {
            container = document.createElement('div');
            container.id = this.id
            container.classList.add(this.containerClass)
        }
        return container
    }

    /**
     * Subtypes can override this method to modfiy the position of selection-aware extensions
     */
    protected updatePosition(selectedElements: SModelElement[]) {
        // default: do nothing
    }

    protected abstract createUIElements(): void

    protected setContainerVisible(value: boolean) {
        if (this.containerElement) {
            if (value) {
                this.containerElement.style.visibility = 'visible';
                this.containerElement.style.opacity = '1';
            } else {
                this.containerElement.style.visibility = 'hidden';
                this.containerElement.style.opacity = '0';
            }
        }
    }

    protected executeAction(input: LabeledAction | Action[] | Action) {
        this.actionDispatcherProvider()
            .then((actionDispatcher) => actionDispatcher.dispatchAll(toActionArray(input)))
            .catch((reason) => this.logger.error(this, 'No action dispatcher available to execute diagram ui extension  action', reason));
    }
}

function toActionArray(input: LabeledAction | Action[] | Action): Action[] {
    if (input instanceof LabeledAction) {
        return input.actions
    } else {
        return [...input]
    }
}

/**
 * Action with a label. This can be for instanace used to represent the available actions in command palettes.
 */
export class LabeledAction {
    constructor(readonly label: string, readonly actions: Action[]) { }
}