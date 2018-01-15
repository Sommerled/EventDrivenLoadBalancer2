package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Balancer.BalancerService;
import context.ConnectionContext;
import context.ContextEvent;
import context.ContextLoader;
import eventHandler.EventDispatcher;
import eventHandler.EventListener;
import events.Event;
import events.EventType;
import workerCreation.WorkerCreationService;
/**
 * This class is for launching the load balancing server
 */
public class ServerMain {

	public static void main(String[] args) {
		String configFile = "";
		String keystorePath = "";
		
		if(args.length > 0){
			configFile = args[0];
		}else{
			configFile = System.getProperty("user.home") + "\\AppData\\Roaming\\EDLB\\Connections.xml";
		}
		
		if(args.length > 1){
			keystorePath = args[1];
		}else{
			keystorePath = System.getProperty("user.home") + "\\AppData\\Roaming\\EDLB\\keystores";
		}
		
		try {
			ContextLoader.loadContexts(configFile, keystorePath);
			
			Map<String, ServiceFactory> serviceMap = new HashMap<String, ServiceFactory>();
			
			IDGenerator generator = new IDGenerator();
			List<String> ids = generator.getBatchIDs(3);
			
			serviceMap.put("ID Generator", 
					(EventListener listener, EventDispatcher dispatcher)->{
						return new IDGenerationService(listener, dispatcher, ids.get(0), generator);
					}
			);
			
			serviceMap.put("Balancing Act", 
					(EventListener listener, EventDispatcher dispatcher)->{
						return new BalancerService(listener, dispatcher, ids.get(1));
					}
			);
			
			serviceMap.put("Creation", 
					(EventListener listener, EventDispatcher dispatcher)->{
						return new WorkerCreationService(listener, dispatcher, ids.get(2));
					}
			);
			
			List<Event>initialEvents = new ArrayList<Event>();
			List<ConnectionContext> contexts = ContextLoader.getLoadedContexts();
			
			for(int i = 0; i < contexts.size(); i++){
				Event e = new ContextEvent(null, null, EventType.NEW_CONNECTION, false, contexts.get(i));
				initialEvents.add(e);
			}
			
			new Server(serviceMap, initialEvents);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
