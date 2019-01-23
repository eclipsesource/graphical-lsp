/*******************************************************************************
 * Copyright (c) 2019 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/

import { SEdge, SModelElement, SNode } from "sprotty/lib";

export const edgeEditConfig = Symbol.for("edgeEditConfiguration")
export const nodeEditConfig = Symbol.for("nodeEditConfiguration")

export function isConfigurableElement(element: SModelElement): element is SModelElement & EditConfig {
    return (<any>element).configType !== undefined && typeof ((<any>element).configType) === "symbol"
}

export function isConfigurableEdge(element: SModelElement): element is SEdge & EdgeEditConfig {
    return element instanceof SEdge && isConfigurableElement(element) && element.configType === edgeEditConfig
}
export function isConfigurableNode(element: SModelElement): element is SNode & NodeEditConfig {
    return element instanceof SNode && isConfigurableElement(element) && element.configType === nodeEditConfig
}

export interface EditConfig {
    deletable: boolean
    repositionable: boolean
    configType?: symbol
}

export interface NodeEditConfig extends EditConfig {
    resizable: boolean
    isContainer(): boolean
    isContainableElement(element: SModelElement): boolean
}

export interface EdgeEditConfig extends EditConfig {
    routable: boolean
    isAllowedSource(element: SModelElement): boolean
    isAllowedTarget(element: SModelElement): boolean
}

