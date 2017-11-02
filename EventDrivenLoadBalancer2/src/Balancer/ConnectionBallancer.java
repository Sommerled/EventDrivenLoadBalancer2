package Balancer;

import context.ConnectionContext;

/**
 * The interface that all balancing objects must implement
 */
public interface ConnectionBallancer {
	/**
	 * Retrieves the next connection to be used
	 * @return
	 */
	public ConnectionContext nextConnection();
	
	/**
	 * Registers a new connection with the balancer
	 * @param c
	 */
	public void registerContext(ConnectionContext c);
	
	/**
	 * Returns true if the balancer is missing a context
	 * @return
	 */
	public boolean needsContext();
	
	/**
	 * Returns true if <cc> is the context for a server socket
	 * 
	 * @param cc
	 * @return
	 */
	public boolean originator(ConnectionContext cc);
}
