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
import { GLSP_TYPES } from "../../types";
import { DefaultTypeHintsService } from "./element-type-hints-service";

const modelHintsModule = new ContainerModule(bind => {
    bind(GLSP_TYPES.TypeHintsService).to(DefaultTypeHintsService).inSingletonScope()
})

export default modelHintsModule;