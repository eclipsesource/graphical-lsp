/*******************************************************************************
 * Copyright (c) 2019 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/

import { AutocompleteResult, AutocompleteSettings } from "autocompleter";
import { inject, injectable } from "inversify";
import {
    Action, ActionHandlerRegistry, CommandExecutionContext, CommandResult, findParentByFeature, IActionDispatcher, //
    IActionHandler, IActionHandlerInitializer, ICommand, ILogger, isBoundsAware, isSelectable, isViewport, KeyListener, //
    SModelElement, SystemCommand, TYPES, ViewerOptions
} from "sprotty/lib";
import { toArray } from "sprotty/lib/utils/iterable";
import { matchesKeystroke } from "sprotty/lib/utils/keyboard";
import { GLSP_TYPES } from "../../types";
import { ICommandPaletteActionProviderRegistry } from "./action-provider";

// import of function autocomplete(...) doesn't work
// see also https://github.com/kraaden/autocomplete/issues/13
// this is a workaround to still get the function including type support
const configureAutocomplete: (settings: AutocompleteSettings<LabeledAction>) => AutocompleteResult = require("autocompleter");

export class CommandPaletteKeyListener extends KeyListener {
    keyDown(element: SModelElement, event: KeyboardEvent): Action[] {
        if (matchesKeystroke(event, 'Escape')) {
            return [new HideCommandPaletteAction()];
        } else if (matchesKeystroke(event, 'Space', 'ctrl')) {
            const selected = toArray(element.root.index.all().filter(e => isSelectable(e) && e.selected)).map(e => e.id);
            return [new ShowCommandPaletteAction(selected)];
        }
        return [];
    }
}

/**
 * Action requesting to show the command palette.
 */
export class ShowCommandPaletteAction implements Action {
    static KIND = "show-command-palette";
    readonly kind = ShowCommandPaletteAction.KIND;
    constructor(public readonly selectedElementIds: string[]) { }
}

/**
 * Action requesting to hide the command palette.
 */
export class HideCommandPaletteAction implements Action {
    static KIND = "hide-command-palette";
    readonly kind = HideCommandPaletteAction.KIND;
}

/**
 * Action with a label. This is used to represent the available actions in the command palettes.
 */
export class LabeledAction {
    constructor(readonly label: string, readonly actions: Action[]) { }
}

// TODO can be removed after https://github.com/eclipse/sprotty/pull/32 is merged
export type IActionDispatcherProvider = () => Promise<IActionDispatcher>;

@injectable()
export class CommandPalette {

    readonly xOffset = 20;
    readonly yOffset = 30;
    readonly defaultWidth = 400;

    protected containerElement: HTMLDivElement;
    protected inputElement: HTMLInputElement;
    protected autoCompleteResult: AutocompleteResult;

    constructor(
        @inject(TYPES.ViewerOptions) protected options: ViewerOptions,
        @inject(TYPES.IActionDispatcherProvider) protected actionDispatcherProvider: IActionDispatcherProvider,
        @inject(GLSP_TYPES.ICommandPaletteActionProviderRegistry) protected actionProvider: ICommandPaletteActionProviderRegistry,
        @inject(TYPES.ILogger) protected logger: ILogger) { }

    show(selectedElements: SModelElement[]) {
        if (!this.containerElement) {
            this.createUiElements();
        }
        this.updatePosition(selectedElements);
        this.containerElement.style.visibility = 'visible';
        this.containerElement.style.opacity = '1';
        if (this.inputElement.value) {
            this.inputElement.setSelectionRange(0, this.inputElement.value.length);
        }
        this.autoCompleteResult = configureAutocomplete(this.autocompleteSettings(selectedElements));
        this.inputElement.focus();
    }

    protected createUiElements() {
        const baseDiv = document.getElementById(this.options.baseDiv);
        if (!baseDiv) {
            this.logger.warn(this, 'Could not obtain sprotty base container for showing command palette');
            return;
        }

        this.containerElement = document.createElement('div');
        this.containerElement.id = `${this.options.baseDiv}_command-palette`;
        this.containerElement.classList.add('command-palette');
        this.containerElement.style.position = 'absolute';
        this.inputElement = document.createElement('input');
        this.inputElement.style.width = '100%';
        this.containerElement.appendChild(this.inputElement);
        if (baseDiv) {
            baseDiv.insertBefore(this.containerElement, baseDiv.firstChild);
        }

        this.inputElement.onblur = () => window.setTimeout(() => this.hide(), 200);
    }

    protected updatePosition(selectedElements: SModelElement[]) {
        let x = this.xOffset;
        let y = this.yOffset;
        if (selectedElements.length === 1) {
            const firstElement = selectedElements[0];
            if (isBoundsAware(firstElement)) {
                const viewport = findParentByFeature(firstElement, isViewport);
                if (viewport) {
                    x += (firstElement.bounds.x - viewport.scroll.x) * viewport.zoom;
                    y += (firstElement.bounds.y - viewport.scroll.y) * viewport.zoom;
                } else {
                    x += firstElement.bounds.x;
                    y += firstElement.bounds.y;
                }
            }
        }
        this.containerElement.style.left = `${x}px`;
        this.containerElement.style.top = `${y}px`;
        this.containerElement.style.width = `${this.defaultWidth}px`;
    }

    private autocompleteSettings(selectedElements: SModelElement[]): AutocompleteSettings<LabeledAction> {
        return {
            input: this.inputElement,
            emptyMsg: "No commands available",
            className: "command-palette-suggestions",
            minLength: -1,
            fetch: (text: string, update: (items: LabeledAction[]) => void) => {
                this.actionProvider()
                    .then(provider => provider.getActions(selectedElements))
                    .then(actions => update(this.filterActions(text, actions)))
                    .catch((reason) => this.logger.error(this, "Failed to obtain actions from command palette action providers", reason));
            },
            onSelect: (item: LabeledAction) => {
                this.executeCommandPaletteAction(item);
            },
            customize: (input: HTMLInputElement, inputRect: ClientRect | DOMRect, container: HTMLDivElement, maxHeight: number) => {
                // move container into our command palette container as this is already positioned correctly
                if (this.containerElement) {
                    this.containerElement.appendChild(container);
                }
            }
        };
    }

    protected filterActions(filterText: string, actions: LabeledAction[]): LabeledAction[] {
        return toArray(actions.filter(action => {
            const label = action.label.toLowerCase();
            const searchWords = filterText.split(' ');
            return searchWords.every(word => label.indexOf(word.toLowerCase()) !== -1);
        }));
    }

    protected executeCommandPaletteAction(item: LabeledAction) {
        this.actionDispatcherProvider()
            .then((actionDispatcher) => actionDispatcher.dispatchAll(item.actions))
            .catch((reason) => this.logger.error(this, 'No action dispatcher available to execute command palette action', reason));
        this.hide();
    }

    hide() {
        if (this.containerElement) {
            this.containerElement.style.visibility = 'hidden';
            this.containerElement.style.opacity = '0';
        }
        if (this.autoCompleteResult) {
            this.autoCompleteResult.destroy();
        }
        // restore focus of sprotty's svg element
        // _sprotty: svg container id as specified in DiagramManagerImpl
        const sprottyDiv = document.getElementById(this.options.baseDiv + "_sprotty");
        if (sprottyDiv) {
            sprottyDiv.focus();
        }
    }
}

@injectable()
export class CommandPaletteActionHandlerInitializer implements IActionHandlerInitializer, IActionHandler {

    @inject(CommandPalette)
    readonly commandPalette: CommandPalette;

    initialize(registry: ActionHandlerRegistry): void {
        registry.register(ShowCommandPaletteAction.KIND, this);
        registry.register(HideCommandPaletteAction.KIND, this);
    }

    handle(action: Action): void | ICommand | Action {
        if (action instanceof ShowCommandPaletteAction) {
            return new CommandPaletteActionCommand((context) => {
                const index = context.root.index;
                const selectedElements = toArray(index.all()
                    .filter(e => action.selectedElementIds.indexOf(e.id) >= 0));
                this.commandPalette.show(selectedElements)
            });
        } else if (action instanceof HideCommandPaletteAction) {
            return new CommandPaletteActionCommand((context) => {
                this.commandPalette.hide();
            });
        }
    }
}

export type CommandEffect = (context: CommandExecutionContext) => void;

/**
 * A system command that doesn't change the model but just performs a specified `effect`.
 */
export class CommandPaletteActionCommand extends SystemCommand {

    constructor(readonly effect: CommandEffect) {
        super();
    }

    execute(context: CommandExecutionContext): CommandResult {
        this.effect(context);
        context.root.index
        return context.root;
    }

    undo(context: CommandExecutionContext): CommandResult {
        return context.root;
    }

    redo(context: CommandExecutionContext): CommandResult {
        return context.root;
    }
}