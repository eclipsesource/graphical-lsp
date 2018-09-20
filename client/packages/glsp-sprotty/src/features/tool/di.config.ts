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

import { ContainerModule } from "inversify";
import { TYPES } from "sprotty/lib/base/types";
import { MoveTool } from "./move-tool";

const moveToolModule = new ContainerModule(bind => {
    bind(TYPES.MouseListener).to(MoveTool);
});

export default moveToolModule;
