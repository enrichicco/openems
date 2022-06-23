package io.openems.edge.evcs.ocpp.versinetic;

import io.openems.common.channel.Level;
import io.openems.common.channel.Unit;
import io.openems.common.types.OpenemsType;
import io.openems.edge.common.channel.Doc;

public enum VersineticChannelId implements io.openems.edge.common.channel.ChannelId {
	
	CP_ALIAS(Doc.of(OpenemsType.STRING).text("A human-readable name of this Component")),
	CP_RFID_ID(Doc.of(OpenemsType.STRING).text("RFID-ID")),
	CP_METER_VALUE(Doc.of(OpenemsType.INTEGER).unit(Unit.WATT_HOURS).text("Meter value")),
	CP_STATE(Doc.of(OpenemsType.STRING).text("Chargepoint status")),
	
	CP_ENERGY_TOTAL(Doc.of(OpenemsType.INTEGER).unit(Unit.WATT_HOURS).text("Total power consumption")),
	
	CP_CHARGINGSTATION_STATE_ERROR(Doc.of(Level.WARNING));
	
	private final Doc doc;

	private VersineticChannelId(Doc doc) {
		this.doc = doc;
	}

	@Override
	public Doc doc() {
		return this.doc;
	}
}