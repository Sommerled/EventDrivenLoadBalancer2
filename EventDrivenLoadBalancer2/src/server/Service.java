package server;

import eventHandler.EventDispatcher;
import eventHandler.EventDispatcherAware;
import eventHandler.EventListener;
import eventHandler.EventListenerAware;

public abstract class Service implements EventDispatcherAware, EventListenerAware, Runnable {
	private EventListener listener = null;
	private EventDispatcher dispatcher = null;

	public Service(EventListener listener, EventDispatcher dispatcher){
		this.listener = listener;
		this.dispatcher = dispatcher;
	}
	
	@Override
	public EventListener getEventListener() {
		return this.listener;
	}

	@Override
	public void setEventListener(EventListener eventListener) {
		this.listener = eventListener;
	}

	@Override
	public EventDispatcher getEventDispatcher() {
		return this.dispatcher;
	}

	@Override
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.dispatcher = eventDispatcher;
	}
}
