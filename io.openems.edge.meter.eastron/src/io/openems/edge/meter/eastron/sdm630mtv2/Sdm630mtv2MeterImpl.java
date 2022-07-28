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
import io.openems.edge.bridge.modbus.api.element.SignedDoublewordElement;
import io.openems.edge.bridge.modbus.api.task.FC3ReadRegistersTask;
import io.openems.edge.bridge.modbus.api.task.FC4ReadInputRegistersTask;
import io.openems.edge.common.channel.Doc;
import io.openems.edge.common.channel.FloatReadChannel;
import io.openems.edge.common.channel.IntegerReadChannel;
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
			// new FC3ReadRegistersTask(/*0x7531*/ 0, Priority.HIGH, //
			new FC4ReadInputRegistersTask(/*0x7531*/ 0, Priority.HIGH, //
				
				
			    m(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL1, new FloatDoublewordElement(0), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    m(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL2, new FloatDoublewordElement(2), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    m(Sdm630mtv2Meter.ChannelId.VOLTAGE_FL3, new FloatDoublewordElement(4), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
				
			    m(Sdm630mtv2Meter.ChannelId.AMPERE_FL1, new FloatDoublewordElement(6), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    m(Sdm630mtv2Meter.ChannelId.AMPERE_FL2, new FloatDoublewordElement(8), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    m(Sdm630mtv2Meter.ChannelId.AMPERE_FL3, new FloatDoublewordElement(10), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
					
			    m(Sdm630mtv2Meter.ChannelId.POWER_FL1, new FloatDoublewordElement(12), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    m(Sdm630mtv2Meter.ChannelId.POWER_FL2, new FloatDoublewordElement(14), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    m(Sdm630mtv2Meter.ChannelId.POWER_FL3, new FloatDoublewordElement(16), 
			    		ElementToChannelConverter.DIRECT_1_TO_1)
					
			)
		);
		modbusProtocol.addTask(
			new FC3ReadRegistersTask(/* */ 52, Priority.HIGH, //

			    m(Sdm630mtv2Meter.ChannelId.SYSPOW_FTOTAL, new FloatDoublewordElement(52), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    m(Sdm630mtv2Meter.ChannelId.SYSVA_FTOTAL, new FloatDoublewordElement(54), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    m(Sdm630mtv2Meter.ChannelId.SYSVAR_FTOTAL, new FloatDoublewordElement(56), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    m(Sdm630mtv2Meter.ChannelId.SYSPOWFACT_FTOTAL, new FloatDoublewordElement(58), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    m(Sdm630mtv2Meter.ChannelId.SYSPHASEANGLE_FTOTAL, new FloatDoublewordElement(60), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    
			    m(Sdm630mtv2Meter.ChannelId.IMPORTWH_FTOTAL, new FloatDoublewordElement(62), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    m(Sdm630mtv2Meter.ChannelId.EXPORTWH_FTOTAL, new FloatDoublewordElement(64), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    m(Sdm630mtv2Meter.ChannelId.IMPORTVAHREACT_FTOTAL, new FloatDoublewordElement(66), 
			    		ElementToChannelConverter.DIRECT_1_TO_1),
			    m(Sdm630mtv2Meter.ChannelId.EXPORTVAHREACT_FTOTAL, new FloatDoublewordElement(68), 
			    		ElementToChannelConverter.DIRECT_1_TO_1)
			    
						
						
			)
		);
		modbusProtocol.addTask(
			new FC3ReadRegistersTask(/* */ 18, Priority.HIGH, //
				    m(Sdm630mtv2Meter.ChannelId.NETWORK_PARITY_STOP, new FloatDoublewordElement(18), 
				    		ElementToChannelConverter.DIRECT_1_TO_1),
				    m(Sdm630mtv2Meter.ChannelId.NETWORK_NODE, new FloatDoublewordElement(20), 
				    		ElementToChannelConverter.DIRECT_1_TO_1)
					
			)
		);		
		modbusProtocol.addTask(
			new FC3ReadRegistersTask(/* */ 28, Priority.HIGH, //
				    m(Sdm630mtv2Meter.ChannelId.BAUDRATE, new FloatDoublewordElement(28), 
				    		ElementToChannelConverter.DIRECT_1_TO_1)
					
			)
		);		
		modbusProtocol.addTask(
			new FC3ReadRegistersTask(/* */ 42, Priority.HIGH, //
				    m(Sdm630mtv2Meter.ChannelId.SERIAL_NUMBER_HI, new FloatDoublewordElement(42), 
				    		ElementToChannelConverter.DIRECT_1_TO_1),
				    m(Sdm630mtv2Meter.ChannelId.SERIAL_NUMBER_LO, new FloatDoublewordElement(44), 
				    		ElementToChannelConverter.DIRECT_1_TO_1)
					
			)
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
		theMessage.append("\n - A1, A2, A3 - ");
		theMessage.append(this.getFAmpereFL1().asString() + " " + this.getFAmpereFL2().asString() + " " + this.getFAmpereFL3().asString());
		theMessage.append("\n - P1, P2, P3 - ");
		theMessage.append(this.getFPowerFL1().asString() + " " + this.getFPowerFL2().asString() + " " + this.getFPowerFL3().asString());
		
		theMessage.append("\n - Total Imported Energy, Total Exported Energy,  - ");
		theMessage.append(this.getFImportWHTotal().asString() + " " + this.getFExportWHTotal().asString());
		
		theMessage.append("\n - Total Imported VAHR, Total Exported VAHR,  - ");
		theMessage.append(this.getFImportVAHRTotal().asString() + " " + this.getFExportVAHRTotal().asString());

		
		theMessage.append("\n - Network Parity and Node - ");
		theMessage.append(this.getNetParityStop().asString() + " " + this.getNetNode().asString());
		
		theMessage.append("\n - Baud level - ");
		theMessage.append(this.getFBaudRate().asString());
		
		theMessage.append("\n - serial number low and high - ");
		theMessage.append(this.getSerNumbLo().asString() + " " + this.getSerNumbHi());
		
		theMessage.append("\n");
		msgFormatter.close();
		return theMessage.toString();
		
	}
	
	
	// generic ChannelId get LongReadChannel
	public IntegerReadChannel getIntGenericChannel(io.openems.edge.common.channel.ChannelId theChannel) {
		return this.channel(theChannel);
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
	
	public Value<Float> getFAmpereFL1() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.AMPERE_FL1).value();
	}
	
	public Value<Float> getFAmpereFL2() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.AMPERE_FL2).value();
	}
	
	public Value<Float> getFAmpereFL3() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.AMPERE_FL3).value();
	}

	public Value<Float> getFPowerFL1() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.POWER_FL1).value();
	}
	
	public Value<Float> getFPowerFL2() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.POWER_FL2).value();
	}
	
	public Value<Float> getFPowerFL3() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.POWER_FL3).value();
	}

	
	
	
	public Value<Float> getFSystemPowerTotal() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.SYSPOW_FTOTAL).value();
	}

	public Value<Float> getFSystemVATotal() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.SYSVA_FTOTAL).value();
	}

	public Value<Float> getFSystemVARTotal() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.SYSVAR_FTOTAL).value();
	}

	public Value<Float> getFSystemPowFactTotal() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.SYSPOWFACT_FTOTAL).value();
	}
	
	public Value<Float> getFSystemPhaseAngleTotal() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.SYSPHASEANGLE_FTOTAL).value();
	}
	
	
	
	public Value<Float> getFVoltFrequency() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.VOLTFREQ_F).value();
	}

	

	public Value<Float> getFImportWHTotal() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.IMPORTWH_FTOTAL).value();
	}
	
	public Value<Float> getFExportWHTotal() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.EXPORTWH_FTOTAL).value();
	}
	
	public Value<Float> getFImportVAHRTotal() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.IMPORTVAHREACT_FTOTAL).value();
	}
	
	public Value<Float> getFExportVAHRTotal() {
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.EXPORTVAHREACT_FTOTAL).value();
	}
	
	
	
	public Value<Float> getNetParityStop(){
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.NETWORK_PARITY_STOP).value();
	}
	public Value<Float> getNetNode(){
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.NETWORK_NODE).value();
	}


	public Value<Float> getFBaudRate(){
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.BAUDRATE).value();
	}
	
	
	public Value<Float> getSerNumbHi(){
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.SERIAL_NUMBER_HI).value();
	}
	public Value<Float> getSerNumbLo(){
		return this.getFloatGenericChannel(Sdm630mtv2Meter.ChannelId.SERIAL_NUMBER_LO).value();
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
