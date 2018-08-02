import { SGraphFactory, SModelElementSchema, SParentElement, SChildElement, getBasicType, getSubType } from "sprotty/lib";
import { WeightedEdge, TaskNode, ActivityNode } from "./model";
import { TaskNodeSchema, ActivityNodeSchema, WeightedEdgeSchema } from "./model-schema";

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

