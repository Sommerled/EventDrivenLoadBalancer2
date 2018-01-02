package server;

import java.util.Iterator;
import java.util.List;

import eventHandler.EventDispatcher;
import eventHandler.EventListener;
import eventHandler.EventTypeTester;
import events.Event;

public abstract class ServiceWorker extends Service{
	private EventTypeTester tester = null;
	
	public ServiceWorker(EventListener listener, EventDispatcher dispatcher, EventTypeTester tester) {
		super(listener, dispatcher);
		
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
						process(iter.next());
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected abstract void process(Event e);

}
