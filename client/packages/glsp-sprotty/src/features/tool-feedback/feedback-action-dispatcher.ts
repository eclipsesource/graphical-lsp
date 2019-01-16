/*******************************************************************************
 * Copyright (c) 2019 EclipseSource
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *  Philip Langer - initial API and implementation
 ******************************************************************************/
import { inject, injectable } from "inversify";
import { Action, IActionDispatcher, ILogger, SModelRoot, TYPES } from "sprotty/lib";
import { IModelUpdateNotifier, IModelUpdateObserver } from "../../base/command-stack";
import { GLSP_TYPES } from "../../types";

export interface IFeedbackEmitter { }

/**
 * Action dispatcher for actions that are meant to show tool feedback.
 *
 * The purpose of this action dispatcher is to re-establish the feedback
 * after the model has been updated or reset by the server, as this would
 * overwrite the already established feedback, in case it is drawn by
 * extending the `SModelRoot`. Therefore, tools can register themselves
 * as feedback emitters with actions they want to place for showing
 * feedback. This dispatcher will then re-establish all feedback actions
 * of the registered tools, whenever the `SModelRoot` has been set.
 */
export interface IFeedbackActionDispatcher {
    /**
     * Registers `actions` to be sent out by a `feedbackEmitter`.
     * @param feedbackEmitter the emitter sending out feedback actions.
     * @param actions the actions to be sent out.
     */
    registerFeedback(feedbackEmitter: IFeedbackEmitter, actions: Action[]): void;
    /**
     * Deregisters a `feedbackEmitter` from this dispatcher.
     * @param feedbackEmitter the emitter sending out feedback actions.
     * @param actions the actions to be sent out after deregistration.
     */
    deregisterFeedback(feedbackEmitter: IFeedbackEmitter, actions: Action[]): void;
}

@injectable()
export class FeedbackActionDispatcher implements IFeedbackActionDispatcher, IModelUpdateObserver {

    protected feedbackEmitters: Map<IFeedbackEmitter, Action[]> = new Map;

    constructor(
        @inject(GLSP_TYPES.IModelUpdateNotifier) protected modelUpdateNotifier: IModelUpdateNotifier,
        @inject(TYPES.IActionDispatcherProvider) protected actionDispatcher: () => Promise<IActionDispatcher>,
        @inject(TYPES.ILogger) protected logger: ILogger) {
        this.modelUpdateNotifier.registerObserver(this);
    }

    registerFeedback(feedbackEmitter: IFeedbackEmitter, actions: Action[]): void {
        this.feedbackEmitters.set(feedbackEmitter, actions);
        this.dispatch(actions, feedbackEmitter);
    }

    deregisterFeedback(feedbackEmitter: IFeedbackEmitter, actions: Action[]): void {
        this.feedbackEmitters.delete(feedbackEmitter);
        this.dispatch(actions, feedbackEmitter);
    }

    private dispatch(actions: Action[], feedbackEmitter: IFeedbackEmitter) {
        this.actionDispatcher()
            .then(dispatcher => dispatcher.dispatchAll(actions))
            .then(() => this.logger.info(this, `Dispatched feedback actions for ${feedbackEmitter}`))
            .catch(reason => this.logger.error(this, 'Failed to dispatch feedback actions', reason));
    }

    beforeServerUpdate(model: SModelRoot): void {
        this.feedbackEmitters.forEach((actions, feedbackEmitter) =>
            this.dispatch(actions, feedbackEmitter));
    }

}