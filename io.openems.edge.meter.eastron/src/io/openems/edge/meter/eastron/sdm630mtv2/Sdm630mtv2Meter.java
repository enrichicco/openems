package io.openems.edge.meter.eastron.sdm630mtv2;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.metatype.annotations.Designate;

import io.openems.common.channel.PersistencePriority;
import io.openems.common.channel.Unit;
import io.openems.common.exceptions.OpenemsException;
import io.openems.common.types.OpenemsType;
import io.openems.edge.bridge.modbus.api.AbstractOpenemsModbusComponent;
import io.openems.edge.bridge.modbus.api.BridgeModbus;
import io.openems.edge.bridge.modbus.api.ElementToChannelConverter;
import io.openems.edge.bridge.modbus.api.ModbusProtocol;
import io.openems.edge.bridge.modbus.api.element.FloatDoublewordElement;
import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.component.OpenemsComponent;

public interface Sdm630mtv2Meter extends OpenemsComponent {

	public enum ChannelId implements io.openems.edge.common.channel.ChannelId {

		VOLTAGE_FL1(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),
		VOLTAGE_FL2(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),
		VOLTAGE_FL3(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),

		AMPERE_FL1(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.AMPERE) //
				.persistencePriority(PersistencePriority.HIGH)),
		AMPERE_FL2(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.AMPERE) //
				.persistencePriority(PersistencePriority.HIGH)),
		AMPERE_FL3(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.AMPERE) //
				.persistencePriority(PersistencePriority.HIGH)),

		POWER_FL1(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),
		POWER_FL2(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),
		POWER_FL3(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),


		SYSPOW_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.WATT) //
				.persistencePriority(PersistencePriority.HIGH)),
		SYSVA_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT_AMPERE) //
				.persistencePriority(PersistencePriority.HIGH)),
		SYSVAR_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT_AMPERE_REACTIVE) //
				.persistencePriority(PersistencePriority.HIGH)),
		SYSPOWFACT_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.WATT) //
				.persistencePriority(PersistencePriority.HIGH)),
		SYSPHASEANGLE_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.NONE) //
				.persistencePriority(PersistencePriority.HIGH)),

		VOLTFREQ_F(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.HERTZ) //
				.persistencePriority(PersistencePriority.HIGH)),

		IMPORTWH_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.WATT_HOURS) //
				.persistencePriority(PersistencePriority.HIGH)),
		EXPORTWH_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.WATT_HOURS) //
				.persistencePriority(PersistencePriority.HIGH)),
		IMPORTVAHREACT_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT_AMPERE_REACTIVE_HOURS) //
				.persistencePriority(PersistencePriority.HIGH)),
		EXPORTVAHREACT_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT_AMPERE_REACTIVE_HOURS) //
				.persistencePriority(PersistencePriority.HIGH)),

		SYSVAH_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT_AMPERE_HOURS) //
				.persistencePriority(PersistencePriority.HIGH)),
		SYSAH_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT_AMPERE) //
				.persistencePriority(PersistencePriority.HIGH)),

		SYSPOWDEMAND_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.WATT) //
				.persistencePriority(PersistencePriority.HIGH)),
		SYSPOWDEMAND_FMAX(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.WATT) //
				.persistencePriority(PersistencePriority.HIGH)),

		SYSVADEMAND_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT_AMPERE_HOURS) //
				.persistencePriority(PersistencePriority.HIGH)),
		SYSVADEMAND_FMAX(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT_AMPERE) //
				.persistencePriority(PersistencePriority.HIGH)),

		SYSNCDEMAND_FTOTAL(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT_AMPERE_HOURS) //
				.persistencePriority(PersistencePriority.HIGH)),
		SYSNCDEMAND_FMAX(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT_AMPERE) //
				.persistencePriority(PersistencePriority.HIGH)),

		VOLTAGE_FL12(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),
		VOLTAGE_FL23(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),
		VOLTAGE_FL31(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),
		
		
		
		
		
		
		
		
		

		NETWORK_PARITY_STOP(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.NONE) //
				.persistencePriority(PersistencePriority.HIGH)),
		NETWORK_NODE(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.NONE) //
				.persistencePriority(PersistencePriority.HIGH)),

		BAUDRATE(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.NONE) //
				.persistencePriority(PersistencePriority.HIGH)),

		SERIAL_NUMBER_HI(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.NONE) //
				.persistencePriority(PersistencePriority.HIGH)),
		SERIAL_NUMBER_LO(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.NONE) //
				.persistencePriority(PersistencePriority.HIGH));

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
