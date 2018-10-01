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
import { getBasicType, getSubType, SChildElement, SGraphFactory, SModelElementSchema, SParentElement } from "glsp-sprotty/lib";
import { ActivityNode, TaskNode, WeightedEdge } from "./model";
import { ActivityNodeSchema, TaskNodeSchema, WeightedEdgeSchema } from "./model-schema";

export class WorkflowModelFactory extends SGraphFactory {
    createElement(schema: SModelElementSchema, parent?: SParentElement): SChildElement {
        if (this.isTaskNodeSchema(schema)) {
            return this.initializeChild(new TaskNode(), schema, parent);
        } else if (this.isWeightedEdgeSchema(schema)) {
            return this.initializeChild(new WeightedEdge(), schema, parent)
        } else if (this.isActivityNodeSchema(schema)) {
            return this.initializeChild(new ActivityNode(), schema, parent)
        } else {
            return super.createElement(schema, parent);
        }
    }

    isTaskNodeSchema(schema: SModelElementSchema): schema is TaskNodeSchema {
        return getBasicType(schema) === 'node' && getSubType(schema) === 'task'
    }

    isActivityNodeSchema(schema: SModelElementSchema): schema is ActivityNodeSchema {
        return getBasicType(schema) === 'node' && getSubType(schema) === 'activity'
    }
    isWeightedEdgeSchema(schema: SModelElementSchema): schema is WeightedEdgeSchema {
        return getBasicType(schema) === 'edge' && getSubType(schema) === 'weighted'
    }
}

