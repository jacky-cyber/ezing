package cn.ezing.push;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.relayrides.pushy.apns.ApnsEnvironment;
import com.relayrides.pushy.apns.PushManager;
import com.relayrides.pushy.apns.PushManagerConfiguration;
import com.relayrides.pushy.apns.util.ApnsPayloadBuilder;
import com.relayrides.pushy.apns.util.SSLContextUtil;
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification;
import com.relayrides.pushy.apns.util.TokenUtil;

public class CorrectData {

	public static void main(String[] args) {
		System.out.println("-------- PostgreSQL "
                + "JDBC Connection Testing ------------");
		Connection connection = null;
		try {

            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL JDBC Driver Registered!");
            
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://ip:5432/actor", 
                    "",
                    "");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select id from users");
            int i = 0;
            while (rs.next()){
            	//i++;
            	long userid = rs.getLong("id");  
            	//System.out.println(" userid= " + userid);
            	
            	Statement stmt1 = connection.createStatement();
            	ResultSet rs1 = stmt1.executeQuery("select * from auth_ids where deleted_at IS NULL and user_id = "+userid);
            	while (rs1.next()){
            		long auth_id = rs1.getLong("id");  
            		//System.out.println("     auth_id= " + auth_id);
            		Statement stmt2 = connection.createStatement();
            		ResultSet rs2 = stmt2.executeQuery("select count(timestamp) count, max(timestamp) timestamp1 from seq_updates_ngen  where auth_id = "+auth_id+" and seq=1000");
            		while (rs2.next()){
	            		long count = rs2.getLong("count");  
	            		if(count>1){
	            			long timestamp = rs2.getLong("timestamp1"); 
	            			System.out.println(" userid= " + userid);
	            			System.out.println("     auth_id= " + auth_id); 
	            			System.out.println("         count= " + count);
	            			System.out.println("         timestamp= " + timestamp);
	            			i++;
	            			
	            			//Statement stmt3 = connection.createStatement();
	            			//int nRows = stmt3.executeUpdate("DELETE FROM seq_updates_ngen WHERE auth_id = "+auth_id+" and timestamp < "+timestamp);
	            			//stmt3.close();
	            		}
            		}
            		stmt2.close();
            		
            	}
            	
            	stmt1.close();
                
            }
            
            System.out.println("------all----- " + i);
            //pushManager.shutdown();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			 if (connection != null) {
	             //System.out.println("You made it, take control your database now!");
	             connection.close();
			 } else {
	             //System.out.println("Failed to make connection!");
			 }
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	static final String HEXES = "0123456789ABCDEF";
	  public static String getHex( byte [] raw ) {
	    if ( raw == null ) {
	      return null;
	    }
	    final StringBuilder hex = new StringBuilder( 2 * raw.length );
	    for ( final byte b : raw ) {
	      hex.append(HEXES.charAt((b & 0xF0) >> 4))
	         .append(HEXES.charAt((b & 0x0F)));
	    }
	    return hex.toString();
	  }
}
