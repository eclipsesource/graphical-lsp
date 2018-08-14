import { SGraph } from "sprotty/lib";
import { Saveable, saveFeature } from "../features/save/model";


export class GLSPGraph extends SGraph implements Saveable{
    dirty: boolean;

    hasFeature(feature: symbol) {
        return feature === saveFeature || super.hasFeature(feature);
    }
}