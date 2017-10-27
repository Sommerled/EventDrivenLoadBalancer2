package server;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for generating ever expanding id's, such that 
 * the more id's that are requested, the larger that
 * they become.
 */
public class IDGenerator {
	private static final String INCREMENT = "-BAAAAAAA"; 
	private List<String> freedIds = null;
	private char thresh = 'Q';
	private int pos = 0;
	private String baseID = "";
	private char[] ID = {'A', 'A', 'A', 'A','A','A','A','A'};
	
	public IDGenerator(){
		this.freedIds = new ArrayList<String>();
	}
	
	/**
	 * Generates a new ID
	 */
	private String getNewID(){
		String retID = new String(this.ID);
		
		this.ID[0]++;
		
		if(this.ID[0] > thresh){
			this.ID[0] = 'A';
			int pos = 1;
			while(pos < this.ID.length){
				this.ID[pos]++;
				if(this.ID[pos] <= thresh){
					break;
				}
				
				this.ID[pos] = 'A';
				pos++;
			}
			
			if(this.ID[pos-1] == 'A'){
				String newID = new String(this.ID);
				newID = newID + INCREMENT;
				
				this.ID = newID.toCharArray();
			}
		}
		
		return retID;
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
			retID = getNewID();
		}
		
		return retID;
	}
	
	/**
	 * Returns a group of ID's
	 * 
	 * @param num
	 * @return
	 */
	public List<String> getBatchIDs(int num){
		List<String> IDs = new ArrayList<String>();
		
		for(int i = 0; i < num; i++){
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
