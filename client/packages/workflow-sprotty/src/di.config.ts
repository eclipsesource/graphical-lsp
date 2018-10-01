/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/

import { Container, ContainerModule } from "inversify";
import {
    defaultModule, TYPES, SGraphView, SLabelView, SCompartmentView, PolylineEdgeView,
    ConsoleLogger, LogLevel, boundsModule, moveModule, selectModule, undoRedoModule,
    viewportModule, hoverModule, HtmlRootView, PreRenderedView, exportModule, expandModule,
    fadeModule, ExpandButtonView, buttonModule, SRoutingHandleView, openModule, modelSourceModule, overrideViewerOptions, configureModelElement, SLabel, SCompartment, SEdge, PreRenderedElement, HtmlRoot, SButton, SRoutingHandle, RectangularNode, RectangularNodeView
} from "glsp-sprotty/lib";


import { WeightedEdgeView, IconView, ActivityNodeView, TaskNodeView, WorkflowEdgeView } from "./workflow-views";
import { WorkflowModelFactory } from "./model-factory";
import { TaskNode, WeightedEdge, Icon, ActivityNode } from "./model";
import { saveModule, paletteModule, moveToolModule, GLSPGraph } from "glsp-sprotty/lib"


const workflowDiagramModule = new ContainerModule((bind, unbind, isBound, rebind) => {
    rebind(TYPES.ILogger).to(ConsoleLogger).inSingletonScope()
    rebind(TYPES.LogLevel).toConstantValue(LogLevel.warn)
    rebind(TYPES.IModelFactory).to(WorkflowModelFactory).inSingletonScope()
    const context = { bind, unbind, isBound, rebind };
    configureModelElement(context, 'graph', GLSPGraph, SGraphView);
    configureModelElement(context, 'node:task', TaskNode, TaskNodeView);
    configureModelElement(context, 'label:heading', SLabel, SLabelView);
    configureModelElement(context, 'label:text', SLabel, SLabelView);
    configureModelElement(context, 'comp:comp', SCompartment, SCompartmentView);
    configureModelElement(context, 'comp:header', SCompartment, SCompartmentView);
    configureModelElement(context, 'label:icon', SLabel, SLabelView);
    configureModelElement(context, 'html', HtmlRoot, HtmlRootView);
    configureModelElement(context, 'pre-rendered', PreRenderedElement, PreRenderedView);
    configureModelElement(context, 'button:expand', SButton, ExpandButtonView);
    configureModelElement(context, 'routing-point', SRoutingHandle, SRoutingHandleView);
    configureModelElement(context, 'volatile-routing-point', SRoutingHandle, SRoutingHandleView);
    configureModelElement(context, 'edge', SEdge, WorkflowEdgeView)
    configureModelElement(context, 'edge:weighted', WeightedEdge, WeightedEdgeView)
    configureModelElement(context, 'icon', Icon, IconView);
    configureModelElement(context, 'node:activity', ActivityNode, ActivityNodeView)
    configureModelElement(context, 'node', RectangularNode, RectangularNodeView)
});
export default function createContainer(widgetId: string): Container {
    const container = new Container();

    container.load(defaultModule, selectModule, moveModule, boundsModule, undoRedoModule, viewportModule,
        hoverModule, fadeModule, exportModule, expandModule, openModule, buttonModule, modelSourceModule,
        workflowDiagramModule, saveModule, paletteModule, moveToolModule);


    overrideViewerOptions(container, {
        needsClientLayout: true,
        needsServerLayout: false,
        baseDiv: widgetId
    })


    return container
}
