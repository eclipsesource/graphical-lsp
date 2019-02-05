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
import { WidgetOpenerOptions } from "@theia/core/lib/browser";
import URI from "@theia/core/lib/common/uri";
import { EditorPreferences } from "@theia/editor/lib/browser";
import { inject, injectable } from "inversify";
import { DiagramManagerImpl, DiagramWidgetFactory } from "theia-glsp/lib";
import { GLSPDiagramWidget } from "./glsp-diagram-widget";

@injectable()
export abstract class GLSPDiagramManager extends DiagramManagerImpl {
    @inject(EditorPreferences)
    protected readonly editorPreferences: EditorPreferences;
    abstract get fileExtensions(): string[];

    canHandle(uri: URI, options?: WidgetOpenerOptions | undefined): number {
        for (const extension of this.fileExtensions) {
            if (uri.path.toString().endsWith(extension)) {
                return 101;
            }
        }
        return 0
    }

    protected get diagramWidgetFactory(): DiagramWidgetFactory {
        return options => new GLSPDiagramWidget(options, this.editorPreferences);
    }

}
