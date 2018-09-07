/*
 * Copyright (C) 2017 TypeFox and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License") you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Modifications: Copyright (C) 2018 Tobias Ortmayr<tormayr@eclipsesource.com>
 */



import { RequestModelAction, CenterAction, InitializeCanvasBoundsAction, ModelSource, ServerStatusAction, IActionDispatcher } from 'glsp-sprotty/lib';
import { Widget } from "@phosphor/widgets"
import { Message } from "@phosphor/messaging/lib"
import URI from "@theia/core/lib/common/uri"
import { BaseWidget } from '@theia/core/lib/browser/widgets/widget'

export interface DiagramWidgetOptions {
    id: string
    svgContainerId: string
    uri: URI
    diagramType: string
    modelSource: ModelSource
    actionDispatcher: IActionDispatcher
}

export type DiagramWidgetFactory = (options: DiagramWidgetOptions) => DiagramWidget
export const DiagramWidgetFactory = Symbol('DiagramWidgetFactory')

export class DiagramWidget extends BaseWidget {

    private statusIconDiv: HTMLDivElement
    private statusMessageDiv: HTMLDivElement

    public readonly id: string
    public readonly svgContainerId: string
    public readonly uri: URI
    public readonly diagramType: string
    public readonly modelSource: ModelSource
    public readonly actionDispatcher: IActionDispatcher

    constructor(options: DiagramWidgetOptions) {
        super()
        this.id = options.id
        this.svgContainerId = options.svgContainerId
        this.uri = options.uri
        this.diagramType = options.diagramType
        this.modelSource = options.modelSource
        this.actionDispatcher = options.actionDispatcher
    }

    protected onAfterAttach(msg: Message): void {
        super.onAfterAttach(msg)
        const svgContainer = document.createElement("div")
        svgContainer.id = this.svgContainerId
        this.node.appendChild(svgContainer)

        const statusDiv = document.createElement("div")
        statusDiv.setAttribute('class', 'sprotty-status')
        this.node.appendChild(statusDiv)

        this.statusIconDiv = document.createElement("div")
        this.statusIconDiv.setAttribute('class', 'fa')
        statusDiv.appendChild(this.statusIconDiv)

        this.statusMessageDiv = document.createElement("div")
        this.statusMessageDiv.setAttribute('class', 'sprotty-status-message')
        statusDiv.appendChild(this.statusMessageDiv)
        this.sendInitialRequestMessages()

    }

    protected sendInitialRequestMessages() {
        this.modelSource.handle(new RequestModelAction({
            sourceUri: this.uri.toString(),
            diagramType: this.diagramType,
        }))
    }
    protected getBoundsInPage(element: Element) {
        const bounds = element.getBoundingClientRect()
        return {
            x: bounds.left,
            y: bounds.top,
            width: bounds.width,
            height: bounds.height
        }
    }

    protected onResize(msg: Widget.ResizeMessage): void {
        super.onResize(msg)
        const newBounds = this.getBoundsInPage(this.node as Element)
        this.actionDispatcher.dispatch(new InitializeCanvasBoundsAction(newBounds))
        this.actionDispatcher.dispatch(new CenterAction([], false))
    }

    protected onActivateRequest(msg: Message): void {
        super.onActivateRequest(msg)
        const svgElement = this.node.querySelector(`#${this.svgContainerId} svg`) as HTMLElement
        if (svgElement !== null)
            svgElement.focus()
    }

    setStatus(status: ServerStatusAction): void {
        this.statusMessageDiv.textContent = status.message
        this.removeClasses(this.statusMessageDiv, 1)
        this.statusMessageDiv.classList.add(status.severity.toLowerCase())
        this.removeClasses(this.statusIconDiv, 1)
        const classes = this.statusIconDiv.classList
        classes.add(status.severity.toLowerCase())
        switch (status.severity) {
            case 'ERROR': classes.add('fa-exclamation-circle')
                break
            case 'WARNING': classes.add('fa-warning')
                break
            case 'INFO': classes.add('fa-info-circle')
                break
        }
    }

    protected removeClasses(element: Element, keep: number) {
        const classes = element.classList
        while (classes.length > keep) {
            const item = classes.item(classes.length - 1)
            if (item)
                classes.remove(item)
        }
    }
}