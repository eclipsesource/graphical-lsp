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
    boundsModule, buttonModule, configureModelElement, configureViewerOptions, ConsoleLogger, defaultModule, edgeEditModule, //
    ExpandButtonView, expandModule, exportModule, fadeModule, hoverModule, HtmlRoot, HtmlRootView, LogLevel, modelSourceModule, moveModule, //
    PolylineEdgeView, PreRenderedElement, PreRenderedView, routingModule, SButton, SCompartment, SCompartmentView, SEdge, selectModule, SGraphView, //
    SLabel, SLabelView, SRoutingHandle, SRoutingHandleView, TYPES, undoRedoModule, updateModule, viewportModule
} from "sprotty/lib";
import { ClassNode, EcoreGraph, EdgeWithMultiplicty, Icon, Link } from "./model";
import { AggregationEdgeView, ArrowEdgeView, ClassNodeView, CompositionEdgeView, IconView, InheritanceEdgeView, LinkView } from "./views";

export default (containerId: string, withSelectionSupport: boolean, needsServerLayout: boolean) => {
    const classDiagramModule = new ContainerModule((bind, unbind, isBound, rebind) => {
        rebind(TYPES.ILogger).to(ConsoleLogger).inSingletonScope();
        rebind(TYPES.LogLevel).toConstantValue(LogLevel.warn);
        const context = { bind, unbind, isBound, rebind };
        configureModelElement(context, 'graph', EcoreGraph, SGraphView);
        configureModelElement(context, 'node:class', ClassNode, ClassNodeView);
        configureModelElement(context, 'label:heading', SLabel, SLabelView);
        configureModelElement(context, 'label:text', SLabel, SLabelView);
        configureModelElement(context, 'comp:comp', SCompartment, SCompartmentView);
        configureModelElement(context, 'comp:header', SCompartment, SCompartmentView);
        configureModelElement(context, 'icon', Icon, IconView);
        configureModelElement(context, 'label:icon', SLabel, SLabelView);
        configureModelElement(context, 'edge:straight', SEdge, PolylineEdgeView);
        configureModelElement(context, 'html', HtmlRoot, HtmlRootView);
        configureModelElement(context, 'pre-rendered', PreRenderedElement, PreRenderedView);
        configureModelElement(context, 'button:expand', SButton, ExpandButtonView);
        configureModelElement(context, 'routing-point', SRoutingHandle, SRoutingHandleView);
        configureModelElement(context, 'volatile-routing-point', SRoutingHandle, SRoutingHandleView);
        configureModelElement(context, 'edge:association', SEdge, ArrowEdgeView)
        configureModelElement(context, 'edge:inheritance', SEdge, InheritanceEdgeView)
        configureModelElement(context, 'edge:aggregation', EdgeWithMultiplicty, AggregationEdgeView)
        configureModelElement(context, 'edge:composition', EdgeWithMultiplicty, CompositionEdgeView)
        configureModelElement(context, 'link', Link, LinkView)
        configureViewerOptions(context, {
            needsClientLayout: true,
            needsServerLayout,
            baseDiv: containerId
        });
    });

    const container = new Container();
    const modules = [defaultModule, moveModule, boundsModule, undoRedoModule, modelSourceModule, routingModule,
        updateModule, viewportModule, fadeModule, hoverModule, exportModule, expandModule, buttonModule,
        edgeEditModule, classDiagramModule]
    if (withSelectionSupport) {
        modules.push(selectModule)
    }
    container.load(...modules)
    return container;
};
