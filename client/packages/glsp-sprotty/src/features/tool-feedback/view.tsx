/*******************************************************************************
 * Copyright (c) 2018 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/

/** @jsx svg */
import { svg } from 'snabbdom-jsx';

import { VNode } from "snabbdom/vnode";
import { IView, ORIGIN_POINT, Point, RenderingContext, SModelElement } from "sprotty/lib";


/**
* This view is used for the invisible end of the feedback edge.
* A feedback edge is shown as a visual feedback when creating edges.
*/
export class FeedbackEdgeEnd implements IView {
    render(model: Readonly<SModelElement>, context: RenderingContext): VNode {
        const position: Point = (model as any).position || ORIGIN_POINT;
        return <g x={position.x} y={position.y} />;
    }
}