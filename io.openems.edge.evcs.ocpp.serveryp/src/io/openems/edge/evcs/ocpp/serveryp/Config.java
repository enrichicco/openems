package io.openems.edge.evcs.ocpp.serveryp;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(//
		name = "EVCS OCPP Server YP", //
		description = "Implements a OCPP Server from YP. Only one Server needed.")
@interface Config {

	@AttributeDefinition(name = "Component-ID", description = "Unique ID of this Component")
	String id() default "ocppServerYP0";

	@AttributeDefinition(name = "Alias", description = "Human-readable name of this Component; defaults to Component-ID")
	String alias() default "";

	@AttributeDefinition(name = "Is enabled?", description = "Is this Component enabled?")
	boolean enabled() default true;

	@AttributeDefinition(name = "Debug Mode", description = "Activates the debug mode")
	boolean debugMode() default false;

	@AttributeDefinition(name = "IP-Address", description = "The IP address to listen on. ('0.0.0.0' for any IP)")
	String ip() default OcppServerImpl.DEFAULT_IP;

	@AttributeDefinition(name = "Port", description = "The port of to listen on.")
	int port() default OcppServerImpl.DEFAULT_PORT;

	// RFID external databases for status management
	@AttributeDefinition(name = "Rfid-DB API endpoint", description = "The database connection string for external RFID auth")
	String rfid_api_endpoint();
	
	@AttributeDefinition(name = "Rfid-DB database connection string", description = "The database connection string for external RFID auth")
	String rfid_db_connection_string() default "jdbc:postgresql://127.0.0.1:5432/[your_database]?user=postgres&password=&ssl=false";
	
	@AttributeDefinition(name = "Rfid-DB table", description = "The table containing RFIDs")
	String rfid_db_table() default "rfid";
	
	@AttributeDefinition(name = "Rfid-DB table field for RFID-UID", description = "The field on table for RFIDs UID")
	String rfid_db_field() default "rfid_uid";
	
	@AttributeDefinition(name = "Rfid-DB table field for RFID status", description = "The field on table for RFIDs status")
	String rfid_db_status_field() default "rfid_status";
	
	@AttributeDefinition(name = "Rfid JSON file for RFID status", description = "The ABSOLUTE PATH (with filename.json) for for RFID status")
	String rfid_json_path() default  "/Users/gpoletto/Sites/eclipse_workspaces/openems_org/var/lib/openems/rfid_auth_list.json";
	
	@AttributeDefinition(name = "Evcs-DB table", description = "The table containing EVCSs")
	String evcs_db_table() default "evcs";
	
	@AttributeDefinition(name = "Evcs-DB table field for Evcs-UID", description = "The field on table for EVCSs UID")
	String evcs_db_field() default "evcs_uid";
	
	@AttributeDefinition(name = "Evcs-DB table field for EVCS status", description = "The field on table for EVCSs status")
	String evcs_db_status_field() default "evcs_status";
	
	@AttributeDefinition(name = "Evcs JSON file for EVCS status", description = "The ABSOLUTE PATH (with filename.json) for for EVCS status")
	String evcs_json_path() default  "/Users/gpoletto/Sites/eclipse_workspaces/openems_org/var/lib/openems/evcs_auth_list.json";
	
	String webconsole_configurationFactory_nameHint() default "EVCS OCPP Server YP [{id}]";
}