

import { Action, SModelRoot, KeyListener } from "sprotty/lib";
import {matchesKeystroke} from "sprotty/lib/utils/keyboard"

export class SaveModelAction implements Action {
    static readonly KIND= "saveModel"
    readonly kind = SaveModelAction.KIND
    constructor() { }
}


export class SaveModelKeyboardListener extends  KeyListener{
    keyDown(element: SModelRoot, event: KeyboardEvent): Action[] {
        if (matchesKeystroke(event, 'KeyS', 'ctrlCmd')) {
            return [new SaveModelAction()];
        }
        return [];
    }
}