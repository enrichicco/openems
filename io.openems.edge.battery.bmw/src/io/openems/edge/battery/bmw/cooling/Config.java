package io.openems.edge.battery.bmw.cooling;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(//
		name = "BMW Battery Cooling Unit", //
		description = "")
@interface Config {

	@AttributeDefinition(name = "Component-ID", description = "Unique ID of this Component")
	String id() default "bmwCoolingUnit0";

	@AttributeDefinition(name = "Alias", description = "Human-readable name of this Component; defaults to Component-ID")
	String alias() default "";

	@AttributeDefinition(name = "Is enabled?", description = "Is this Component enabled?")
	boolean enabled() default true;
	
	@AttributeDefinition(name = "Battery-IDs", description = "IDs of the BMW Battery List")
	String[] batteryIds() default {"bmsA1", "bmsA2", "bmsA3", "bmsA4", "bmsB1", "bmsB2", "bmsB3", "bmsB4"};
	
	@AttributeDefinition(name = "Digital Output for ON / OFF", description = "WagoIO Digital Output Cooling ON / OFF")
	String coolingCommand() default "ioWago/DigitalOutputM1C5";

	String webconsole_configurationFactory_nameHint() default "BMW Battery Cooling Unit [{id}]";

}