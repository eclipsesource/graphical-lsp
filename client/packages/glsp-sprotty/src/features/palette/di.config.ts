/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Camille Letavernier - initial API and implementation
 ******************************************************************************/
import { ContainerModule } from "inversify";
import { TYPES } from "sprotty/lib";
import { ConnectionTool } from "./connection-tool";
import { CreationTool } from "./creation-tool";
import { DeleteTool } from "./delete-tool";
import { OperationServiceImpl, OP_TYPES } from "./operation-service";

const paletteModule = new ContainerModule(bind => {
    bind(TYPES.MouseListener).to(ConnectionTool);
    bind(TYPES.MouseListener).to(CreationTool);
    bind(TYPES.MouseListener).to(DeleteTool);
    bind(OP_TYPES.OperationService).to(OperationServiceImpl).inSingletonScope();
});

export default paletteModule;
