
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

export function contains<T>(array: T[], value: T): boolean {
    return array.indexOf(value) >= 0
}

export function remove<T>(array: T[], value: T): boolean {
    const index = array.indexOf(value)
    if (index >= 0) {
        array.splice(index, 1)
        return true
    }
    return false
}

export function distinctAdd<T>(array: T[], value: T): boolean {
    if (!contains(array, value)) {
        array.push(value)
        return true
    }
    return false
}


