/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { ContainerModule } from "inversify";
import { TYPES } from "sprotty/lib";
import { GLSP_TYPES } from "../../types";
import { TypeHintsActionIntializer } from "./type-hints-action-initializer";

const modelHintsModule = new ContainerModule(bind => {
    bind(TypeHintsActionIntializer).toSelf().inSingletonScope()
    bind(TYPES.IActionHandlerInitializer).toService(TypeHintsActionIntializer)
    bind(GLSP_TYPES.IModelUpdateObserver).toService(TypeHintsActionIntializer)
})

export default modelHintsModule;