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

import { Action } from "sprotty/lib";
import { Command } from "sprotty/lib";
import { CommandExecutionContext } from "sprotty/lib";
import { CommandResult } from "sprotty/lib";
import { Marker } from "../../utils/marker";
import { MarkerKind } from "../../utils/marker";
import { SIssue } from "sprotty/lib";
import { SIssueMarker } from "sprotty/lib";
import { SModelElement } from "sprotty/lib";
import { SParentElement } from "sprotty/lib";
import { TYPES } from "sprotty/lib";

import { inject } from "inversify";
import { injectable } from "inversify";

/**
 * Action to retrieve markers for a model
 */
export class RequestMarkersAction implements Action {

    static readonly KIND = 'requestMarkers';
    readonly kind = RequestMarkersAction.KIND;

    constructor(public readonly elementsIDs: string[] = []) { }
}

/**
 * Action to set markers for a model
 */
export class SetMarkersAction implements Action {
    readonly kind = SetMarkersCommand.KIND;
    constructor(public readonly markers: Marker[]) { }
}

/**
 * Command for handling SetMarkersActions
 */
@injectable()
export class SetMarkersCommand extends Command {

    static readonly KIND = 'setMarkers';

    markers: Marker[];

    constructor(@inject(TYPES.Action) public action: SetMarkersAction) {
        super();
    }

    /**
     * Creates SIssueMarkers for all received markers and adds them to the respective SModelElements
     * @param context Context of the command execution
     */
    execute(context: CommandExecutionContext): CommandResult {
        this.markers = this.action.markers;
        for (let marker of this.markers) {
            const modelElement: SModelElement | undefined = context.root.index.getById(marker.elementId);
            if (modelElement instanceof SParentElement) {
                const issueMarker: SIssueMarker = this.getOrCreateSIssueMarker(modelElement);
                const issue: SIssue = this.createSIssue(marker);
                issueMarker.issues.push(issue);
            }
        }
        return context.root;
    }

    private getOrCreateSIssueMarker(modelElement: SParentElement): SIssueMarker {
        let issueMarker: SIssueMarker | undefined;

        for (const child of modelElement.children) {
            if (child instanceof SIssueMarker) {
                issueMarker = child;
            }
        }

        if (issueMarker === undefined) {
            issueMarker = new SIssueMarker();
            issueMarker.type = "marker";
            issueMarker.issues = new Array<SIssue>();
            modelElement.add(issueMarker);
        }

        return issueMarker;
    }

    private createSIssue(marker: Marker): SIssue {
        const issue: SIssue = new SIssue();
        issue.message = marker.description;

        switch (marker.kind) {
            case MarkerKind.ERROR: {
                issue.severity = 'error';
                break;
            }
            case MarkerKind.INFO: {
                issue.severity = 'info';
                break;
            }
            case MarkerKind.WARNING: {
                issue.severity = 'warning';
                break;
            }
        }

        return issue;
    }

    undo(context: CommandExecutionContext): CommandResult {
        this.markers = this.action.markers;
        for (let marker of this.markers) {
            const modelElement: SModelElement | undefined = context.root.index.getById(marker.elementId);
            if (modelElement instanceof SParentElement) {
                for (let child of modelElement.children) {
                    if (child instanceof SIssueMarker) {
                        for (let index = 0; index < child.issues.length; ++index) {
                            const issue = child.issues[index];
                            if (issue.message === marker.description) {
                                child.issues.splice(index--, 1);
                            }
                        }
                        if (child.issues.length === 0) {
                            modelElement.remove(child);
                        }
                    }
                }
            }
        }
        return context.root;
    }

    redo(context: CommandExecutionContext): CommandResult {
        return this.execute(context);
    }

    getMarkers(): Marker[] {
        return this.markers;
    }

}
