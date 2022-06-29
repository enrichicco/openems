package io.openems.edge.evcs.ocpp.versinetic;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.osgi.service.event.propertytypes.EventTopics;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.openems.edge.common.channel.Doc;
import eu.chargetime.ocpp.Session;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.core.ChangeConfigurationRequest;
import eu.chargetime.ocpp.model.core.DataTransferRequest;
import eu.chargetime.ocpp.model.core.MeterValuesRequest;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageRequest;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageRequestType;
import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.common.component.AbstractOpenemsComponent;
import io.openems.edge.common.component.OpenemsComponent;
import io.openems.edge.common.event.EdgeEventConstants;
import io.openems.edge.evcs.api.ChargingType;
import io.openems.edge.evcs.api.Evcs;
import io.openems.edge.evcs.api.EvcsPower;
import io.openems.edge.evcs.api.ManagedEvcs;
import io.openems.edge.evcs.api.MeasuringEvcs;

import io.openems.edge.evcs.ocpp.versinetic.VersineticChannelId;

import io.openems.edge.evcs.ocpp.common.AbstractOcppEvcsComponent;
import io.openems.edge.evcs.ocpp.common.ChargingProperty;
import io.openems.edge.evcs.ocpp.common.OcppInformations;
import io.openems.edge.evcs.ocpp.common.OcppProfileType;
import io.openems.edge.evcs.ocpp.common.OcppStandardRequests;

import io.openems.edge.common.channel.Channel;
import io.openems.edge.common.channel.WriteChannel;



@Designate(ocd = Config.class, factory = true)
@Component(//
		name = "Evcs.Ocpp.Versinetic", //
		immediate = true, //
		configurationPolicy = ConfigurationPolicy.REQUIRE //
		
)
@EventTopics({ //
		EdgeEventConstants.TOPIC_CYCLE_EXECUTE_WRITE, //
		EdgeEventConstants.TOPIC_CYCLE_AFTER_PROCESS_IMAGE //
})
public class VersineticChargerImpl extends AbstractOcppEvcsComponent 
		implements Evcs, MeasuringEvcs, ManagedEvcs, OpenemsComponent, EventHandler {

	private final Logger log = LoggerFactory.getLogger(VersineticChargerImpl.class);
	
	// Default value for the hardware limit
	private static final Integer DEFAULT_HARDWARE_LIMIT = 22080;

	// Profiles that a Versinetic is supporting
	private static final OcppProfileType[] PROFILE_TYPES = { //
			OcppProfileType.CORE //
	};

	/*
	 * Values that a Versinetic is supporting Info: It is not sure that the Versinetic is using
	 * all of them, but in particular it is not supporting the information of the
	 * current power.
	 */
	private static final HashSet<OcppInformations> MEASUREMENTS = new HashSet<>( //
			Arrays.asList( //
					OcppInformations.values()) //
	);

	private Config config;

	@Reference
	private EvcsPower evcsPower;

	@Reference
	protected ComponentManager componentManager;


	public VersineticChargerImpl() {
		super(//
				PROFILE_TYPES, //
				OpenemsComponent.ChannelId.values(), //
				Evcs.ChannelId.values(), //
				AbstractOcppEvcsComponent.ChannelId.values(), //
				ManagedEvcs.ChannelId.values(), // 
				MeasuringEvcs.ChannelId.values(), //
				VersineticChannelId.values() //
		);
	}

	@Activate
	public void activate(ComponentContext context, Config config) {
		this.config = config;
		super.activate(context, config.id(), config.alias(), config.enabled());

		this._setChargingType(ChargingType.AC);
		this._setPowerPrecision(230);
	}

	@Override
	public Set<OcppInformations> getSupportedMeasurements() {
		return MEASUREMENTS;
	}

	@Override
	public String getConfiguredOcppId() {
		return this.config.ocpp_id();
	}

	@Override
	public Integer getConfiguredConnectorId() {
		return this.config.connectorId();
	}

	@Override
	public Integer getConfiguredMaximumHardwarePower() {
		// TODO: Set dynamically. Problem: No phases calculation possible.
		return (int) (this.config.maxHwCurrent() / 1000.0) * 230 * 3;
	}

	@Override
	public Integer getConfiguredMinimumHardwarePower() {
		// TODO: Set dynamically. Problem: No phases calculation possible.
		return (int) (this.config.minHwCurrent() / 1000.0) * 230 * 3;
	}

	@Override
	public void handleEvent(Event event) {
		if (!this.isEnabled()) {
			return;
		}
		super.handleEvent(event);
		switch (event.getTopic()) {
		  case EdgeEventConstants.TOPIC_CYCLE_BEFORE_PROCESS_IMAGE:
			 // TODO: fill channels (dalla libreria originale)
			  
			 break;
		  
		  case EdgeEventConstants.TOPIC_CYCLE_AFTER_PROCESS_IMAGE:
			// Ciclo standard, non previsto dalla libreria clonata
			  
			AbstractOcppEvcsComponent evcs = this;
			
			var oc_ocpp_session = evcs.getSessionId();
			var oc_ocpp_id = getConfiguredOcppId();
			var oc_status = evcs.getStatus();
			var oc_energyActToEv = evcs.channel("EnergyActiveToEv"); //evcs.channels;
			
			this.channel(VersineticChannelId.CP_SESSION_ID).setNextValue(oc_ocpp_session);
			this.channel(VersineticChannelId.CP_ID).setNextValue(oc_ocpp_id);
			this.channel(VersineticChannelId.CP_STATUS).setNextValue(oc_status);
			this.channel(VersineticChannelId.CP_METER_VALUE).setNextValue(oc_energyActToEv.value());
			
			// Energy.Active.Import.Register: 79389 Wh
			
			/* 
			var oc_measu = getSupportedMeasurements();
			var oc_active_cons_energy = evcs.getActiveConsumptionEnergy();
			var oc_energy_session = evcs.getEnergySession();
			var oc_charge_power = evcs.getChargePower();
			var oc_state = evcs.getState();
			*/
			
			VersineticChargerImpl.this.log
				.info("Event: TOPIC_CYCLE_AFTER_PROCESS_IMAGE");
			
			VersineticChargerImpl.this.log
				.info("Channel CP_session: " + oc_ocpp_session);
			
			VersineticChargerImpl.this.log
				.info("Channel CP_id: " + oc_ocpp_id);
			
			VersineticChargerImpl.this.log
				.info("Channel CP_status: " + oc_status);

			VersineticChargerImpl.this.log
				.info("Channel EAtoEv : " + oc_energyActToEv.value());
			
			break;
			
		  default:
		  	break;
		}
	}

	@Override
	public OcppStandardRequests getStandardRequests() {
		AbstractOcppEvcsComponent evcs = this;

		return chargePower -> {

			var request = new DataTransferRequest("Versinetic");

			int phases = evcs.getPhases().orElse(3);

			var target = Math.round(chargePower / phases / 230.0) /* voltage */ ;

			var maxCurrent = evcs.getMaximumHardwarePower().orElse(DEFAULT_HARDWARE_LIMIT) / phases / 230;
			target = target > maxCurrent ? maxCurrent : target;

			request.setMessageId("SetLimit");
			request.setData("logicalid=" + VersineticChargerImpl.this.config.limitId() + ";value=" + String.valueOf(target));
			return request;
		};
	}

	@Override
	public List<Request> getRequiredRequestsAfterConnection() {
		List<Request> requests = new ArrayList<>();

		var setMeterValueSampleInterval = new ChangeConfigurationRequest("MeterValueSampleInterval", "10");
		requests.add(setMeterValueSampleInterval);

		var setMeterValueSampledData = new ChangeConfigurationRequest("MeterValuesSampledData",
				"Energy.Active.Import.Register,Current.Import,Voltage,Power.Active.Import,Temperature");
		requests.add(setMeterValueSampledData);

		return requests;
	}

	@Override
	public List<Request> getRequiredRequestsDuringConnection() {
		List<Request> requests = new ArrayList<>();

		var requestMeterValues = new TriggerMessageRequest(TriggerMessageRequestType.MeterValues);
		requestMeterValues.setConnectorId(this.getConfiguredConnectorId());
		requests.add(requestMeterValues);

		var requestStatus = new TriggerMessageRequest(TriggerMessageRequestType.StatusNotification);
		requestStatus.setConnectorId(this.getConfiguredConnectorId());
		requests.add(requestStatus);

		var setMeterValueSampledData = new ChangeConfigurationRequest("MeterValuesSampledData",
				"Energy.Active.Import.Register,Current.Import,Voltage,Power.Active.Import,Temperature");
		requests.add(setMeterValueSampledData);

		return requests;
	}

	@Override
	public EvcsPower getEvcsPower() {
		return this.evcsPower;
	}

	@Override
	public boolean returnsSessionEnergy() {
		return false;
	}

	@Deactivate
	protected void deactivate() {
		super.deactivate();
	}

	public String debugLog() {
		return "Hi, I'm a debug log row";
	}
}
