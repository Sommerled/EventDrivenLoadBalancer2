package server;

import java.util.List;

import Balancer.BalancerService;
import context.ConnectionContext;
import context.ContextEvent;
import context.ContextLoader;
import eventHandler.EventHandler;
import events.EventType;
import workerCreation.WorkerCreationService;
public class Server {
	private EventHandler handler = null;
	private IDGenerationService igs = null;
	private BalancerService balancer = null;
	private WorkerCreationService workerCreationService = null;
	
	public Server(){
		init();
	}
	
	public void init(){
		this.handler = new EventHandler();
		
		this.igs = new IDGenerationService(this.handler, this.handler);
		Thread igsThread = new Thread(this.igs);
		igsThread.setName("ID Generator");
		igsThread.start();
		
		this.balancer = new BalancerService(this.handler, this.handler);
		Thread balancerThread = new Thread(this.balancer);
		balancerThread.setName("Balancing Act");
		balancerThread.start();
		
		this.workerCreationService = new WorkerCreationService(this.handler, this.handler);
		Thread CreationThread = new Thread(this.workerCreationService);
		CreationThread.setName("Creation");
		CreationThread.start();
		
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
