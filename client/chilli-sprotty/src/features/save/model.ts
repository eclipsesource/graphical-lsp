import { SModelExtension, SModelElement, SModelRoot } from "sprotty/lib";

export const saveFeature = Symbol('saveFeature');

export interface Saveable extends SModelExtension {
    dirty: boolean;
}

export function isSaveable(element: SModelElement):element is SModelRoot & Saveable{
    return element.hasFeature(saveFeature);
}