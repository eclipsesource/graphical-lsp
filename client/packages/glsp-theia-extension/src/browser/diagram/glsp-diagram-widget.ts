/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 *  Philip Langer - add support for saveable
 ******************************************************************************/
import { Saveable, SaveableSource } from "@theia/core/lib/browser";
import { Disposable, DisposableCollection, Emitter, Event, MaybePromise } from "@theia/core/lib/common";
import { EditorPreferences } from "@theia/editor/lib/browser";
import { Action, IActionDispatcher, ModelSource, RequestModelAction, RequestOperationsAction, SaveModelAction } from "glsp-sprotty/lib";
import { DiagramWidget, DiagramWidgetOptions } from "theia-glsp/lib";
import { NotifyingModelSource } from "./glsp-theia-diagram-server";

export class GLSPDiagramWidget extends DiagramWidget implements SaveableSource {

    saveable = new SaveableGLSPModelSource(this.actionDispatcher, this.modelSource);

    constructor(options: DiagramWidgetOptions, readonly editorPreferences: EditorPreferences) {
        super(options);
        this.updateSaveable();
        const prefUpdater = editorPreferences.onPreferenceChanged(() => this.updateSaveable());
        this.toDispose.push(prefUpdater);
        this.toDispose.push(this.saveable);
    }

    protected updateSaveable() {
        this.saveable.autoSave = this.editorPreferences['editor.autoSave'];
        this.saveable.autoSaveDelay = this.editorPreferences['editor.autoSaveDelay'];
    }

    protected sendInitialRequestMessages() {
        this.modelSource.handle(new RequestModelAction({
            sourceUri: this.uri.toString(),
            diagramType: this.diagramType,
            needsClientLayout: 'true'
        }))
        this.modelSource.handle(new RequestOperationsAction());
    }

}

export class SaveableGLSPModelSource implements Saveable, Disposable {

    isAutoSave: "on" | "off" = "on";
    autoSaveDelay: number = 500;

    private autoSaveJobs = new DisposableCollection();
    private isDirty: boolean = false;
    readonly dirtyChangedEmitter: Emitter<void> = new Emitter<void>();

    constructor(readonly actionDispatcher: IActionDispatcher, readonly modelSource: ModelSource) {
        if (NotifyingModelSource.is(this.modelSource)) {
            const notifyingModelSource = this.modelSource as NotifyingModelSource;
            notifyingModelSource.onHandledAction((action) => {
                this.dirty = this.dirty || isModelManipulation(action)
            });
        }
    }

    get onDirtyChanged(): Event<void> {
        return this.dirtyChangedEmitter.event;
    }

    save(): MaybePromise<void> {
        return this.actionDispatcher.dispatch(new SaveModelAction())
            .then(() => { this.dirty = false });
    }

    get dirty(): boolean {
        return this.isDirty;
    }

    set dirty(newDirty: boolean) {
        const oldValue = this.isDirty;
        if (oldValue !== newDirty) {
            this.isDirty = newDirty;
            this.dirtyChangedEmitter.fire(undefined);
        }
        this.scheduleAutoSave();
    }

    set autoSave(isAutoSave: "on" | "off") {
        this.isAutoSave = isAutoSave;
        if (this.shouldAutoSave) {
            this.scheduleAutoSave();
        } else {
            this.autoSaveJobs.dispose();
        }
    }

    get autoSave(): "on" | "off" {
        return this.isAutoSave;
    }

    protected scheduleAutoSave() {
        if (this.shouldAutoSave) {
            this.autoSaveJobs.dispose();
            const autoSaveJob = window.setTimeout(() => this.doAutoSave(), this.autoSaveDelay);
            const disposableAutoSaveJob = Disposable.create(() => window.clearTimeout(autoSaveJob));
            this.autoSaveJobs.push(disposableAutoSaveJob);
        }
    }

    protected doAutoSave() {
        if (this.shouldAutoSave) {
            this.save();
        }
    }

    protected get shouldAutoSave(): boolean {
        return this.dirty && this.autoSave === 'on';
    }

    dispose(): void {
        this.autoSaveJobs.dispose();
        this.dirtyChangedEmitter.dispose();
    }

}

function isModelManipulation(action: Action): boolean {
    return action.kind.startsWith("executeOperation") || action.kind === "move";
}