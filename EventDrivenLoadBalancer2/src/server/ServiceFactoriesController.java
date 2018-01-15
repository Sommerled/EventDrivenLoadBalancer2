package server;

import java.util.HashMap;
import java.util.Map;

import eventHandler.EventDispatcher;
import eventHandler.EventDispatcherAware;
import eventHandler.EventListener;
import eventHandler.EventListenerAware;

/**
 * A Class for holding a <Map> of <ServiceFactory>s that 
 * are used to create a <Service> on demand.
 */
public class ServiceFactoriesController implements EventDispatcherAware, EventListenerAware{
	private Map<String, ServiceFactory> factoryMap = null;
	private EventDispatcher dispatcher = null;
	private EventListener listener = null;
	
	public ServiceFactoriesController(Map<String, ServiceFactory> factoryMap, 
			EventDispatcher dispatcher, EventListener listener){
		this.factoryMap = factoryMap;
		
		if(this.factoryMap == null){
			this.factoryMap = new HashMap<String, ServiceFactory>();
		}
		
		this.listener = listener;
		this.dispatcher = dispatcher;
	}
	
	/**
	 * Returns a <Service> if it's factory is 
	 * in the internal <ServiceFactory> <Map>.
	 * Otherwise it returns null.
	 * 
	 * @param key
	 * @return
	 */
	public Service createService(String key){
		ServiceFactory sf = this.factoryMap.get(key);
		
		if(sf == null){
			return null;
		}else{
			return sf.CreateService(this.listener, this.dispatcher);
		}
	}

	@Override
	public EventDispatcher getEventDispatcher() {
		return this.dispatcher;
	}

	@Override
	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.dispatcher = eventDispatcher;
	}

	@Override
	public EventListener getEventListener() {
		return this.listener;
	}

	@Override
	public void setEventListener(EventListener eventListener) {
		this.listener = eventListener;
	}
}
