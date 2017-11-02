package Balancer;

import java.util.Iterator;
import java.util.LinkedList;

import context.ConnectionContext;

/** 
 * A balancing object that balances according
 * to the Round Robin algorithm. In another
 * word, it has a list of <ConnectionContext>s
 * and gives the next <ConnectionContext> in the
 * list, returning to the beginning of the list
 * if the end has been reached, to handle a new
 * connection that originates from a server socket.
 *
 */
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
