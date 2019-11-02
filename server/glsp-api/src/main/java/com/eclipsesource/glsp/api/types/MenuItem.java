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
package com.eclipsesource.glsp.api.types;

import java.util.Collections;
import java.util.List;

import com.eclipsesource.glsp.api.action.Action;

public class MenuItem extends LabeledAction {

	private String id;
	private String sortString;
	private String group;
	private String parentId;
	private boolean isEnabled;
	private boolean isToggled;
	private List<MenuItem> children;

	public MenuItem(String id, String label, List<Action> actions, boolean isEnabled) {
		this(id, label, actions, isEnabled, null);
	}

	public MenuItem(String id, String label, List<Action> actions, boolean isEnabled, String icon) {
		this(id, label, actions, isEnabled, icon, null);
	}

	public MenuItem(String id, String label, List<Action> actions, boolean isEnabled, String icon, String sortString) {
		this(id, label, actions, icon, sortString, null, null, isEnabled, false, Collections.emptyList());
	}

	public MenuItem(String id, String label, List<MenuItem> children) {
		this(id, label, children, null);
	}

	public MenuItem(String id, String label, List<MenuItem> children, String icon) {
		this(id, label, children, icon, null);
	}

	public MenuItem(String id, String label, List<MenuItem> children, String icon, String group) {
		this(id, label, Collections.emptyList(), icon, group, null, null, true, false, children);
	}

	public MenuItem(String id, String label, List<Action> actions, String icon, String sortString, String group,
			String parentId, boolean isEnabled, boolean isToggled, List<MenuItem> children) {
		super(label, icon, actions);
		this.id = id;
		this.sortString = sortString;
		this.group = group;
		this.parentId = parentId;
		this.isEnabled = isEnabled;
		this.isToggled = isToggled;
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSortString() {
		return sortString;
	}

	public void setSortString(String sortString) {
		this.sortString = sortString;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isToggled() {
		return isToggled;
	}

	public void setToggled(boolean isToggled) {
		this.isToggled = isToggled;
	}

	public List<MenuItem> getChildren() {
		return children;
	}

	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isEnabled ? 1231 : 1237);
		result = prime * result + (isToggled ? 1231 : 1237);
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((sortString == null) ? 0 : sortString.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItem other = (MenuItem) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isEnabled != other.isEnabled)
			return false;
		if (isToggled != other.isToggled)
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (sortString == null) {
			if (other.sortString != null)
				return false;
		} else if (!sortString.equals(other.sortString))
			return false;
		return true;
	}

}
