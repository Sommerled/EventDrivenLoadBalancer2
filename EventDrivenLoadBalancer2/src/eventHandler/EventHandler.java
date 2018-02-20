package eventHandler;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import events.Event;

/**
 * This object stores events in a <LinkedBlockingQueue> 
 * until they are removed by services that are listening 
 * for events. 
 */
public class EventHandler implements EventDispatcher, EventListener {
	private List<Event> eventList = null;
	private boolean release = false;
	
	public EventHandler(){
		this.eventList = Collections.synchronizedList(new LinkedList<Event>()) ;
	}

	@Override
	public void put(Event e) throws InterruptedException {
		this.eventList.add(e);
		synchronized(this){
			this.notifyAll();
		}
	}
	
	/**
	 * Returns the number of events on the
	 * <LinkedList>
	 * @return
	 */
	public synchronized int size(){
		return this.eventList.size();
	}

	@Override
	public synchronized List<Event> getAllEvents(EventTypeTester tester) throws InterruptedException {
		
		while(size() == 0){
				wait();
		}
		
		Iterator<Event> iter = this.eventList.iterator();
		List<Event> events = new LinkedList<Event>();
		
		if(!this.release){
			while(iter.hasNext()){
				Event e = iter.next();
				
				if(tester.TestType(e.getEventType())){
					events.add(e);
					iter.remove();
				}
			}
		}
		
		if(events.size() == 0){
			events = null;
		}
		
		return events;
	}
	
	public synchronized void setRelease(boolean r){
		this.release = r;
		this.notifyAll();
	}
}
