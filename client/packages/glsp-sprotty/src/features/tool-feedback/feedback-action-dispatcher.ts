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
import { inject, injectable } from "inversify";
import { Action, IActionDispatcher, ILogger, TYPES } from "sprotty/lib";

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

    /**
    * Retrieve all currently registered `actions`
    */
    getRegisteredFeedback(): Action[]

    /**
     * Retrieve all currently registered `feedbackEmitter` processing `action`
     * @param action the action for which processing feedback emitter are retrieved
     */
    getRegisteredFeedbackEmitters(action: Action): IFeedbackEmitter[]
}

@injectable()
export class FeedbackActionDispatcher implements IFeedbackActionDispatcher {
    protected feedbackEmitters: Map<IFeedbackEmitter, Action[]> = new Map;

    constructor(
        @inject(TYPES.IActionDispatcherProvider) protected actionDispatcher: () => Promise<IActionDispatcher>,
        @inject(TYPES.ILogger) protected logger: ILogger) {
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

    getRegisteredFeedback() {
        const result: Action[] = [];
        this.feedbackEmitters.forEach((value, key) => result.push(...value));
        return result;
    }

    getRegisteredFeedbackEmitters(action: Action) {
        const result: IFeedbackEmitter[] = [];
        this.feedbackEmitters.forEach((value, key) => {
            if (value.find(a => a === action)) {
                result.push(key);
            }
        }
        );
        return result;
    }

}
