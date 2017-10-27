package server;

import eventHandler.EventDispatcher;
import eventHandler.EventDispatcherAware;
import eventHandler.EventListener;
import eventHandler.EventListenerAware;

/**
 * Foundational class for building services
 * internal to the load balancer
 */
public abstract class Service implements EventDispatcherAware, EventListenerAware, Runnable {
	private EventListener listener = null;
	private EventDispatcher dispatcher = null;

	public Service(EventListener listener, EventDispatcher dispatcher){
		this.listener = listener;
		this.dispatcher = dispatcher;
	}
	
	/**
	 * Retrieve the listener
	 */
	@Override
	public EventListener getEventListener() {
		return this.listener;
	}

	/**
	 * Set the listener
	 */
	@Override
	public void setEventListener(EventListener eventListener) {
		this.listener = eventListener;
	}

	/**
	 * Retrieve the dispatcher that sends
	 * events to the event queue
	 */
	@Override
	public EventDispatcher getEventDispatcher() {
		return this.dispatcher;
	}

	/**
	 * Sets the dispatcher
	 */
	@Override
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.dispatcher = eventDispatcher;
	}
}
