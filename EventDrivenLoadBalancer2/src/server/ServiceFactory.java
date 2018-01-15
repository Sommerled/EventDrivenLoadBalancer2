package server;

import eventHandler.EventDispatcher;
import eventHandler.EventListener;

/**
 * A factory for creating a specific service
 */
public interface ServiceFactory {
	
	/**
	 * Creates a service
	 * 
	 * @param listener
	 * @param dispatcher
	 * @return
	 */
	public Service CreateService(EventListener listener, EventDispatcher dispatcher);
}
