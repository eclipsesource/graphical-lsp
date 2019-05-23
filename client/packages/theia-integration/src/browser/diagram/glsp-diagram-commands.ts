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
import {
    AlignElementsAction,
    AlignElementsCommand,
    Alignment,
    Reduce,
    ResizeDimension,
    ResizeElementsAction,
    ResizeElementsCommand,
    Select,
    SelectableBoundsAware
} from "@glsp/sprotty-client/lib";
import { CommandContribution, CommandRegistry, MenuContribution, MenuModelRegistry, MenuPath } from "@theia/core";
import { ApplicationShell, KeybindingContribution, KeybindingRegistry } from "@theia/core/lib/browser";
import { inject, injectable } from "inversify";
import { DiagramCommandHandler, DiagramKeybindingContext, DiagramMenus } from "sprotty-theia";

export namespace GLSPDiagramCommands {
    export const RESIZE_WIDTH_MIN = 'glsp:' + ResizeElementsCommand.KIND + ":width:min";
    export const RESIZE_WIDTH_MAX = 'glsp:' + ResizeElementsCommand.KIND + ":width:max";
    export const RESIZE_WIDTH_AVG = 'glsp:' + ResizeElementsCommand.KIND + ":width:avg";
    export const RESIZE_WIDTH_FIRST = 'glsp:' + ResizeElementsCommand.KIND + ":width:first";
    export const RESIZE_WIDTH_LAST = 'glsp:' + ResizeElementsCommand.KIND + ":width:last";

    export const RESIZE_HEIGHT_MIN = 'glsp:' + ResizeElementsCommand.KIND + ":height:min";
    export const RESIZE_HEIGHT_MAX = 'glsp:' + ResizeElementsCommand.KIND + ":height:max";
    export const RESIZE_HEIGHT_AVG = 'glsp:' + ResizeElementsCommand.KIND + ":height:avg";
    export const RESIZE_HEIGHT_FIRST = 'glsp:' + ResizeElementsCommand.KIND + ":height:first";
    export const RESIZE_HEIGHT_LAST = 'glsp:' + ResizeElementsCommand.KIND + ":height:last";

    export const RESIZE_WIDTH_AND_HEIGHT_MIN = 'glsp:' + ResizeElementsCommand.KIND + ":width_and_height:min";
    export const RESIZE_WIDTH_AND_HEIGHT_MAX = 'glsp:' + ResizeElementsCommand.KIND + ":width_and_height:max";
    export const RESIZE_WIDTH_AND_HEIGHT_AVG = 'glsp:' + ResizeElementsCommand.KIND + ":width_and_height:avg";
    export const RESIZE_WIDTH_AND_HEIGHT_FIRST = 'glsp:' + ResizeElementsCommand.KIND + ":width_and_height:first";
    export const RESIZE_WIDTH_AND_HEIGHT_LAST = 'glsp:' + ResizeElementsCommand.KIND + ":width_and_height:last";

    export const ALIGN_LEFT = 'glsp:' + AlignElementsCommand.KIND + ":left";
    export const ALIGN_CENTER = 'glsp:' + AlignElementsCommand.KIND + ":center";
    export const ALIGN_RIGHT = 'glsp:' + AlignElementsCommand.KIND + ":right";
    export const ALIGN_TOP = 'glsp:' + AlignElementsCommand.KIND + ":top";
    export const ALIGN_MIDDLE = 'glsp:' + AlignElementsCommand.KIND + ":middle";
    export const ALIGN_BOTTOM = 'glsp:' + AlignElementsCommand.KIND + ":bottom";

    export const ALIGN_LEFT_FIRST = 'glsp:' + AlignElementsCommand.KIND + ":left:first";
    export const ALIGN_CENTER_FIRST = 'glsp:' + AlignElementsCommand.KIND + ":center:first";
    export const ALIGN_RIGHT_FIRST = 'glsp:' + AlignElementsCommand.KIND + ":right:first";
    export const ALIGN_TOP_FIRST = 'glsp:' + AlignElementsCommand.KIND + ":top:first";
    export const ALIGN_MIDDLE_FIRST = 'glsp:' + AlignElementsCommand.KIND + ":middle:first";
    export const ALIGN_BOTTOM_FIRST = 'glsp:' + AlignElementsCommand.KIND + ":bottom:first";

    export const ALIGN_LEFT_LAST = 'glsp:' + AlignElementsCommand.KIND + ":left:last";
    export const ALIGN_CENTER_LAST = 'glsp:' + AlignElementsCommand.KIND + ":center:last";
    export const ALIGN_RIGHT_LAST = 'glsp:' + AlignElementsCommand.KIND + ":right:last";
    export const ALIGN_TOP_LAST = 'glsp:' + AlignElementsCommand.KIND + ":top:last";
    export const ALIGN_MIDDLE_LAST = 'glsp:' + AlignElementsCommand.KIND + ":middle:last";
    export const ALIGN_BOTTOM_LAST = 'glsp:' + AlignElementsCommand.KIND + ":bottom:last";
}

export namespace GLSPDiagramMenus {
    export const ALIGN_MENU: MenuPath = DiagramMenus.DIAGRAM.concat("align");
    export const ALIGN_HORIZONTAL_GROUP: MenuPath = ALIGN_MENU.concat("1_horizontal");
    export const ALIGN_VERTICAL_GROUP: MenuPath = ALIGN_MENU.concat("2_vertical");
    export const ALIGN_HORIZONTAL_FIRST_GROUP: MenuPath = ALIGN_MENU.concat("3_horizontal_first");
    export const ALIGN_VERTICAL_FIRST_GROUP: MenuPath = ALIGN_MENU.concat("4_vertical_first");
    export const ALIGN_HORIZONTAL_LAST_GROUP: MenuPath = ALIGN_MENU.concat("5_horizontal_last");
    export const ALIGN_VERTICAL_LAST_GROUP: MenuPath = ALIGN_MENU.concat("6_vertical_last");

    export const RESIZE_MENU: MenuPath = DiagramMenus.DIAGRAM.concat("resize");
    export const RESIZE_WIDTH_GROUP: MenuPath = RESIZE_MENU.concat("1_width");
    export const RESIZE_HEIGHT_GROUP: MenuPath = RESIZE_MENU.concat("2_height");
    export const RESIZE_WIDTH_AND_HEIGHT_GROUP: MenuPath = RESIZE_MENU.concat("3_width_and_height");
}

@injectable()
export class GLSPDiagramMenuContribution implements MenuContribution {

    registerMenus(registry: MenuModelRegistry) {
        registry.registerSubmenu(GLSPDiagramMenus.RESIZE_MENU, "Resize");
        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_WIDTH_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_WIDTH_MIN,
            order: '1'
        });
        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_WIDTH_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_WIDTH_MAX,
            order: '2'
        });
        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_WIDTH_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_WIDTH_AVG,
            order: '3'
        });
        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_WIDTH_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_WIDTH_FIRST,
            order: '4'
        });
        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_WIDTH_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_WIDTH_LAST,
            order: '5'
        });

        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_HEIGHT_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_HEIGHT_MIN,
            order: '1'
        });
        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_HEIGHT_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_HEIGHT_MAX,
            order: '2'
        });
        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_HEIGHT_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_HEIGHT_AVG,
            order: '3'
        });
        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_HEIGHT_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_HEIGHT_FIRST,
            order: '5'
        });
        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_HEIGHT_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_HEIGHT_LAST,
            order: '4'
        });

        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_WIDTH_AND_HEIGHT_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_WIDTH_AND_HEIGHT_MIN,
            order: '1'
        });
        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_WIDTH_AND_HEIGHT_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_WIDTH_AND_HEIGHT_MAX,
            order: '2'
        });
        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_WIDTH_AND_HEIGHT_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_WIDTH_AND_HEIGHT_AVG,
            order: '3'
        });
        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_WIDTH_AND_HEIGHT_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_WIDTH_AND_HEIGHT_FIRST,
            order: '4'
        });
        registry.registerMenuAction(GLSPDiagramMenus.RESIZE_WIDTH_AND_HEIGHT_GROUP, {
            commandId: GLSPDiagramCommands.RESIZE_WIDTH_AND_HEIGHT_LAST,
            order: '5'
        });

        registry.registerSubmenu(GLSPDiagramMenus.ALIGN_MENU, "Align");
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_HORIZONTAL_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_LEFT,
            order: '1'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_HORIZONTAL_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_CENTER,
            order: '2'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_HORIZONTAL_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_RIGHT,
            order: '3'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_VERTICAL_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_TOP,
            order: '4'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_VERTICAL_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_MIDDLE,
            order: '5'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_VERTICAL_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_BOTTOM,
            order: '6'
        });

        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_HORIZONTAL_FIRST_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_LEFT_FIRST,
            order: '1'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_HORIZONTAL_FIRST_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_CENTER_FIRST,
            order: '2'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_HORIZONTAL_FIRST_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_RIGHT_FIRST,
            order: '3'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_VERTICAL_FIRST_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_TOP_FIRST,
            order: '4'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_VERTICAL_FIRST_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_MIDDLE_FIRST,
            order: '5'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_VERTICAL_FIRST_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_BOTTOM_FIRST,
            order: '6'
        });

        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_HORIZONTAL_LAST_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_LEFT_LAST,
            order: '1'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_HORIZONTAL_LAST_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_CENTER_LAST,
            order: '2'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_HORIZONTAL_LAST_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_RIGHT_LAST,
            order: '3'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_VERTICAL_LAST_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_TOP_LAST,
            order: '4'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_VERTICAL_LAST_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_MIDDLE_LAST,
            order: '5'
        });
        registry.registerMenuAction(GLSPDiagramMenus.ALIGN_VERTICAL_LAST_GROUP, {
            commandId: GLSPDiagramCommands.ALIGN_BOTTOM_LAST,
            order: '6'
        });
    }
}


@injectable()
export class GLSPDiagramCommandContribution implements CommandContribution {
    constructor(@inject(ApplicationShell) protected readonly shell: ApplicationShell) {
    }

    registerResize(registry: CommandRegistry, id: string, label: string, dimension: ResizeDimension, f: (...values: number[]) => number) {
        registry.registerCommand({
            id: id,
            label: label,
        });
        registry.registerHandler(id,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new ResizeElementsAction([], dimension, f))
            )
        );
    }

    registerAlign(registry: CommandRegistry, id: string, label: string, alignment: Alignment, f: (elements: SelectableBoundsAware[]) => SelectableBoundsAware[]) {
        registry.registerCommand({
            id: id,
            label: label
        });
        registry.registerHandler(id,
            new DiagramCommandHandler(this.shell, widget =>
                widget.actionDispatcher.dispatch(new AlignElementsAction([], alignment, f))
            )
        );
    }

    registerCommands(reg: CommandRegistry): void {
        this.registerResize(reg, GLSPDiagramCommands.RESIZE_WIDTH_MIN, 'Minimal Width', ResizeDimension.Width, Reduce.min);
        this.registerResize(reg, GLSPDiagramCommands.RESIZE_WIDTH_MAX, 'Maximal Width', ResizeDimension.Width, Reduce.max);
        this.registerResize(reg, GLSPDiagramCommands.RESIZE_WIDTH_AVG, 'Average Width', ResizeDimension.Width, Reduce.avg);
        this.registerResize(reg, GLSPDiagramCommands.RESIZE_WIDTH_FIRST, 'Width of First Selected Element', ResizeDimension.Width, Reduce.first);
        this.registerResize(reg, GLSPDiagramCommands.RESIZE_WIDTH_LAST, 'Width of Last Selected Element', ResizeDimension.Width, Reduce.last);

        this.registerResize(reg, GLSPDiagramCommands.RESIZE_HEIGHT_MIN, 'Minimal Height', ResizeDimension.Height, Reduce.min);
        this.registerResize(reg, GLSPDiagramCommands.RESIZE_HEIGHT_MAX, 'Maximal Height', ResizeDimension.Height, Reduce.max);
        this.registerResize(reg, GLSPDiagramCommands.RESIZE_HEIGHT_AVG, 'Average Height', ResizeDimension.Height, Reduce.avg);
        this.registerResize(reg, GLSPDiagramCommands.RESIZE_HEIGHT_FIRST, 'Height of First Selected Element', ResizeDimension.Height, Reduce.first);
        this.registerResize(reg, GLSPDiagramCommands.RESIZE_HEIGHT_LAST, 'Height of Last Selected Element', ResizeDimension.Height, Reduce.last);

        this.registerResize(reg, GLSPDiagramCommands.RESIZE_WIDTH_AND_HEIGHT_MIN, 'Minimal Width and Height', ResizeDimension.Width_And_Height, Reduce.min);
        this.registerResize(reg, GLSPDiagramCommands.RESIZE_WIDTH_AND_HEIGHT_MAX, 'Maximal Width and Height', ResizeDimension.Width_And_Height, Reduce.max);
        this.registerResize(reg, GLSPDiagramCommands.RESIZE_WIDTH_AND_HEIGHT_AVG, 'Average Width and Height', ResizeDimension.Width_And_Height, Reduce.avg);
        this.registerResize(reg, GLSPDiagramCommands.RESIZE_WIDTH_AND_HEIGHT_FIRST, 'Width and Height of First Selected Element', ResizeDimension.Width_And_Height, Reduce.first);
        this.registerResize(reg, GLSPDiagramCommands.RESIZE_WIDTH_AND_HEIGHT_LAST, 'Width and Height of Last Selected Element', ResizeDimension.Width_And_Height, Reduce.last);


        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_LEFT, 'Left', Alignment.Left, Select.all);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_CENTER, 'Center', Alignment.Center, Select.all);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_RIGHT, 'Right', Alignment.Right, Select.all);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_TOP, 'Top', Alignment.Top, Select.all);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_MIDDLE, 'Middle', Alignment.Middle, Select.all);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_BOTTOM, 'Bottom', Alignment.Bottom, Select.all);

        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_LEFT_FIRST, 'Left of First Selected Element', Alignment.Left, Select.first);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_CENTER_FIRST, 'Center of First Selected Element', Alignment.Center, Select.first);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_RIGHT_FIRST, 'Right of First Selected Element', Alignment.Right, Select.first);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_TOP_FIRST, 'Top of First Selected Element', Alignment.Top, Select.first);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_MIDDLE_FIRST, 'Middle of First Selected Element', Alignment.Middle, Select.first);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_BOTTOM_FIRST, 'Bottom of First Selected Element', Alignment.Bottom, Select.first);

        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_LEFT_LAST, 'Left of Last Selected Element', Alignment.Left, Select.last);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_CENTER_LAST, 'Center of Last Selected Element', Alignment.Center, Select.last);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_RIGHT_LAST, 'Right of Last Selected Element', Alignment.Right, Select.last);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_TOP_LAST, 'Top of Last Selected Element', Alignment.Top, Select.last);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_MIDDLE_LAST, 'Middle of Last Selected Element', Alignment.Middle, Select.last);
        this.registerAlign(reg, GLSPDiagramCommands.ALIGN_BOTTOM_LAST, 'Bottom of Last Selected Element', Alignment.Bottom, Select.last);
    }
}

@injectable()
export class GLSPDiagramKeybindingContribution implements KeybindingContribution {

    constructor(@inject(DiagramKeybindingContext) protected readonly diagramKeybindingContext: DiagramKeybindingContext) { }

    registerKeybindings(registry: KeybindingRegistry): void {
        registry.registerKeybinding({
            command: GLSPDiagramCommands.ALIGN_LEFT,
            context: this.diagramKeybindingContext.id,
            keybinding: 'alt+shift+left'
        });
        registry.registerKeybinding({
            command: GLSPDiagramCommands.ALIGN_RIGHT,
            context: this.diagramKeybindingContext.id,
            keybinding: 'alt+shift+right'
        });
        registry.registerKeybinding({
            command: GLSPDiagramCommands.ALIGN_TOP,
            context: this.diagramKeybindingContext.id,
            keybinding: 'alt+shift+up'
        });
        registry.registerKeybinding({
            command: GLSPDiagramCommands.ALIGN_BOTTOM,
            context: this.diagramKeybindingContext.id,
            keybinding: 'alt+shift+down'
        });
    }
}
