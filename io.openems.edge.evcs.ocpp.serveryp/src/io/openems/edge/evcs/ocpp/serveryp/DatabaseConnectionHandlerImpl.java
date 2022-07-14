package io.openems.edge.evcs.ocpp.serveryp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.chargetime.ocpp.model.core.AuthorizationStatus;

public class DatabaseConnectionHandlerImpl {
	
	public static final String RFID_DEFAULT_API_ENDPOINT = "";
	public static final String RFID_DEFAULT_DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/yp_rfids?user=postgres&password=&ssl=false";
	public static final String RFID_DEFAULT_DB_TABLE = "rfid";
	public static final String RFID_DEFAULT_DB_FIELD = "rfid_uid";
	public static final String RFID_DEFAULT_DB_FIELD_STATUS = "rfid_status";
	
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
		String db_url = this.config.rfid_db_connection_string();
		String api_endpoint = this.config.rfid_api_endpoint();
		if(db_url != "")
		{
			try {
				Connection conn = null;
				
				// Explicit driver for PostgreSQL
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
					switch(rs.getString(this.config.rfid_db_status_field())) {
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
				}
				rs.close();
				st.close();
				
			} catch (SQLException | ClassNotFoundException e) {
				// e.printStackTrace();
				this.logDebug("[YP] Database connection fault: " + e.getMessage());
			}
		}
		else
		{
			// TODO: API connection
		}
		
		return status;
	}	

	private void logDebug(String message) {
		if(this.config.debugMode())
		{
			this.parent.logDebug(this.log, message);
		}	
	}

}
