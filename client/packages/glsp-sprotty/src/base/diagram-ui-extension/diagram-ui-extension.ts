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

/** A toggable extension  that can display additional (UI) information
 *  on top of a sprotty diagram
 */
export interface IDiagramUIExtension {
    readonly id: string
    show(selectedElements: SModelElement[]): void
    hide(): void
}

/** An abstract Diagram UI extension that can serve as a common base for different
 * subtypes of diagram UI extensions
 */
@injectable()
export abstract class BaseDiagramUIExtension implements IDiagramUIExtension {
    abstract readonly id: string
    protected containerElement: HTMLDivElement;
    constructor(
        @inject(TYPES.ViewerOptions) protected options: ViewerOptions,
        @inject(TYPES.IActionDispatcherProvider) protected actionDispatcherProvider: IActionDispatcherProvider,
        @inject(TYPES.ILogger) protected logger: ILogger) { }

    show(selectedElements: SModelElement[]) {
        if (!this.containerElement) {
            this.createUiElements();
        }
        this.updatePosition(selectedElements);
        this.containerElement.style.visibility = 'visible';
        this.containerElement.style.opacity = '1';
    }

    /** Subtypes can override this method to modfiy the position
     * of selection-aware extensions
     */
    protected updatePosition(selectedElements: SModelElement[]) {
        // default: do nothing
    }
    hide(): void {
        if (this.containerElement) {
            this.containerElement.style.visibility = 'hidden';
            this.containerElement.style.opacity = '0';
        }

        // restore focus of sprotty's svg element
        // _sprotty: svg container id as specified in DiagramManagerImpl
        const sprottyDiv = document.getElementById(this.options.baseDiv + "_sprotty");
        if (sprottyDiv) {
            sprottyDiv.focus();
        }
    }

    protected abstract createUiElements(): void

    protected executeDiagramUIExtensionAction(item: LabeledAction) {
        this.actionDispatcherProvider()
            .then((actionDispatcher) => actionDispatcher.dispatchAll(item.actions))
            .catch((reason) => this.logger.error(this, 'No action dispatcher available to execute diagram ui extension  action', reason));
        this.hide();
    }
}

/**
 * Action with a label. This is used to represent the available actions in the command palettes.
 */
export class LabeledAction {
    constructor(readonly label: string, readonly actions: Action[]) { }
}
