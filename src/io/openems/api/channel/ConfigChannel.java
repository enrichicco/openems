/*******************************************************************************
 * OpenEMS - Open Source Energy Management System
 * Copyright (c) 2016 FENECON GmbH and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * Contributors:
 *   FENECON GmbH - initial API and implementation and initial documentation
 *******************************************************************************/
package io.openems.api.channel;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import io.openems.api.exception.NotImplementedException;
import io.openems.api.thing.Thing;
import io.openems.core.utilities.JsonUtils;

public class ConfigChannel<T> extends WriteChannel<T> {
	private final Class<?> type;
	private boolean isOptional;

	public ConfigChannel(String id, Thing parent, Class<?> type) {
		super(id, parent);
		this.type = type;
	}

	@Override public ConfigChannel<T> updateListener(ChannelUpdateListener... listeners) {
		return (ConfigChannel<T>) super.updateListener(listeners);
	}

	@Override public ConfigChannel<T> changeListener(ChannelChangeListener... listeners) {
		return (ConfigChannel<T>) super.changeListener(listeners);
	}

	public Class<?> type() {
		return this.type;
	}

	@Override public void updateValue(Object value, boolean triggerEvent) {
		super.updateValue((T) value, triggerEvent);
	}

	public void updateValue(JsonElement jValue, boolean triggerEvent) throws NotImplementedException {
		T value = (T) JsonUtils.getAsType(jValue, type);
		this.updateValue(value, triggerEvent);
	}

	public ConfigChannel<T> defaultValue(T value) {
		updateValue(value, false);
		return this;
	}

	public ConfigChannel<T> optional() {
		this.isOptional = true;
		return this;
	}

	public boolean isOptional() {
		return isOptional;
	}

	@Override public JsonObject toJsonObject() throws NotImplementedException {
		JsonObject j = super.toJsonObject();
		j.addProperty("writeable", true);
		return j;
	}
}
