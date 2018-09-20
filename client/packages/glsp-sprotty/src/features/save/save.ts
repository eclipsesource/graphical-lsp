/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { Action, KeyListener, SModelRoot } from "sprotty/lib";
import { matchesKeystroke } from "sprotty/lib/utils/keyboard";

export class SaveModelAction implements Action {
    static readonly KIND = "saveModel"
    readonly kind = SaveModelAction.KIND
    constructor() { }
}


export class SaveModelKeyboardListener extends KeyListener {
    keyDown(element: SModelRoot, event: KeyboardEvent): Action[] {
        if (matchesKeystroke(event, 'KeyS', 'ctrlCmd')) {
            return [new SaveModelAction()];
        }
        return [];
    }
}