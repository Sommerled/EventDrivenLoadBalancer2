package Balancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import context.ConnectionContext;

/**
 * Balancer is both a library of all registered
 * <ConnectionContext>s and the mechanism that determines
 * how new connections are balanced.
 */
public class Balancer {
	private static final String ROUND_ROBIN = "Round Robin";
	private List<ConnectionContext> connectionList = null;
	private Map<ConnectionContext, ConnectionBallancer> connectionBalancerMap = null;
	private List<WaitingAdapter> needsContext = null;
	private List<ConnectionContext> unused = null;
	
	public Balancer(){
		this.connectionList = new ArrayList<ConnectionContext>();
		this.connectionBalancerMap = new HashMap<ConnectionContext, ConnectionBallancer>();
		this.needsContext = new ArrayList<WaitingAdapter>();
		this.unused = new ArrayList<ConnectionContext>();
	}

	public ConnectionContext nextConnection(ConnectionContext cc){
		ConnectionContext next = null;
		
		if(this.connectionBalancerMap.containsKey(cc)){
			ConnectionBallancer cb = this.connectionBalancerMap.get(cc);
			next = cb.nextConnection();
		}
		
		return next;
	}
	
	public boolean addConnection(ConnectionContext cc){
		boolean ret = false;
		
		if(!this.connectionList.contains(cc)){
			ret = this.connectionList.add(cc);
			ConnectionBallancer cb = null;
			
			if(cc.getListening()){
				switch(cc.getAlgorithm()){
				case ROUND_ROBIN:
					cb = new RoundRobinBalancer(cc.getBalances(), cc);
					break;
				}
				
				if(cb != null){
					this.connectionBalancerMap.put(cc, cb);
					for(int i = 0; i < this.unused.size(); i++){
						ConnectionContext u = this.unused.get(i);
						if(u.getListensFor().equals(cc)){
							cb.registerContext(u);
							this.unused.remove(u);
							
							if(this.unused.size() > 0){
								i--;
							}else{
								break;
							}
						}
					}
				}
			}else{
				boolean balanced = false;
				
				for(WaitingAdapter wa : needsContext){
					if(wa.alert(cc) && wa.done()){
						needsContext.remove(wa);
						balanced = true;
						break;
					}
				}
				
				if(!balanced){
					unused.add(cc);
				}
			}
		}
		
		return ret;
	}
}
