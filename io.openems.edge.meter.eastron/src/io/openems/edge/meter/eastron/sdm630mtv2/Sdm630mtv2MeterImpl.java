package io.openems.edge.meter.eastron.sdm630mtv2;

import java.util.Formatter;
import java.util.Locale;

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

import io.openems.common.channel.AccessMode;
import io.openems.common.exceptions.OpenemsException;
import io.openems.edge.bridge.modbus.api.AbstractOpenemsModbusComponent;
import io.openems.edge.bridge.modbus.api.BridgeModbus;
import io.openems.edge.bridge.modbus.api.ElementToChannelConverter;
import io.openems.edge.bridge.modbus.api.ModbusComponent;
import io.openems.edge.bridge.modbus.api.ModbusProtocol;
import io.openems.edge.bridge.modbus.api.element.DummyRegisterElement;
import io.openems.edge.bridge.modbus.api.element.FloatDoublewordElement;
import io.openems.edge.bridge.modbus.api.element.FloatQuadruplewordElement;
import io.openems.edge.bridge.modbus.api.task.FC3ReadRegistersTask;
import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.channel.FloatReadChannel;
import io.openems.edge.common.channel.LongReadChannel;
import io.openems.edge.common.channel.value.Value;
import io.openems.edge.common.component.OpenemsComponent;
import io.openems.edge.common.modbusslave.ModbusSlave;
import io.openems.edge.common.modbusslave.ModbusSlaveTable;
import io.openems.edge.common.taskmanager.Priority;
import io.openems.edge.meter.api.AsymmetricMeter;
import io.openems.edge.meter.api.MeterType;
import io.openems.edge.meter.api.SymmetricMeter;

@Designate(ocd = Config.class, factory = true)
@Component(//
		name = "io.openems.edge.meter.eastron", //
		immediate = true, //
		configurationPolicy = ConfigurationPolicy.REQUIRE //
)
public class Sdm630mtv2MeterImpl extends AbstractOpenemsModbusComponent 
    implements Sdm630mtv2Meter, SymmetricMeter, AsymmetricMeter, ModbusComponent, OpenemsComponent, ModbusSlave  {

	private MeterType meterType = MeterType.PRODUCTION;

	/*
	 * Invert power values
	 */
	private boolean invert = false;
	private Config config = null;

	public Sdm630mtv2MeterImpl() {
		super(//
				OpenemsComponent.ChannelId.values(), //
				ModbusComponent.ChannelId.values(), //
				SymmetricMeter.ChannelId.values(), //
				AsymmetricMeter.ChannelId.values(), //
				Sdm630mtv2Meter.ChannelId.values() //
		);
	}

	@Reference
	protected ConfigurationAdmin cm;
	
	@Override
	@Reference(policy = ReferencePolicy.STATIC, policyOption = ReferencePolicyOption.GREEDY, cardinality = ReferenceCardinality.MANDATORY)
	protected void setModbus(BridgeModbus modbus) {
		super.setModbus(modbus);
	}

	@Activate
	void activate(ComponentContext context, Config config) throws OpenemsException {
		this.meterType = config.type();
		this.invert = config.invert();

		if (super.activate(context, config.id(), config.alias(), config.enabled(), config.modbusUnitId(), this.cm,
        "Modbus", config.modbus_id())) {
			return;
		}
		
	}

	@Deactivate
	protected void deactivate() {
		super.deactivate();
	}

	@Override
	public MeterType getMeterType() {
		return this.meterType;
	}

	@Override
	protected ModbusProtocol defineModbusProtocol() throws OpenemsException {
		// TODO implement ModbusProtocol
		
		ModbusProtocol modbusProtocol = new ModbusProtocol(this);
		
		modbusProtocol.addTask(
				new FC3ReadRegistersTask(0x2000, Priority.HIGH, //
						
					    m(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL12, new FloatDoublewordElement(0x2000), 
					    		ElementToChannelConverter.SCALE_FACTOR_MINUS_1),
					    m(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL23, new FloatDoublewordElement(0x2002), 
					    		ElementToChannelConverter.SCALE_FACTOR_MINUS_1),
					    m(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL31, new FloatDoublewordElement(0x2004), 
					    		ElementToChannelConverter.SCALE_FACTOR_MINUS_1),
						
					    m(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL1, new FloatDoublewordElement(0x2006), 
					    		ElementToChannelConverter.SCALE_FACTOR_MINUS_1),
					    m(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL2, new FloatDoublewordElement(0x2008), 
					    		ElementToChannelConverter.SCALE_FACTOR_MINUS_1),
					    m(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL3, new FloatDoublewordElement(0x200A), 
					    		ElementToChannelConverter.SCALE_FACTOR_MINUS_1)
						
/*				    m(MeterAlgo2UEM1P5_4DS_E.ChannelId.VOLTAGE_FL1, new FloatDoublewordElement(0x1000), 
			    		ElementToChannelConverter.DIRECT_1_TO_1), //
					m(MeterAlgo2UEM1P5_4DS_E.ChannelId.VOLTAGE_FL2, new FloatDoublewordElement(0x1002),
					    ElementToChannelConverter.DIRECT_1_TO_1),
					m(MeterAlgo2UEM1P5_4DS_E.ChannelId.VOLTAGE_FL3, new FloatDoublewordElement(0x1004),
						    ElementToChannelConverter.DIRECT_1_TO_1),
					m(MeterAlgo2UEM1P5_4DS_E.ChannelId.VOLTAGE_FL12, new FloatDoublewordElement(0x1006),
						    ElementToChannelConverter.DIRECT_1_TO_1),
					m(MeterAlgo2UEM1P5_4DS_E.ChannelId.VOLTAGE_FL23, new FloatDoublewordElement(0x1008),
						    ElementToChannelConverter.DIRECT_1_TO_1),
					m(MeterAlgo2UEM1P5_4DS_E.ChannelId.VOLTAGE_FL31, new FloatDoublewordElement(0x100A),
						    ElementToChannelConverter.DIRECT_1_TO_1),
					m(MeterAlgo2UEM1P5_4DS_E.ChannelId.VOLTAGE_FSYS, new FloatDoublewordElement(0x100C),
						    ElementToChannelConverter.DIRECT_1_TO_1)
*/				)
			);		
		
		
		
		return modbusProtocol;
	}

	@Override
	public String debugLog() {
		
		
		StringBuilder theMessage = new StringBuilder();
		// Send all output to the Appendable object theMessage
		Formatter msgFormatter = new Formatter(theMessage, Locale.US);
		//
		theMessage.append("\n- VOLTAGES VALUEs \n");	
		//
		//
		theMessage.append("\n - V1, V2, V3 - ");
		theMessage.append(this.getFVoltageFL1().asString() + " " + this.getFVoltageFL2().asString() + " " + this.getFVoltageFL3().asString());
		theMessage.append("\n - V12, V23, V31 - ");
		theMessage.append(this.getFVoltageFL12().asString() + " " + this.getFVoltageFL23().asString() + " " + this.getFVoltageFL31().asString());
		
		
		
		theMessage.append("\n");
		msgFormatter.close();
		return theMessage.toString();
		
	}
	
	
	// generic ChannelId get LongReadChannel
	public LongReadChannel getLongGenericChannel(io.openems.edge.common.channel.ChannelId theChannel) {
		return this.channel(theChannel);
	}
	
	// generic ChannelId get FloatReadChannel
	public FloatReadChannel getFloatGenericChannel(io.openems.edge.common.channel.ChannelId theChannel) {
		return this.channel(theChannel);
	}
	
	public Value<Float> getFVoltageFL1() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL1).value();
	}
	
	public Value<Float> getFVoltageFL2() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL2).value();
	}
	
	public Value<Float> getFVoltageFL3() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL3).value();
	}
	
	public Value<Float> getFVoltageFL12() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL12).value();
	}
	
	public Value<Float> getFVoltageFL23() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL23).value();
	}
	
	public Value<Float> getFVoltageFL31() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL31).value();
	}

	public Value<Long> getRunningRegs(){
		return this.getLongGenericChannel(Sdm630mtv2Meter.ChannelId.METAS_COUNTER_REGSET_IN_USE).value();
	}

	
	@Override
	public ModbusSlaveTable getModbusSlaveTable(AccessMode accessMode) {
		return new ModbusSlaveTable(//
				OpenemsComponent.getModbusSlaveNatureTable(accessMode), //
				SymmetricMeter.getModbusSlaveNatureTable(accessMode), //
				AsymmetricMeter.getModbusSlaveNatureTable(accessMode) //
		);

	}
}
