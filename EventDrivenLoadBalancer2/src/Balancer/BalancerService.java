package Balancer;

import context.ConnectionContext;
import context.ContextEvent;
import context.DoubleContextEvent;
import eventHandler.EventDispatcher;
import eventHandler.EventListener;
import events.Event;
import events.EventType;
import server.Service;
import server.socketWorkers.SocketContextEvent;
/**
 * This service uses the <Balancer> class
 * to register new <ConnectionContext>s and
 * to get the next <ConnectionContext> that
 * will handle directing traffic for a new
 * connection.
 */
public class BalancerService extends Service{
	private final Balancer balancingAct; //Ha ha
	
	public BalancerService(EventListener listener, EventDispatcher dispatcher) {
		super(listener, dispatcher);
		this.balancingAct = new Balancer();
	}

	@Override
	public void run() {
		while(true){
			try {
				Event event = this.getEventListener().peek();
				
				switch(event.getEventType()){
				case NEW_CONNECTION:
					ContextEvent ce = (ContextEvent) event;
					this.balancingAct.addConnection(ce.getContext());
					this.getEventListener().remove(event);
					
					if(ce.getContext().getListening()){
						ContextEvent createWorker = new ContextEvent("", this, EventType.NEW_SERVER_SOCKET_SERVICE, false, ce.getContext());
						this.getEventDispatcher().put(createWorker);
					}
					
					event = null;
					break;
				case BALANCE_REQUEST:
					SocketContextEvent ssce = (SocketContextEvent) event;
					ConnectionContext next = this.balancingAct.nextConnection(ssce.getConnectionContext());
					SocketContextEvent createWorkerEvent = new SocketContextEvent(null, this, EventType.BALANCE_RESPONSE, 
							false,ssce.getSocket(), next);
					this.getEventListener().remove(event);
					event = null;
					this.getEventDispatcher().put(createWorkerEvent);
					break;
				default:
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
