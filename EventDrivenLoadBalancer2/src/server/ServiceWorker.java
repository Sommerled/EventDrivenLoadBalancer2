package server;

import java.util.Iterator;
import java.util.List;

import eventHandler.EventDispatcher;
import eventHandler.EventListener;
import eventHandler.EventTypeTester;
import events.Event;
import events.EventType;
import events.StringEvent;

public abstract class ServiceWorker extends Service{
	private EventTypeTester tester = null;
	private final String ID;
	
	public ServiceWorker(EventListener listener, EventDispatcher dispatcher, EventTypeTester tester, String id) {
		super(listener, dispatcher);
		this.ID = id;
		this.tester = tester;
	}
	
	@Override
	public void run() {
		List<Event> events = null;
		
		while(true){
			try {
				events = this.getEventListener().getAllEvents(tester);
				if(events != null){
					Iterator<Event> iter = events.iterator();
					while(iter.hasNext()){
						Event event = iter.next();
						if(event.getEventType().equals(EventType.THREAD_PING)){
							StringEvent es = (StringEvent)event;
							if(ID.equals(es.getValue())){
								StringEvent response = new StringEvent("",this, EventType.THREAD_PING,false, this.ID);
								this.getEventDispatcher().put(response);
								es.setOriginator(null);
								es = null;
								event = null;
							}else{
								this.getEventDispatcher().put(event);
							}
						}else{
							process(event);
							event.setOriginator(null);
							event = null;
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getID(){
		return this.ID;
	}
	
	/**
	 * Processes events
	 * @param e
	 */
	protected abstract void process(Event e);
}
