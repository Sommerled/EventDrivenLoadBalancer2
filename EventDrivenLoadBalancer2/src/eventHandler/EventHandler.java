package eventHandler;

import java.util.concurrent.LinkedBlockingQueue;

import events.Event;

/**
 * This object stores events in a <LinkedBlockingQueue> 
 * until they are removed by services that are listening 
 * for events. 
 */
public class EventHandler implements EventDispatcher, EventListener {
	private LinkedBlockingQueue<Event> eventQueue = null;
	
	public EventHandler(){
		this.eventQueue = new LinkedBlockingQueue<Event>();
	}

	@Override
	public void put(Event e) throws InterruptedException {
		this.eventQueue.put(e);
		synchronized(this){
			this.notifyAll();
		}
	}

	@Override
	public Event peek() throws InterruptedException {
		Event e = null;
		
		while(e == null){
			while(size() == 0){
				synchronized(this){
					wait();
				}
			}
			e = this.eventQueue.peek();
		}
		
		return e;
	}
	
	/**
	 * Returns the number of events on the
	 * <LinkedBlockingQueue>
	 * @return
	 */
	public synchronized int size(){
		return this.eventQueue.size();
	}

	@Override
	public boolean remove(Event e) {
		return this.eventQueue.remove(e);
	}
	
	
}
