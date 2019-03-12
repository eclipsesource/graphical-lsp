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
import { AggregationEdgeView } from "./views";
import { ArrowEdgeView } from "./views";
import { ClassNode } from "./model";
import { ClassNodeView } from "./views";
import { CompositionEdgeView } from "./views";
import { ConsoleLogger } from "sprotty/lib";
import { Container } from "inversify";
import { ContainerModule } from "inversify";
import { EcoreGraph } from "./model";
import { EdgeWithMultiplicty } from "./model";
import { ExpandButtonView } from "sprotty/lib";
import { HtmlRoot } from "sprotty/lib";
import { HtmlRootView } from "sprotty/lib";
import { Icon } from "./model";
import { IconView } from "./views";
import { InheritanceEdgeView } from "./views";
import { LogLevel } from "sprotty/lib";
import { PreRenderedElement } from "sprotty/lib";
import { PreRenderedView } from "sprotty/lib";
import { SButton } from "sprotty/lib";
import { SCompartment } from "sprotty/lib";
import { SCompartmentView } from "sprotty/lib";
import { SEdge } from "sprotty/lib";
import { SGraphView } from "sprotty/lib";
import { SLabel } from "sprotty/lib";
import { SLabelView } from "sprotty/lib";
import { SRoutingHandle } from "sprotty/lib";
import { SRoutingHandleView } from "sprotty/lib";
import { TYPES } from "sprotty/lib";

import { boundsModule } from "sprotty/lib";
import { buttonModule } from "sprotty/lib";
import { configureModelElement } from "sprotty/lib";
import { configureViewerOptions } from "sprotty/lib";
import { defaultModule } from "sprotty/lib";
import { edgeEditModule } from "sprotty/lib";
import { edgeLayoutModule } from "sprotty/lib";
import { expandModule } from "sprotty/lib";
import { exportModule } from "sprotty/lib";
import { fadeModule } from "sprotty/lib";
import { graphModule } from "sprotty/lib";
import { hoverModule } from "sprotty/lib";
import { modelSourceModule } from "sprotty/lib";
import { moveModule } from "sprotty/lib";
import { routingModule } from "sprotty/lib";
import { selectModule } from "sprotty/lib";
import { undoRedoModule } from "sprotty/lib";
import { updateModule } from "sprotty/lib";
import { viewportModule } from "sprotty/lib";


export default (containerId: string, withSelectionSupport: boolean, needsServerLayout: boolean) => {
    const classDiagramModule = new ContainerModule((bind, unbind, isBound, rebind) => {
        rebind(TYPES.ILogger).to(ConsoleLogger).inSingletonScope();
        rebind(TYPES.LogLevel).toConstantValue(LogLevel.warn);
        const context = { bind, unbind, isBound, rebind };
        configureModelElement(context, 'graph', EcoreGraph, SGraphView);
        configureModelElement(context, 'node:class', ClassNode, ClassNodeView);
        configureModelElement(context, 'node:enum', ClassNode, ClassNodeView);
        configureModelElement(context, 'label:heading', SLabel, SLabelView);
        configureModelElement(context, 'label:prop:attr', SLabel, SLabelView);
        configureModelElement(context, 'label:prop:enum', SLabel, SLabelView);
        configureModelElement(context, 'label:text', SLabel, SLabelView);
        configureModelElement(context, 'comp:comp', SCompartment, SCompartmentView);
        configureModelElement(context, 'comp:header', SCompartment, SCompartmentView);
        configureModelElement(context, 'icon', Icon, IconView);
        configureModelElement(context, 'label:icon', SLabel, SLabelView);
        configureModelElement(context, 'html', HtmlRoot, HtmlRootView);
        configureModelElement(context, 'pre-rendered', PreRenderedElement, PreRenderedView);
        configureModelElement(context, 'button:expand', SButton, ExpandButtonView);
        configureModelElement(context, 'routing-point', SRoutingHandle, SRoutingHandleView);
        configureModelElement(context, 'volatile-routing-point', SRoutingHandle, SRoutingHandleView);
        configureModelElement(context, 'edge:association', SEdge, ArrowEdgeView)
        configureModelElement(context, 'edge:inheritance', SEdge, InheritanceEdgeView)
        configureModelElement(context, 'edge:aggregation', EdgeWithMultiplicty, AggregationEdgeView)
        configureModelElement(context, 'edge:composition', EdgeWithMultiplicty, CompositionEdgeView)
        configureModelElement(context, 'edge', SEdge, ArrowEdgeView)
        configureViewerOptions(context, {
            needsClientLayout: true,
            needsServerLayout,
            baseDiv: containerId
        });
    });

    const container = new Container();
    const modules = [defaultModule, moveModule, boundsModule, undoRedoModule, modelSourceModule, routingModule,
        updateModule, viewportModule, fadeModule, hoverModule, exportModule, expandModule, buttonModule,
        edgeEditModule, edgeLayoutModule, classDiagramModule, graphModule]
    if (withSelectionSupport) {
        modules.push(selectModule)
    }
    container.load(...modules)
    return container;
};
