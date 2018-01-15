package server;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

import eventHandler.EventHandler;
import events.Event;

/**
 * This class starts all the services that were
 * passed into it via the <serviceMap> parameter
 * in it's constructor. This was purposely built
 * with an inversion of control philosophy to
 * enable a more efficient testing approach.
 */
public class Server {
	private EventHandler handler = null;
	private boolean stayAlive = true;
	
	public Server(Map<String, ServiceFactory> serviceMap, List<Event>initialEvents){
		init(serviceMap, initialEvents);
	}
	
	/**
	 * Initializes services and adds all initial events
	 * to the event handler
	 */
	private void init(Map<String, ServiceFactory> serviceMap, List<Event>initialEvents){
		this.handler = new EventHandler();
		ConcurrentLinkedDeque<String> IDList = new ConcurrentLinkedDeque();
		
		Set<String> keys = serviceMap.keySet();
		for(String k : keys){
			ServiceFactory sf = serviceMap.get(k);
			Service service = sf.CreateService(this.handler, this.handler);
			IDList.add(((ServiceWorker)service).getID());
			Thread serviceThread = new Thread(service);
			serviceThread.setName(k);
			serviceThread.start();
		}
		
		ServiceFactoriesController sfc = new ServiceFactoriesController(serviceMap, this.handler, this.handler);
		
		for(int i = 0; i < initialEvents.size(); i++){
			Event event = initialEvents.get(i);
			try {
				event.setOriginator(this);
				this.handler.put(event);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
}
