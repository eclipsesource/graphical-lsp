/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.server.command;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.change.ChangeDescription;

import com.eclipsesource.glsp.graph.GModelRoot;

public class GModelRecordingCommand extends AbstractCommand {

	private GModelRoot modelRoot;
	private Runnable runnable;
	private ChangeDescription change;

	public GModelRecordingCommand(GModelRoot root, String label, Runnable runnable) {
		super(label);
		this.modelRoot = root;
		this.runnable = runnable;
	}

	protected boolean prepare() {
		return change == null;
	}

	@Override
	public void execute() {
		GModelChangeRecorder recorder = new GModelChangeRecorder(modelRoot).beginRecording();
		try {
			runnable.run();
		} finally {
			change = recorder.endRecording();
			recorder.dispose();
			runnable = null;
		}
	}

	@Override
	public boolean canUndo() {
		return change != null;
	}

	@Override
	public void undo() {
		applyChanges();
	}

	@Override
	public void redo() {
		applyChanges();
	}

	private void applyChanges() {
		GModelChangeRecorder recorder = new GModelChangeRecorder(modelRoot).beginRecording();
		try {
			change.apply();
		} finally {
			change = recorder.endRecording();
			recorder.dispose();
		}
	}

}
