package io.openems.edge.evcs.ocpp.generic;

import org.osgi.service.event.EventHandler;
import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.component.OpenemsComponent;


public interface GenericCharger extends OpenemsComponent, EventHandler {

	public enum ChannelId implements io.openems.edge.common.channel.ChannelId {
		;

		private final Doc doc;

		private ChannelId(Doc doc) {
			this.doc = doc;
		}

		@Override
		public Doc doc() {
			return this.doc;
		}
	}

}
