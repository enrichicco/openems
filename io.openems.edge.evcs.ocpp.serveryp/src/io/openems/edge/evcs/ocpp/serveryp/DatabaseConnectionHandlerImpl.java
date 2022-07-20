package io.openems.edge.evcs.ocpp.serveryp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import eu.chargetime.ocpp.model.core.AuthorizationStatus;

public class DatabaseConnectionHandlerImpl {
	
	public static final String RFID_DEFAULT_API_ENDPOINT = "";
	public static final String RFID_DEFAULT_DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/yp_rfids?user=postgres&password=&ssl=false";
	public static final String RFID_DEFAULT_DB_TABLE = "rfid";
	public static final String RFID_DEFAULT_DB_FIELD = "rfid_uid";
	public static final String RFID_DEFAULT_DB_FIELD_STATUS = "rfid_status";
	public static final String RFID_DEFAULT_JSON_ABSOLUTE_PATH = "/Users/gpoletto/Sites/openems_org/var/lib/openems/rfid_auth_list.json";
	
	public Config config;
	
	private final Logger log = LoggerFactory.getLogger(CoreEventHandlerImpl.class);

	private final OcppServerImpl parent;
	
	public DatabaseConnectionHandlerImpl(OcppServerImpl parent) {
		this.config = parent.config;
		this.parent = parent;
	}
	
	/**
	 * Connect to external DB to receive Rfid status.
	 *
	 * @param idTag Rfid UID
	 * @return Rfid status
	 */
	public AuthorizationStatus CheckIdTag(String idTag) {
		AuthorizationStatus status = AuthorizationStatus.Invalid;
		String returned_status = "";
		String db_url = this.config.rfid_db_connection_string();
		String api_endpoint = this.config.rfid_api_endpoint();
		
		// 1 - try connecting database
		if(db_url != "")
		{
			try {
				Connection conn = null;
				
				// Explicit driver for PostgreSQL (only)
				if(db_url.contains("postgresql"))
				{
					Class.forName("org.postgresql.Driver");	
				}
				conn = DriverManager.getConnection(db_url);
				PreparedStatement st = conn.prepareStatement("SELECT * FROM " + this.config.rfid_db_table() + " WHERE " + this.config.rfid_db_field() + " = ?");
				st.setString(1, idTag);
				ResultSet rs = st.executeQuery();
				if (rs.next())
				{
					returned_status = rs.getString(this.config.rfid_db_status_field());
				}
				rs.close();
				st.close();
				
			} catch (SQLException | ClassNotFoundException e) {
				
				// 2 - database connection fault: try reading local JSON file
				this.logDebug("[YP] Database connection fault: " + e.getMessage());
				this.logDebug("[YP] Reading local Rfid list");
				
				returned_status = this.searchJsonFile(idTag);
			}
		}
		else
		{
			// TODO: API connection
		}
		
		switch(returned_status) {
		case "Accepted":
			status = AuthorizationStatus.Accepted;
			this.logDebug("[YP] RFID-ID: " + idTag + " / Accepted");
			break;
		case "Blocked":
			status = AuthorizationStatus.Blocked;
			this.logDebug("[YP] RFID-ID: " + idTag + " / Blocked");
			break;
		case "Expired":
			status = AuthorizationStatus.Expired;
			this.logDebug("[YP] RFID-ID: " + idTag + " / Expired");
			break;
		case "ConcurrentTx":
			status = AuthorizationStatus.ConcurrentTx;
			this.logDebug("[YP] RFID-ID: " + idTag + " / ConcurrentTx");
			break;
		default:
			status = AuthorizationStatus.Invalid;
			this.logDebug("[YP] RFID-ID: " + idTag + " / Invalid");
		}
		
		return status;
	}
	
	/**
	 * Read local JSON file to receive Rfid status.
	 *
	 * @param idTag Rfid UID
	 * @return Rfid status
	 */
	@SuppressWarnings("finally")
	public String searchJsonFile(String idTag) {
	    
		String gsonStatus = "Invalid";
		
		String db_field = this.config.rfid_db_field(); 				
		String db_status = this.config.rfid_db_status_field(); 
	    
	    this.logDebug("[YP] RFID-ID searched on Json: " + idTag);
	    
	    try {
	    	
			String filepath = this.config.rfid_json_path();
	    	Gson gson = new Gson();
	    	Reader reader = Files.newBufferedReader(Paths.get(filepath));
	    	
	    	List<HashMap<String,String>> MrfidArray = new Gson().fromJson(reader, new TypeToken<List<HashMap<String,String>>>(){}.getType());
	    	
	    	List<HashMap<String, String>> rFids = MrfidArray.stream()
												.filter(p -> p.get(db_field).equals(idTag))
												.collect(Collectors.toList());
	    	
	    	if(rFids.size() == 1)
	    	{
	    		for(HashMap<String, String> p : rFids) {
		    		gsonStatus = p.get(db_status);
	    		}
	    	}
	    	else
	    	{
	    		this.logDebug("[YP] Reading local list fault, RFID-ID searched on Json: " + idTag + "not found of rfid.size > 1" );
	    		gsonStatus = "Invalid";
	    	}	
	    		
	    	reader.close();
	    	
	    } catch (Exception e) {
	    	this.logDebug("[YP] Reading local list fault: " + e.getMessage());
	    }
		finally {
			return gsonStatus;
		}
	}

	private void logDebug(String message) {
		if(this.config.debugMode())
		{
			this.parent.logDebug(this.log, message);
		}	
	}

}
