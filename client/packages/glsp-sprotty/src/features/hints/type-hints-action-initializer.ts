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
import { injectable } from "inversify";
import { Action, ActionHandlerRegistry, IActionHandler, IActionHandlerInitializer, ICommand, SModelElement, SModelElementSchema, SModelRoot } from "sprotty/lib";
import { IModelUpdateObserver } from "../../base/command-stack";
import {
    EdgeEditConfig, edgeEditConfig, EditConfig, IEditConfigProvider, isEdgeEditConfig, //
    isNodeEditConfig, NodeEditConfig, nodeEditConfig
} from "../../base/edit-config/edit-config";
import { contains } from "../../utils/array-utils";
import { EdgeTypeHint, isSetTypeHintsAction, NodeTypeHint, SetTypeHintsAction } from "./action-definition";


@injectable()
export class TypeHintsActionIntializer implements IActionHandlerInitializer, IActionHandler, IModelUpdateObserver, IEditConfigProvider {

    protected editConfigs: Map<string, EditConfig> = new Map
    handle(action: Action): ICommand | Action | void {
        if (isSetTypeHintsAction(action)) {
            action.nodeHints.forEach(hint => this.editConfigs.set(hint.elementTypeId, createNodeEditConfig(hint)))
            action.edgeHints.forEach(hint => this.editConfigs.set(hint.elementTypeId, createEdgeEditConfig(hint)))
        }
    }

    initialize(registry: ActionHandlerRegistry): void {
        registry.register(SetTypeHintsAction.KIND, this)
    }

    beforeServerUpdate(model: SModelRoot) {
        this.applyConfig(model)
    }

    applyConfig(model: SModelRoot) {
        model.index.all().forEach(element => {
            const config = this.editConfigs.get(element.type)
            if (config) {
                Object.keys(config).forEach(key => (<any>element)[key] = (<any>config)[key])
            }
        })
    }

    getEditConfig(input: SModelElement | SModelElementSchema | string): EditConfig | undefined {
        return this.editConfigs.get(getElementTypeId(input))
    }

    getAllEdgeEditConfigs(): EdgeEditConfig[] {
        const configs: EdgeEditConfig[] = []
        this.editConfigs.forEach((value, key) => {
            if (isEdgeEditConfig(value)) {
                configs.push(value)
            }
        })
        return configs
    }

    getAllNodeEditConfigs(): NodeEditConfig[] {
        const configs: NodeEditConfig[] = []
        this.editConfigs.forEach((value, key) => {
            if (isNodeEditConfig(value)) {
                configs.push(value)
            }
        })
        return configs
    }
}

export function createNodeEditConfig(hint: NodeTypeHint): NodeEditConfig {
    return <NodeEditConfig>{
        elementTypeId: hint.elementTypeId,
        deletable: hint.deletable,
        repositionable: hint.repositionable,
        resizable: hint.resizable,
        configType: nodeEditConfig,
        isContainableElement: (element) => { return hint.containableElementTypeIds ? contains(hint.containableElementTypeIds, getElementTypeId(element)) : false },
        isContainer: () => { return hint.containableElementTypeIds ? hint.containableElementTypeIds.length > 0 : false }
    }
}


export function createEdgeEditConfig(hint: EdgeTypeHint): EdgeEditConfig {
    return <EdgeEditConfig>{
        elementTypeId: hint.elementTypeId,
        deletable: hint.deletable,
        repositionable: hint.repositionable,
        routable: hint.routable,
        configType: edgeEditConfig,
        isAllowedSource: (source) => { return contains(hint.sourceElementTypeIds, getElementTypeId(source)) },
        isAllowedTarget: (target) => { return contains(hint.targetElementTypeIds, getElementTypeId(target)) }
    }
}

function getElementTypeId(input: SModelElement | SModelElementSchema | string) {
    if (typeof (input) === 'string') {
        return <string>input
    } else {
        return <string>(<any>input)["type"]
    }
}