package io.openems.edge.meter.chint;

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
import io.openems.edge.bridge.modbus.api.ModbusProtocol;
import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.component.OpenemsComponent;

public interface Chint6XXMeter extends OpenemsComponent {

	public enum ChannelId implements io.openems.edge.common.channel.ChannelId {

		VOLTAGE_FL12(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),
		VOLTAGE_FL23(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),
		VOLTAGE_FL31(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),
		
		VOLTAGE_FL1(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),
		VOLTAGE_FL2(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),
		VOLTAGE_FL3(Doc.of(OpenemsType.FLOAT) //
				.unit(Unit.VOLT) //
				.persistencePriority(PersistencePriority.HIGH)),
		
		METAS_COUNTER_REGSET_IN_USE(Doc.of(OpenemsType.LONG) //
				.unit(Unit.NONE) //
				.persistencePriority(PersistencePriority.HIGH))
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
