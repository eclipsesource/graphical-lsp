import { SNodeSchema, SEdgeSchema } from "sprotty/lib";

export namespace ActivityNodeSchema {
    export namespace Type {
        export const INITIAL = 'initalNode'
        export const FINAL = 'finalNode'
        export const DECISION = 'decisionNode'
        export const MERGE = 'mergeNode'
        export const JOIN = 'joinNode'
        export const FORK = 'forkNode'
        export const UNDEFINED = "undefined"
    }
}
export interface TaskNodeSchema extends SNodeSchema {
    name?: string
    duration?: number
    taskType?: string
    reference?: string
}

export interface WeightedEdgeSchema extends SEdgeSchema {
    probability?: string
}

export interface ActivityNodeSchema extends SNodeSchema {
    nodeType: string
}


