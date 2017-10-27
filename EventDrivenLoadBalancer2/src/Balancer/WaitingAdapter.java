package Balancer;

import context.ConnectionContext;

public class WaitingAdapter implements WaitingContext{
	private final ConnectionContext waiting;
	private final ConnectionBallancer cb;
	
	public WaitingAdapter(ConnectionContext cc, ConnectionBallancer cb){
		this.waiting = cc;
		this.cb = cb;
	}
	
	@Override
	public boolean alert(ConnectionContext cc) {
		boolean ret = cc.getListensFor().equals(this.waiting);
		
		if(cc.getListensFor().equals(this.waiting)){
			this.cb.registerContext(cc);
		}
		
		return ret;
	}

	@Override
	public boolean done() {
		return !this.cb.needsContext();
	}
}
