package Balancer;

import context.ConnectionContext;

public interface WaitingContext {
	public boolean alert(ConnectionContext cc);
	public boolean done();
}
