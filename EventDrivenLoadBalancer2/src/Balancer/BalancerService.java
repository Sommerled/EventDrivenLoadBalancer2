package Balancer;

import context.ConnectionContext;
import context.ContextEvent;
import context.DoubleContextEvent;
import eventHandler.EventDispatcher;
import eventHandler.EventListener;
import events.Event;
import events.EventType;
import server.Service;
import server.ServiceWorker;
import server.socketWorkers.SocketContextEvent;
/**
 * This service uses the <Balancer> class
 * to register new <ConnectionContext>s and
 * to get the next <ConnectionContext> that
 * will handle directing traffic for a new
 * connection.
 */
public class BalancerService extends ServiceWorker{
	private final Balancer balancingAct; //Ha ha
	
	public BalancerService(EventListener listener, EventDispatcher dispatcher) {
		super(listener, dispatcher, 
				(EventType phoneHome)->{
					return (phoneHome.equals(EventType.NEW_CONNECTION) || 
							phoneHome.equals(EventType.BALANCE_REQUEST));
				}
		);
		this.balancingAct = new Balancer();
	}

	@Override
	protected void process(Event e) {
		try {			
			switch(e.getEventType()){
			case NEW_CONNECTION:
				ContextEvent ce = (ContextEvent)e;
				this.balancingAct.addConnection(ce.getContext());
				
				if(ce.getContext().getListening()){
					ContextEvent createWorker = new ContextEvent("", this, EventType.NEW_SERVER_SOCKET_SERVICE, false, ce.getContext());
					this.getEventDispatcher().put(createWorker);
				}
				break;
			case BALANCE_REQUEST:
				SocketContextEvent ssce = (SocketContextEvent) e;
				ConnectionContext next = this.balancingAct.nextConnection(ssce.getConnectionContext());
				SocketContextEvent createWorkerEvent = new SocketContextEvent(null, this, EventType.BALANCE_RESPONSE, 
						false,ssce.getSocket(), next);
				this.getEventDispatcher().put(createWorkerEvent);
				break;
			default:
				break;
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}finally{
			e = null;
		}
	}

}
