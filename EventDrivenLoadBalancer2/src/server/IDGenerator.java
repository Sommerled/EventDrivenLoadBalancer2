package server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * A class for generating ever expanding id's, such that 
 * the more id's that are requested, the larger that
 * they become.
 */
public class IDGenerator {
	private List<String> freedIds = null;
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public IDGenerator(){
		this.freedIds = new LinkedList<String>();
	}
	
	/**
	 * Generates a SHA-256 hashed UUID.
	 * Created with help from http://www.baeldung.com/java-uuid
	 * 
	 * In theory it would take making 1 billion UUIDs every second
	 * for 100 years before a 50% chance of a collision (duplicate ID) 
	 * would be achieved.
	 * 
	 * @return
	 */
	private String sha_256_UUID(){
		String retID = "";
		
		try {
			MessageDigest salt = MessageDigest.getInstance("SHA-256");
			salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
			retID = bytesToHex(salt.digest());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return retID;
	}
	
	/**
	 * Converts a byte array to a hex string.
	 * Taken from:
	 * https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
	 * 
	 * @param bytes
	 * @return
	 */
	private String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	/**
	 * Generates or retrieves an existing ID
	 * @return
	 */
	public String getFreeID(){
		String retID = "";
		
		if(freedIds.size() > 0){
			retID = freedIds.get(0);
			freedIds.remove(0);
		}else{
			retID = sha_256_UUID();
		}
		
		return retID;
	}
	
	/**
	 * Returns a group of ID's
	 * 
	 * @param num
	 * @return
	 */
	public List<String> getBatchIDs(long num){
		LinkedList<String> IDs = new LinkedList<String>();
		
		for(long i = 0; i < num; i++){
			IDs.add(getFreeID());
		}
		
		return IDs;
	}
	
	/**
	 * registers an ID that has been freed
	 * 
	 * @param freed
	 */
	public void addFreeID(String freed){
		freedIds.add(freed);
	}
}
