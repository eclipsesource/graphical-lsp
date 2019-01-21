/*******************************************************************************
 * Copyright (c) 2018 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Philip Langer - initial API and implementation
 ******************************************************************************/

export const GLSP_TYPES = {
    ToolManager: Symbol.for("ToolManager"),
    ICommandPaletteActionProvider: Symbol.for("ICommandPaletteActionProvider"),
    ICommandPaletteActionProviderRegistry: Symbol.for("ICommandPaletteActionProviderRegistry"),
    IFeedbackActionDispatcher: Symbol.for("IFeedbackActionDispatcher"),
    ToolFactory: Symbol.for("Factory<Tool>"),
    IModelUpdateNotifier: Symbol.for("IModelUpdateNotifier"),
    IModelUpdateObserver: Symbol.for("IModelUpdateObserver"),
    IReadonlyModelAccessProvider: Symbol.for("IReadonlyModelAccessProvider"),
    IDiagramUIExtension: Symbol.for("DiagramUIExtension")
}