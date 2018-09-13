/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { RequestModelAction, RequestOperationsAction } from "glsp-sprotty/lib";
import { DiagramWidget } from "theia-glsp/lib";

export class GLSPDiagramWidget extends DiagramWidget {

    protected sendInitialRequestMessages() {
        this.modelSource.handle(new RequestModelAction({
            sourceUri: this.uri.toString(),
            diagramType: this.diagramType,
            needsClientLayout: 'true'
        }))

        this.modelSource.handle(new RequestOperationsAction())
    }

}