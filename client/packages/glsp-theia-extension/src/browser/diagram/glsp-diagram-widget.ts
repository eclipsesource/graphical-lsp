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
import { DiagramWidget } from "theia-glsp/lib";
import { RequestModelAction, RequestToolsAction } from "glsp-sprotty/lib";

export class GLSPDiagramWidget extends DiagramWidget {

    protected sendInitialRequestMessages() {
        this.modelSource.handle(new RequestModelAction({
            sourceUri: this.uri.toString(),
            diagramType: this.diagramType,
            needsClientLayout: 'true'
        }))

        // this.modelSource.handle(new RequestToolsAction())
    }
}