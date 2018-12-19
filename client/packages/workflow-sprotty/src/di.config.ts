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

import {
    boundsModule, buttonModule, commandPaletteModule, configureModelElement, ConsoleLogger, defaultGLSPModule, defaultModule, DiamondNodeView, ExpandButtonView, //
    expandModule, exportModule, fadeModule, GLSPGraph, hoverModule, HtmlRoot, HtmlRootView, LogLevel, modelAccessModule, modelHintsModule, modelSourceModule, //
    openModule, overrideViewerOptions, PreRenderedElement, PreRenderedView, RectangularNode, RectangularNodeView, resizeCommandModule, saveModule, SButton, //
    SCompartment, SCompartmentView, SEdge, selectModule, SGraphView, SLabel, SLabelView, SResizeHandle, SRoutingHandle, SRoutingHandleView, toolFeedbackModule, //
    toolManagerModule, TYPES, undoRedoModule, viewportModule
} from "glsp-sprotty/lib";
import executeCommandModule from "glsp-sprotty/lib/features/execute/di.config";
import { Container, ContainerModule } from "inversify";
import { ActivityNode, Icon, TaskNode, WeightedEdge } from "./model";
import { WorkflowModelFactory } from "./model-factory";
import { IconView, SResizeHandleView, TaskNodeView, WeightedEdgeView, WorkflowEdgeView } from "./workflow-views";


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
    configureModelElement(context, 'resize-handle', SResizeHandle, SResizeHandleView);
    configureModelElement(context, 'icon', Icon, IconView);
    configureModelElement(context, 'node:activity', ActivityNode, DiamondNodeView)
    configureModelElement(context, 'node', RectangularNode, RectangularNodeView)
});

export default function createContainer(widgetId: string): Container {
    const container = new Container();

    container.load(defaultModule, selectModule, boundsModule, undoRedoModule, viewportModule,
        hoverModule, fadeModule, exportModule, expandModule, openModule, buttonModule, modelSourceModule,
        workflowDiagramModule, saveModule, executeCommandModule, toolManagerModule, toolFeedbackModule, defaultGLSPModule, modelHintsModule,
        resizeCommandModule, modelAccessModule, commandPaletteModule);

    overrideViewerOptions(container, {
        needsClientLayout: true,
        needsServerLayout: false,
        baseDiv: widgetId
    })

    return container
}
