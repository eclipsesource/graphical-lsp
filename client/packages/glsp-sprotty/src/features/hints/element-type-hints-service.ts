/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { injectable } from "inversify";
import { SModelElement } from "sprotty/lib";
import { contains } from "../../utils/array-utils";
import { EdgeTypeHint, NodeTypeHint, SetTypeHintsAction, TypeHint } from "./action-definition";

export interface TypeHintsService {
    initialize(action: SetTypeHintsAction): void
    isRepositionable(element: SModelElement): boolean
    isDeletable(element: SModelElement): boolean
    isResizeable(element: SModelElement): boolean

    isContainer(element: SModelElement): boolean
    isContainerChangeAllowed(newContainer: SModelElement, element: SModelElement): boolean

    isRoutable(element: SModelElement): boolean
    isAllowedEdgeType(source: SModelElement, target: SModelElement, edgeTypeId: string): boolean
}

@injectable()
export class DefaultTypeHintsService implements TypeHintsService {
    protected typeHints: Map<string, TypeHint> = new Map
    protected nodeTypeHints: Map<string, NodeTypeHint> = new Map
    protected edgeTypeHints: Map<string, EdgeTypeHint> = new Map
    initialize(action: SetTypeHintsAction): void {
        this.nodeTypeHints = new Map
        this.edgeTypeHints = new Map
        action.nodeHints.forEach(hint => {
            this.typeHints.set(hint.elementTypeId, hint)
            this.nodeTypeHints.set(hint.elementTypeId, hint)
        });
        action.edgeHints.forEach(hint => {
            this.typeHints.set(hint.elementTypeId, hint)
            this.edgeTypeHints.set(hint.elementTypeId, hint)
        });
    }

    isRepositionable(element: SModelElement): boolean {
        return getBooleanFeature(this.typeHints, "repositionable", element)
    }

    isDeletable(element: SModelElement): boolean {
        return getBooleanFeature(this.typeHints, "deletable", element)
    }
    isResizeable(element: SModelElement): boolean {
        return getBooleanFeature(this.nodeTypeHints, "resizable", element)
    }

    isRoutable(element: SModelElement): boolean {
        return getBooleanFeature(this.edgeTypeHints, "routable", element)
    }

    isContainer(element: SModelElement): boolean {
        const hint = this.nodeTypeHints.get(element.type)
        return (hint && hint.containableElementTypeIds) ? hint.containableElementTypeIds.length > 0 : false
    }

    isContainerChangeAllowed(newContainer: SModelElement, element: SModelElement): boolean {
        const hint = this.nodeTypeHints.get(newContainer.id)
        return (hint && hint.containableElementTypeIds) ?
            contains(hint.containableElementTypeIds, element.type) : false
    }

    isAllowedEdgeType(source: SModelElement, target: SModelElement, edgeTypeId: string): boolean {
        const hint = this.edgeTypeHints.get(edgeTypeId)
        return hint ? contains(hint.sourceElementTypeIds, source.type) &&
            contains(hint.targetElementTypeIds, target.type) : false
    }
}

function getBooleanFeature<T extends TypeHint, K extends keyof T>
    (typeMap: Map<string, T>, property: K, element: SModelElement): boolean {
    const hint = typeMap.get(element.type)
    return hint ? (hint as any)[property] as boolean : false
}