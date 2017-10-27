package Balancer;

import java.util.Iterator;
import java.util.LinkedList;

import context.ConnectionContext;

public class RoundRobinBalancer implements ConnectionBallancer{
	private LinkedList<ConnectionContext> connections = null;
	private Iterator<ConnectionContext> iter = null;
	private int contextsNeeded = 0;
	private final ConnectionContext context;
	
	public RoundRobinBalancer(int contextsNeeded, ConnectionContext context){
		this.connections = new LinkedList<ConnectionContext>();
		this.contextsNeeded = contextsNeeded;
		this.context = context;
	}
	
	@Override
	public ConnectionContext nextConnection() {
		ConnectionContext cc = null;
		
		if(this.iter.hasNext()){
			cc = this.iter.next();
		}else{
			this.iter = null;
			this.iter = this.connections.iterator();
			cc = this.iter.next();
		}
		
		return cc;
	}

	@Override
	public void registerContext(ConnectionContext c) {
		if(! this.connections.contains(c)){
			this.connections.addLast(c);
			
			//if(this.connections.size() == 1){
				this.iter = this.connections.iterator();
			//}
		}
	}

	@Override
	public boolean needsContext() {
		return this.connections.size() != this.contextsNeeded;
	}

	@Override
	public boolean originator(ConnectionContext cc) {
		return this.context.equals(cc);
	}

}
