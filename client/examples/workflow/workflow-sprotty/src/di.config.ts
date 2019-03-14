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

import "../css/diagram.css";

import { ActivityNode } from "./model";
import { ConsoleLogger } from "glsp-sprotty/lib";
import { Container } from "inversify";
import { ContainerModule } from "inversify";
import { DiamondNodeView } from "glsp-sprotty/lib";
import { ExpandButtonView } from "glsp-sprotty/lib";
import { GLSPGraph } from "glsp-sprotty/lib";
import { HtmlRoot } from "glsp-sprotty/lib";
import { HtmlRootView } from "glsp-sprotty/lib";
import { Icon } from "./model";
import { IconView } from "./workflow-views";
import { LogLevel } from "glsp-sprotty/lib";
import { PreRenderedElement } from "glsp-sprotty/lib";
import { PreRenderedView } from "glsp-sprotty/lib";
import { RectangularNode } from "glsp-sprotty/lib";
import { RectangularNodeView } from "glsp-sprotty/lib";
import { SButton } from "glsp-sprotty/lib";
import { SCompartment } from "glsp-sprotty/lib";
import { SCompartmentView } from "glsp-sprotty/lib";
import { SEdge } from "glsp-sprotty/lib";
import { SGraphView } from "glsp-sprotty/lib";
import { SLabel } from "glsp-sprotty/lib";
import { SLabelView } from "glsp-sprotty/lib";
import { SRoutingHandle } from "glsp-sprotty/lib";
import { SRoutingHandleView } from "glsp-sprotty/lib";
import { TaskNode } from "./model";
import { TaskNodeView } from "./workflow-views";
import { TYPES } from "glsp-sprotty/lib";
import { WeightedEdge } from "./model";
import { WeightedEdgeView } from "./workflow-views";
import { WorkflowEdgeView } from "./workflow-views";
import { WorkflowModelFactory } from "./model-factory";

import { boundsModule } from "glsp-sprotty/lib";
import { buttonModule } from "glsp-sprotty/lib";
import { commandPaletteModule } from "glsp-sprotty/lib";
import { configureModelElement } from "glsp-sprotty/lib";
import { defaultGLSPModule } from "glsp-sprotty/lib";
import { defaultModule } from "glsp-sprotty/lib";
import { edgeLayoutModule } from "glsp-sprotty/lib";
import { expandModule } from "glsp-sprotty/lib";
import { exportModule } from "glsp-sprotty/lib";
import { fadeModule } from "glsp-sprotty/lib";
import { hoverModule } from "glsp-sprotty/lib";
import { modelHintsModule } from "glsp-sprotty/lib";
import { modelSourceModule } from "glsp-sprotty/lib";
import { openModule } from "glsp-sprotty/lib";
import { overrideViewerOptions } from "glsp-sprotty/lib";
import { paletteModule } from "glsp-sprotty/lib";
import { requestResponseModule } from "glsp-sprotty/lib";
import { routingModule } from "glsp-sprotty/lib";
import { saveModule } from "glsp-sprotty/lib";
import { selectModule } from "glsp-sprotty/lib";
import { toolFeedbackModule } from "glsp-sprotty/lib";
import { viewportModule } from "glsp-sprotty/lib";

import executeCommandModule from "glsp-sprotty/lib/features/execute/di.config";

const workflowDiagramModule = new ContainerModule((bind, unbind, isBound, rebind) => {
    rebind(TYPES.ILogger).to(ConsoleLogger).inSingletonScope()
    rebind(TYPES.LogLevel).toConstantValue(LogLevel.warn)
    rebind(TYPES.IModelFactory).to(WorkflowModelFactory).inSingletonScope()
    const context = { bind, unbind, isBound, rebind };
    configureModelElement(context, 'graph', GLSPGraph, SGraphView);
    configureModelElement(context, 'task:automated', TaskNode, TaskNodeView);
    configureModelElement(context, 'task:manual', TaskNode, TaskNodeView);
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
    configureModelElement(context, 'activityNode:merge', ActivityNode, DiamondNodeView)
    configureModelElement(context, 'activityNode:decision', ActivityNode, DiamondNodeView)
    configureModelElement(context, 'node', RectangularNode, RectangularNodeView)
});

export default function createContainer(widgetId: string): Container {
    const container = new Container();

    container.load(defaultModule, defaultGLSPModule, selectModule, boundsModule, viewportModule,
        hoverModule, fadeModule, exportModule, expandModule, openModule, buttonModule, modelSourceModule,
        workflowDiagramModule, saveModule, executeCommandModule, toolFeedbackModule, modelHintsModule,
        commandPaletteModule, paletteModule, requestResponseModule, routingModule, edgeLayoutModule);

    overrideViewerOptions(container, {
        needsClientLayout: true,
        needsServerLayout: false,
        baseDiv: widgetId,
        hiddenDiv: widgetId + "_hidden"
    })

    return container
}
