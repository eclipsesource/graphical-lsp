/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Martin Fleck - initial API and implementation
 ******************************************************************************/
import { ContainerModule } from "inversify";
import { TYPES } from "sprotty/lib";
import { SwitchResizeModeCommand } from "./resize";

const changeBoundsCommandModule = new ContainerModule(bind => {
    bind(TYPES.ICommand).toConstructor(SwitchResizeModeCommand);
})

export default changeBoundsCommandModule;
