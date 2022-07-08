package io.openems.edge.evcs.ocpp.serveryp;

import io.openems.common.channel.Level;
import io.openems.common.types.OpenemsType;
import io.openems.edge.common.channel.Doc;

public enum OcppServerChannelId implements io.openems.edge.common.channel.ChannelId {
	
	YP_EVCS_ID(Doc.of(OpenemsType.STRING).text("EVCS-ID")),
	YP_RFID_ID(Doc.of(OpenemsType.STRING).text("RFID-ID")),
	YP_METER_START(Doc.of(OpenemsType.STRING).text("Meter start value")),
	YP_METER_STOP(Doc.of(OpenemsType.STRING).text("Meter stop value")),
	
	YP_CHARGINGSTATION_STATE_ERROR(Doc.of(Level.WARNING));
	
	private final Doc doc;

	private OcppServerChannelId(Doc doc) {
		this.doc = doc;
	}

	@Override
	public Doc doc() {
		return this.doc;
	}

}
