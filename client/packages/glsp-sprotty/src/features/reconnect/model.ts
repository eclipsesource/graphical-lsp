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
import { createRoutingHandle, Routable, SModelElement, SParentElement, SRoutingHandle } from "sprotty/lib";

const ROUTING_HANDLE_SOURCE_INDEX: number = -2;

export function isRoutable<T extends SModelElement>(element: T): element is T & SParentElement & Routable {
    return (element as any).routingPoints !== undefined
        && typeof ((element as any).route) === 'function'
        && element instanceof SParentElement;
}

export function isReconnectHandle(element: SModelElement | undefined): element is SRoutingHandle {
    return element !== undefined && element instanceof SRoutingHandle;
}

export function addReconnectHandles(element: SParentElement & Routable) {
    removeReconnectHandles(element);
    element.add(createRoutingHandle('source', element.id, ROUTING_HANDLE_SOURCE_INDEX));
    element.add(createRoutingHandle('target', element.id, element.routingPoints.length));
}

export function removeReconnectHandles(element: SParentElement & Routable) {
    element.removeAll(child => child instanceof SRoutingHandle);
}

export function isSourceRoutingHandle(edge: Routable, routingHandle: SRoutingHandle) {
    return routingHandle.pointIndex === ROUTING_HANDLE_SOURCE_INDEX;
}

export function isTargetRoutingHandle(edge: Routable, routingHandle: SRoutingHandle) {
    return routingHandle.pointIndex === edge.routingPoints.length;
}