package io.openems.edge.core.host;

import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonObject;

import io.openems.common.utils.JsonUtils;

public class NetworkConfiguration {

	public static final String PATTERN_INET4ADDRESS = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";

	private final Map<String, NetworkInterface<?>> interfaces;

	public NetworkConfiguration(Map<String, NetworkInterface<?>> interfaces) {
		this.interfaces = interfaces;
	}

	/**
	 * Return this NetworkConfiguration as a JSON object.
	 * 
	 * <p>
	 * 
	 * <pre>
	 * {
	 *   "interfaces": {
	 *     [name: string]: {
	 *       "dhcp"?: boolean,
	 *       "linkLocalAddressing"?: boolean,
	 *       "gateway"?: string,
	 *       "dns"?: string,
	 *       "addresses"?: string[]
	 *     }
	 *   }
	 * }
	 * </pre>
	 * 
	 * @return this configuration as JSON
	 */
	public JsonObject toJson() {
		JsonObject interfaces = new JsonObject();
		for (Entry<String, NetworkInterface<?>> entry : this.interfaces.entrySet()) {
			interfaces.add(entry.getKey(), entry.getValue().toJson());
		}
		return JsonUtils.buildJsonObject() //
				.add("interfaces", interfaces) //
				.build();
	}

	public Map<String, NetworkInterface<?>> getInterfaces() {
		return this.interfaces;
	}

}
