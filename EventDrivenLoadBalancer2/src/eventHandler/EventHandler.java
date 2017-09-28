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
		
		while(size() == 0){
			this.wait();
		}
		
		return this.eventQueue.peek();
	}
	
	public synchronized int size(){
		return this.eventQueue.size();
	}

	@Override
	public boolean remove(Event e) {
		return this.eventQueue.remove(e);
	}
	
	
}
