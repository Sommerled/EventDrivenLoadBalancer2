package server;

import eventHandler.EventDispatcher;
import eventHandler.EventListener;
import events.Event;
import events.EventType;
import events.IntEvent;
import events.StringEvent;
import events.StringListEvent;

/**
 * The service for using the <IDGenerator> object
 */
public class IDGenerationService extends ServiceWorker{
	private IDGenerator generator = null;

	public IDGenerationService(EventListener listener, EventDispatcher dispatcher, String ID, IDGenerator generator) {
		super(listener, dispatcher, 
				(EventType phoneHome)->{
					return (phoneHome.equals(EventType.NEW_ID) || 
							phoneHome.equals(EventType.BATCH_IDS)||
							phoneHome.equals(EventType.FREED_ID));
				},
			ID
		);
		
		this.generator = generator;
	}

	@Override
	protected void process(Event e) {
		Event response = null;
		try {
			switch(e.getEventType()){
			case NEW_ID:
				response = new StringEvent(null, this, EventType.RESPONSE_ID, false, this.generator.getFreeID());
				this.getEventDispatcher().put(response);
				break;
			case BATCH_IDS:
				int num = ((IntEvent)e).getValue();
				response = new StringListEvent(null, this, EventType.RESPONSE_ID, false, this.generator.getBatchIDs(num));
				this.getEventDispatcher().put(response);
				break;
			case FREED_ID:
				String freedID = ((StringEvent)e).getValue();
				this.generator.addFreeID(freedID);
				break;
			default:
				break;
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	
}
