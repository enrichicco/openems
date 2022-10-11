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
	 *	  { "evcs_serial": "ray-032302579530773700", "evcs_status": "Accepted" },
	 *	  { "evcs_serial": "", "evcs_status": "Rejected" }
	 *	]
     *
	 */
	
}
