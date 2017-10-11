package server;

import java.util.List;

import context.ConnectionContext;
import context.ContextEvent;
import context.ContextLoader;
import eventHandler.EventHandler;
import events.EventType;
public class Server {
	private EventHandler handler = null;
	private IDGenerationService igs = null;
	
	public Server(){
		init();
	}
	
	public void init(){
		this.handler = new EventHandler();
		
		this.igs = new IDGenerationService(handler, handler);
		Thread igsThread = new Thread(igs);
		igsThread.setName("ID Generator");
		igsThread.start();
		
		List<ConnectionContext> contexts = ContextLoader.getLoadedContexts();
		for(int i = 0; i < contexts.size(); i++){
			ContextEvent ce = new ContextEvent(null, this, EventType.NEW_CONNECTION, false, contexts.get(i));
			
			try {
				this.handler.put(ce);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}
