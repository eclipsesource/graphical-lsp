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
import { Theme, ThemeService } from "@theia/core/lib/browser/theming";
import { Disposable } from "@theia/languages/lib/browser";
import { injectable } from "inversify";

const darkTheme = require('workflow-sprotty/css/dark/dark.useable.css')
const lightTheme = require('workflow-sprotty/css/light/light.useable.css')

@injectable()
export class ThemeManager implements Disposable {
    private disposable: Disposable;

    initialize() {
        const themeService = ThemeService.get()
        this.switchTheme(undefined, themeService.getCurrentTheme())
        this.disposable = themeService.onThemeChange(event => this.switchTheme(event.oldTheme, event.newTheme))
    }

    private switchTheme(oldTheme: Theme | undefined, newTheme: Theme): void {
        if (oldTheme) {
            if (oldTheme.id === 'dark')
                darkTheme.unuse()
            else
                lightTheme.unuse()
        }
        if (newTheme.id === 'dark')
            darkTheme.use()
        else
            lightTheme.use()
    }

    dispose(): void {
        this.disposable.dispose()
    }
}