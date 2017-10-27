package eventHandler;

import java.util.concurrent.LinkedBlockingQueue;

import events.Event;

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
	
	public synchronized int size(){
		return this.eventQueue.size();
	}

	@Override
	public boolean remove(Event e) {
		return this.eventQueue.remove(e);
	}
	
	
}
