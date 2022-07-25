package io.openems.edge.evcs.ocpp.generic;

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
import org.osgi.service.event.EventHandler;
import org.osgi.service.event.propertytypes.EventTopics;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.core.ChangeConfigurationRequest;
import eu.chargetime.ocpp.model.core.DataTransferRequest;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageRequest;
import eu.chargetime.ocpp.model.remotetrigger.TriggerMessageRequestType;
import io.openems.edge.common.component.ComponentManager;

import io.openems.edge.common.component.OpenemsComponent;
import io.openems.edge.common.event.EdgeEventConstants;
import io.openems.edge.evcs.api.ChargingType;
import io.openems.edge.evcs.api.Evcs;
import io.openems.edge.evcs.api.EvcsPower;
import io.openems.edge.evcs.api.ManagedEvcs;
import io.openems.edge.evcs.api.MeasuringEvcs;
import io.openems.edge.evcs.ocpp.common.AbstractOcppEvcsComponent;
import io.openems.edge.evcs.ocpp.common.OcppInformations;
import io.openems.edge.evcs.ocpp.common.OcppProfileType;
import io.openems.edge.evcs.ocpp.common.OcppStandardRequests;


@Designate(ocd = Config.class, factory = true)
@Component(//
		name = "Evcs.Ocpp.Generic", //
		immediate = true, //
		configurationPolicy = ConfigurationPolicy.REQUIRE //
		
)
@EventTopics({ //
		EdgeEventConstants.TOPIC_CYCLE_EXECUTE_WRITE, //
		EdgeEventConstants.TOPIC_CYCLE_AFTER_PROCESS_IMAGE //
})
public class GenericChargerImpl extends AbstractOcppEvcsComponent 
		implements Evcs, MeasuringEvcs, ManagedEvcs, OpenemsComponent, EventHandler {

	private final Logger log = LoggerFactory.getLogger(GenericChargerImpl.class);
	
	// Default value for the hardware limit
	private static final Integer DEFAULT_HARDWARE_LIMIT = 22080;

	// Profiles that a generic EVCS should be supporting
	private static final OcppProfileType[] PROFILE_TYPES = { //
			OcppProfileType.CORE //
	};

	/*
	 * Values that a generic EVCS is supporting Info: It is not sure that the all EVCS is using
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


	public GenericChargerImpl() {
		super(//
				PROFILE_TYPES, //
				OpenemsComponent.ChannelId.values(), //
				Evcs.ChannelId.values(), //
				AbstractOcppEvcsComponent.ChannelId.values(), //
				ManagedEvcs.ChannelId.values(), // 
				MeasuringEvcs.ChannelId.values(), //
				GenericChannelId.values() //
		);
	}

	@Activate
	public void activate(ComponentContext context, Config config) {
		this.config = config;
		super.activate(context, config.id(), config.alias(), config.enabled());

		this._setChargingType(ChargingType.AC); // Actually using only AC
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
			 // TODO: fill channels if needed
			  
			 break;
		  
		  case EdgeEventConstants.TOPIC_CYCLE_AFTER_PROCESS_IMAGE:
			// Standard operation cycle
			  
			AbstractOcppEvcsComponent evcs = this;
			
			// standard data for debugging 
			var oc_ocpp_session = evcs.getSessionId();
			var oc_ocpp_id = getConfiguredOcppId();
			var oc_status = evcs.getStatus();
			var oc_energyActToEv = evcs.channel("EnergyActiveToEv"); //evcs.channels;
			
			switch (oc_status) {
			case ERROR:  // error: EVCS not connected to server (previously connected)
				this.logDebug(log, "EVCS: " + oc_ocpp_id + " / Not connected or connection Faulted");
				break;
			
			case NOT_READY_FOR_CHARGING: // available
				this.logDebug(log, "EVCS: " + oc_ocpp_id + " / Available");
				this.logDebug(log, "EVCS status: " + oc_status);
				this.logDebug(log, "EVCS session: " + oc_ocpp_session);
				this.logDebug(log, "EVCS meter value: " + oc_energyActToEv.value());
				this.logDebug(log, "Channel GC_EVCS_ID: " + oc_ocpp_id);
				break;
				
			case CHARGING:	// charging
				this.logDebug(log, "EVCS: " + oc_ocpp_id + " / Charging");
				break;
				
			case CHARGING_FINISHED:
				this.logDebug(log, "EVCS: " + oc_ocpp_id + " / Charging finished");
				this.logDebug(log, "EVCS status: " + oc_status);
				this.logDebug(log, "EVCS session: " + oc_ocpp_session);
				this.logDebug(log, "EVCS meter value: " + oc_energyActToEv.value());
				this.logDebug(log, "Channel GC_EVCS_ID: " + oc_ocpp_id);
				break;
				
			case READY_FOR_CHARGING: // preparing
				this.logDebug(log, "EVCS: " + oc_ocpp_id + " / Pairing with EV and preparing");
				break;
				
			case CHARGING_REJECTED: // suspended by EVSE or EV
				this.logDebug(log, "EVCS: " + oc_ocpp_id + " / Charging suspended by EVSE or EV");
				break;
				
			case UNDEFINED: // Undefined: EVCS is not present on lan
				this.logDebug(log, "EVCS: " + oc_ocpp_id + " / State UNDEFINED");
				break;
				
			case STARTING: // TODO: test this state
				this.logDebug(log, "EVCS: " + oc_ocpp_id + " / Charging is starting");
				this.logDebug(log, "EVCS status: " + oc_status);
				this.logDebug(log, "EVCS session: " + oc_ocpp_session);
				this.logDebug(log, "EVCS meter value: " + oc_energyActToEv.value());
				this.logDebug(log, "Channel GC_EVCS_ID: " + oc_ocpp_id);
				break;
				
			case ENERGY_LIMIT_REACHED: // energy limit reached, test this state
				this.logDebug(log, "EVCS: " + oc_ocpp_id + " / Energy limit reached");
				break;
			}
			
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
			request.setData("logicalid=" + GenericChargerImpl.this.config.limitId() + ";value=" + String.valueOf(target));
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
