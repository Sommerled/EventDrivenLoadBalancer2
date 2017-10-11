package server;

import eventHandler.EventDispatcher;
import eventHandler.EventListener;
import events.Event;
import events.EventType;
import events.IntEvent;
import events.StringEvent;
import events.StringListEvent;

public class IDGenerationService extends Service{
	private IDGenerator generator = null;

	public IDGenerationService(EventListener listener, EventDispatcher dispatcher) {
		super(listener, dispatcher);
		
		this.generator = new IDGenerator();
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Event request = this.getEventListener().peek();
				Event response = null;
				EventType et = request.getEventType();
				
				if(et == EventType.NEW_ID || et == EventType.BATCH_IDS || et == EventType.FREED_ID){
					if(this.getEventListener().remove(request)){
						switch(request.getEventType()){
						case NEW_ID:
							response = new StringEvent(null, this, EventType.RESPONSE_ID, false, this.generator.getFreeID());
							this.getEventDispatcher().put(response);
							break;
						case BATCH_IDS:
							int num = ((IntEvent)request).getValue();
							response = new StringListEvent(null, this, EventType.RESPONSE_ID, false, this.generator.getBatchIDs(num));
							this.getEventDispatcher().put(response);
							break;
						case FREED_ID:
							String freedID = ((StringEvent)request).getValue();
							this.generator.addFreeID(freedID);
							break;
						default:
							break;
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
}
