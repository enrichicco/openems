package io.openems.edge.evcs.ocpp.serveryp;

public class RfidCollection {

	/*
	 *  Max 20 char string case sensitive
	 */
	String rfid_uid;
	
	/*
	 *  Available statuses:
	 *  Accepted
	 *  Blocked
	 *  Expired
	 *  Invalid
	 *  ConcurrentTx
	 */
	String rfid_status;
	
	
	/*
	 *  JSON file structure:
	 *  [
	 *	  { "rfid_uid": "72f42d2d", "rfid_status": "Accepted" },
	 *	  { "rfid_uid": "72f42d2g", "rfid_status": "Blocked" }
	 *	]
     *
	 */
	
}
