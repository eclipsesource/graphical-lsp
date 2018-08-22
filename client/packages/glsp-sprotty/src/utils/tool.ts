/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
export namespace ToolType {
    export const CONNECTION = 'connection-tool'
    export const CREATION = 'creation-tool'
}
export interface Tool {
    /**
     * The tool identifier.
     */
    readonly id: string;

    /**
     * The tool name.
     */
    readonly name: string;

    /**
     * The tool type.
     */
    readonly toolType: string;

}