/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *  Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { Action, ElementAndBounds, Point } from "sprotty/lib";
import { OperationKind } from "./set-operations";

export class CreateNodeOperationAction implements Action {
    readonly kind = OperationKind.CREATE_NODE;

    constructor(public readonly elementTypeId: string,
        public readonly location?: Point,
        public readonly containerId?: string) { }
}

export class CreateConnectionOperationAction implements Action {
    readonly kind = OperationKind.CREATE_CONNECTION;

    constructor(public readonly elementTypeId: string,
        public readonly sourceElementId?: string,
        public readonly targetElementId?: string) { }
}

export class DeleteElementOperationAction implements Action {
    kind = OperationKind.DELETE_ELEMENT;

    constructor(readonly elementIds: string[]) { }
}

export class ChangeBoundsOperationAction implements Action {
    readonly kind = OperationKind.CHANGE_BOUNDS;

    constructor(public newBounds: ElementAndBounds[]) { }
}

export class ChangeContainerOperation implements Action {
    readonly kind = OperationKind.CHANGE_CONTAINER;

    constructor(public readonly elementId: string,
        public readonly targetContainerId: string,
        public readonly location?: string) { }
}

export class GenericOperationAction implements Action {
    readonly kind = OperationKind.GENERIC;

    constructor(public readonly id: string,
        public readonly elementId?: string,
        public readonly location?: Point) { }
}

