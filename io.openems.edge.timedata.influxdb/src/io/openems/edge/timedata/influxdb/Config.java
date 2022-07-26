package io.openems.edge.timedata.influxdb;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition( //
		name = "Timedata InfluxDB", //
		description = "This component persists all data to an InfluxDB timeseries database.")
@interface Config {

	@AttributeDefinition(name = "Component-ID", description = "Unique ID of this Component")
	String id() default "influx0";

	@AttributeDefinition(name = "Alias", description = "Human-readable name of this Component; defaults to Component-ID")
	String alias() default "";

	@AttributeDefinition(name = "Is enabled?", description = "Is this Component enabled?")
	boolean enabled() default true;

	@AttributeDefinition(name = "URL", description = "The InfluxDB URL, e.g.: http://ip:port")
	String url();

	@AttributeDefinition(name = "Org", description = "The Organisation; '-' for InfluxDB v1")
	String org() default "-";
	
	@AttributeDefinition(name = "ApiKey", description = "The ApiKey; 'username:password' for InfluxDB v1")
	String apiKey();

	@AttributeDefinition(name = "Bucket", description = "The bucket name; 'database/retentionPolicy' for InfluxDB v1")
	String bucket();
	
	@AttributeDefinition(name = "No of Cycles", description = "How many Cycles till data is written to InfluxDB.")
	int noOfCycles() default 1;

	@AttributeDefinition(name = "Read-Only mode", description = "Activates the read-only mode. Then no data is written to InfluxDB.")
	boolean isReadOnly() default false;
	
	@AttributeDefinition(name = "Write channels name on text file", description = "Activate a function to save on text file all data keys (channels name) written on InfluxDB")
	boolean isFileKey() default false;
	
	@AttributeDefinition(name = "Text file URL", description = "The text file URL for saving all data keys")
	String fileKeyurl() default "/Users/gpoletto/Sites/eclipse_workspaces/openems_org/var/lib/openems/influx_written_channels.txt";
	
	@AttributeDefinition(name = "Write ONLY channels present on custom text file", description = "Activate a function to write on InfluxDB only data keys (channels name) present on a custom text file")
	boolean isOnlyOnFile() default false;
	
	@AttributeDefinition(name = "Custom text file URL", description = "The text file containing data keys to be written on InfluxDB (URL)")
	String fileKeyValidurl() default "/Users/gpoletto/Sites/eclipse_workspaces/openems_org/var/lib/openems/influx_valid_channels.txt";

	String webconsole_configurationFactory_nameHint() default "Timedata InfluxDB [{id}]";
}