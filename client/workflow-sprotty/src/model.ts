import { RectangularNode, SEdge, LayoutContainer, SShapeElement, Bounds, boundsFeature, layoutContainerFeature, layoutableChildFeature, fadeFeature, Expandable, expandFeature } from "sprotty/lib";
import { ActivityNodeSchema } from "./model-schema";

export class TaskNode extends RectangularNode implements Expandable {
    expanded: boolean 
    name: string = ""
    duration?: number;
    taskType?: string;
    reference?: string;


    hasFeature(feature: symbol) {
        return feature === expandFeature || super.hasFeature(feature);
    }

}

export class WeightedEdge extends SEdge {
    probability?: string
}

export class ActivityNode extends RectangularNode {
    nodeType: string = ActivityNodeSchema.Type.UNDEFINED
    size = {
        width: 16,
        height: 16
    };

}
export class Icon extends SShapeElement implements LayoutContainer {
    layout: string 
    layoutOptions?: { [key: string]: string | number | boolean; };
    bounds:Bounds
    size = {
        width: 32,
        height: 32
    };

    hasFeature(feature: symbol): boolean {
        return feature === boundsFeature || feature === layoutContainerFeature || feature === layoutableChildFeature || feature === fadeFeature;
    }
}
