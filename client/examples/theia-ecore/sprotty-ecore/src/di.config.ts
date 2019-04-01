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
import { Container, ContainerModule } from "inversify";
import {
    boundsModule,
    buttonModule,
    configureModelElement,
    configureViewerOptions,
    ConsoleLogger,
    defaultModule,
    edgeEditModule,
    edgeLayoutModule,
    ExpandButtonView,
    expandModule,
    exportModule,
    fadeModule,
    graphModule,
    hoverModule,
    HtmlRoot,
    HtmlRootView,
    LogLevel,
    modelSourceModule,
    PolylineEdgeView,
    PreRenderedElement,
    PreRenderedView,
    RectangularNodeView,
    routingModule,
    SButton,
    SCompartment,
    SCompartmentView,
    SEdge,
    selectModule,
    SGraphView,
    SLabel,
    SLabelView,
    SNode,
    SRoutingHandle,
    SRoutingHandleView,
    TYPES,
    undoRedoModule,
    updateModule,
    viewportModule
} from "sprotty/lib";

import { ClassNode, EcoreGraph, Icon } from "./model";
import {
    AggregationEdgeView,
    ArrowEdgeView,
    ClassNodeView,
    CompositionEdgeView,
    IconView,
    InheritanceEdgeView
} from "./views";


export default (containerId: string, withSelectionSupport: boolean, needsServerLayout: boolean) => {
    const classDiagramModule = new ContainerModule((bind, unbind, isBound, rebind) => {
        rebind(TYPES.ILogger).to(ConsoleLogger).inSingletonScope();
        rebind(TYPES.LogLevel).toConstantValue(LogLevel.warn);
        const context = { bind, unbind, isBound, rebind };
        configureModelElement(context, 'graph', EcoreGraph, SGraphView);
        configureModelElement(context, 'node:class', ClassNode, ClassNodeView);
        configureModelElement(context, 'node:enum', ClassNode, ClassNodeView);
        configureModelElement(context, 'node:datatype', ClassNode, ClassNodeView);
        configureModelElement(context, 'label:heading', SLabel, SLabelView);
        configureModelElement(context, 'node:attribute', SNode, RectangularNodeView);
        configureModelElement(context, 'node:enumliteral', SNode, RectangularNodeView);
        configureModelElement(context, 'node:operation', SNode, RectangularNodeView);
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
        configureModelElement(context, 'edge:reference', SEdge, PolylineEdgeView);
        configureModelElement(context, 'edge:inheritance', SEdge, InheritanceEdgeView);
        configureModelElement(context, 'edge:aggregation', SEdge, AggregationEdgeView);
        configureModelElement(context, 'edge:composition', SEdge, CompositionEdgeView);
        configureModelElement(context, 'edge', SEdge, ArrowEdgeView);
        configureViewerOptions(context, {
            needsClientLayout: true,
            needsServerLayout,
            baseDiv: containerId
        });
    });

    const container = new Container();
    const modules = [defaultModule, selectModule, boundsModule, undoRedoModule,
        viewportModule, fadeModule, hoverModule, exportModule, expandModule, buttonModule,
        updateModule, graphModule, routingModule, edgeEditModule, edgeLayoutModule,
        modelSourceModule, classDiagramModule];

    container.load(...modules);
    return container;
};
