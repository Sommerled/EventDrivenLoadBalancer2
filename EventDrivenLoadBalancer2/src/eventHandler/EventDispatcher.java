package eventHandler;

import events.Event;

/**
 * The interface for creating dispatchers
 * for placing events on the event queue
 */
public interface EventDispatcher {
	
	/**
	 * Puts and event on the event queue
	 * @param e
	 * @throws InterruptedException
	 */
	public void put(Event e) throws InterruptedException;
}
