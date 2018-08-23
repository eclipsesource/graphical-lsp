/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
package at.tortmayr.glsp.api.tool;

import java.util.ArrayList;

import com.google.common.base.Optional;

import at.tortmayr.glsp.api.action.Action;
import at.tortmayr.glsp.api.action.kind.ExecuteNodeCreationToolAction;
import io.typefox.sprotty.api.Point;
import io.typefox.sprotty.api.SModelElement;
import io.typefox.sprotty.api.SModelIndex;
import io.typefox.sprotty.api.SModelRoot;

public abstract class NodeCreationTool extends ExecutableTool {

	public NodeCreationTool(String id, String name) {
		super(id, name, ToolType.CREATION);
	}

	protected abstract SModelElement createNode(Optional<Point> point);
	
	@Override
	public SModelRoot execute(Action action, SModelRoot modelRoot) {

		if (action instanceof ExecuteNodeCreationToolAction) {
			ExecuteNodeCreationToolAction executeAction = (ExecuteNodeCreationToolAction) action;

			SModelElement container = SModelIndex.find(modelRoot, executeAction.getContainerId());
			if (container == null) {
				container = modelRoot;
			}

			Optional<Point> point = Optional.of(executeAction.getLocation());
			SModelElement element = createNode(point);
			if (container.getChildren() == null) {
				container.setChildren(new ArrayList<SModelElement>());
			}
			container.getChildren().add(element);

		}
		return modelRoot;
	}

}
