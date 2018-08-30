/*
 * Copyright (C) 2017 TypeFox and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 */

import { ContainerModule } from "inversify";
import { TYPES } from "sprotty/lib";
import { ConnectionTool } from "./connection-tool";
import { OP_TYPES, OperationServiceImpl } from "./operation-service";
import { CreationTool } from "./creation-tool";
import { DeleteTool } from "./delete-tool";

const paletteModule = new ContainerModule(bind => {
    bind(TYPES.MouseListener).to(ConnectionTool);
    bind(TYPES.MouseListener).to(CreationTool);
    bind(TYPES.MouseListener).to(DeleteTool);
    bind(OP_TYPES.OperationService).to(OperationServiceImpl).inSingletonScope();
});

export default paletteModule;
