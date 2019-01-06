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
import { SGraph } from "sprotty/lib";
import { Saveable, saveFeature } from "../features/save/model";

export class GLSPGraph extends SGraph implements Saveable {
    dirty: boolean;

    hasFeature(feature: symbol) {
        return feature === saveFeature || super.hasFeature(feature);
    }
}