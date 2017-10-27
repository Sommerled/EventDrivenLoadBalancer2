package Balancer;

import context.ConnectionContext;

public interface ConnectionBallancer {
	public ConnectionContext nextConnection();
	public void registerContext(ConnectionContext c);
	public boolean needsContext();
	public boolean originator(ConnectionContext cc);
}
