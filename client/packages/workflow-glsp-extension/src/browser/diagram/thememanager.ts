/*******************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
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