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
	String rfid_api_endpoint() default DatabaseConnectionHandlerImpl.RFID_DEFAULT_API_ENDPOINT;
	
	@AttributeDefinition(name = "Rfid-DB database connection string", description = "The database connection string for external RFID auth")
	String rfid_db_connection_string() default DatabaseConnectionHandlerImpl.RFID_DEFAULT_DB_CONNECTION;
	
	@AttributeDefinition(name = "Rfid-DB table", description = "The table containing RFIDs")
	String rfid_db_table() default DatabaseConnectionHandlerImpl.RFID_DEFAULT_DB_TABLE;
	
	@AttributeDefinition(name = "Rfid-DB table field for RFID-UID", description = "The field on table for RFIDs UID")
	String rfid_db_field() default DatabaseConnectionHandlerImpl.RFID_DEFAULT_DB_FIELD;
	
	@AttributeDefinition(name = "Rfid-DB table field for RFID status", description = "The field on table for RFIDs status")
	String rfid_db_status_field() default DatabaseConnectionHandlerImpl.RFID_DEFAULT_DB_FIELD_STATUS;
	
	String webconsole_configurationFactory_nameHint() default "EVCS OCPP Server YP [{id}]";
}