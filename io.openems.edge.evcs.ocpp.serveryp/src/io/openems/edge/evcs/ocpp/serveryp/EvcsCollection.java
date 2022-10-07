package io.openems.edge.evcs.ocpp.serveryp;

public class EvcsCollection {

	/*
	 *  EVCS unique name (internal for YouPower)
	 */
	String evcs_uid;
	
	/*
	 *  EVCS serial number (max 25)
	 */
	String evcs_serial;
	
	/*
	 *  Available statuses:
	 *  Accepted
	 *  Pending
	 *  Rejected
	 */
	String evcs_status;
	
	
	/*
	 *  JSON file structure:
	 *  [
	 *	  { "evcs_uid": "YP_Test_001", "evcs_status": "Accepted" },
	 *	  { "evcs_uid": "YP_Test_002", "evcs_status": "Rejected" }
	 *	]
     *
	 */
	
}
