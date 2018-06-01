/*
 * Copyright (C) 2017 TypeFox and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 */

import { Container, ContainerModule } from "inversify";
import {
    defaultModule, TYPES, SGraphView, SLabelView, SCompartmentView, PolylineEdgeView,
    ConsoleLogger, LogLevel, boundsModule, moveModule, selectModule, undoRedoModule,
    viewportModule, hoverModule, HtmlRootView, PreRenderedView, exportModule, expandModule,
    fadeModule, ExpandButtonView, buttonModule, SRoutingHandleView,
    RectangularNodeView, openModule, modelSourceModule, overrideViewerOptions, ViewRegistry
} from "sprotty/lib";


import { WeightedEdgeView, IconView, ActivityNodeView } from "./workflow-views";
import { WorkflowModelFactory } from "./model-factory";


const workflowDiagramModule = new ContainerModule((bind, unbind, isBounds, rebind) => {
    rebind(TYPES.ILogger).to(ConsoleLogger).inSingletonScope()
    rebind(TYPES.LogLevel).toConstantValue(LogLevel.warn)
    rebind(TYPES.IModelFactory).to(WorkflowModelFactory).inSingletonScope()
});
export default function createContainer(widgetId: string): Container {
    const container = new Container();

    container.load(defaultModule, selectModule, moveModule, boundsModule, undoRedoModule, viewportModule,
        hoverModule, fadeModule, exportModule, expandModule, openModule, buttonModule, modelSourceModule,
        workflowDiagramModule);
    // container.bind(TYPES.ModelSource).to(WebSocketDiagramServer).inSingletonScope();
    overrideViewerOptions(container, {
        needsClientLayout: true,
        needsServerLayout: true,
        baseDiv: widgetId
    })
    const viewRegistry = container.get<ViewRegistry>(TYPES.ViewRegistry)
    viewRegistry.register('graph', SGraphView);
    viewRegistry.register('label:heading', SLabelView);
    viewRegistry.register('label:text', SLabelView);
    viewRegistry.register('comp:comp', SCompartmentView);
    viewRegistry.register('comp:header', SCompartmentView);
    viewRegistry.register('label:icon', SLabelView);
    viewRegistry.register('edge', PolylineEdgeView);
    viewRegistry.register('html', HtmlRootView);
    viewRegistry.register('pre-rendered', PreRenderedView);
    viewRegistry.register('button:expand', ExpandButtonView);
    viewRegistry.register('routing-point', SRoutingHandleView);
    viewRegistry.register('volatile-routing-point', SRoutingHandleView);
    viewRegistry.register('edge:weighted', WeightedEdgeView)
    viewRegistry.register('icon', IconView);
    viewRegistry.register('node:activity', ActivityNodeView)
    viewRegistry.register('node', RectangularNodeView)

    return container
}
