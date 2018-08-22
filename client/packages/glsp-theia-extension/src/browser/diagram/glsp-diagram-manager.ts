/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { injectable, inject } from "inversify";
import { DiagramManagerImpl, DiagramWidgetFactory } from "theia-sprotty/lib";
import { GLSPDiagramWidget } from "./glsp-diagram-widget";

@injectable()
export abstract class GLSPDiagramManager extends DiagramManagerImpl {

    protected get diagramWidgetFactory(): DiagramWidgetFactory {
        return options => new GLSPDiagramWidget(options)
    }







}