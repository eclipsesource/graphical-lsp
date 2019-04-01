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
import {
    getBasicType,
    getSubType,
    SChildElement,
    SGraphFactory,
    SModelElementSchema,
    SParentElement
} from "glsp-sprotty/lib";

import { ActivityNode, TaskNode, WeightedEdge } from "./model";
import { ActivityNodeSchema, TaskNodeSchema, WeightedEdgeSchema } from "./model-schema";

export class WorkflowModelFactory extends SGraphFactory {
    createElement(schema: SModelElementSchema, parent?: SParentElement): SChildElement {
        if (this.isTaskNodeSchema(schema)) {
            return this.initializeChild(new TaskNode(), schema, parent);
        } else if (this.isWeightedEdgeSchema(schema)) {
            return this.initializeChild(new WeightedEdge(), schema, parent);
        } else if (this.isActivityNodeSchema(schema)) {
            return this.initializeChild(new ActivityNode(), schema, parent);
        } else {
            return super.createElement(schema, parent);
        }
    }

    isTaskNodeSchema(schema: SModelElementSchema): schema is TaskNodeSchema {
        return getBasicType(schema) === 'task';
    }

    isActivityNodeSchema(schema: SModelElementSchema): schema is ActivityNodeSchema {
        return getBasicType(schema) === 'activityNode';
    }
    isWeightedEdgeSchema(schema: SModelElementSchema): schema is WeightedEdgeSchema {
        return getBasicType(schema) === 'edge' && getSubType(schema) === 'weighted';
    }
}

