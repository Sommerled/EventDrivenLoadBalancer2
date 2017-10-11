package server;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<String> getBatchIDs(int num){
		List<String> IDs = new ArrayList<String>();
		
		for(int i = 0; i < num; i++){
			IDs.add(getFreeID());
		}
		
		return IDs;
	}
	
	public void addFreeID(String freed){
		freedIds.add(freed);
	}
}
