package eventHandler;

import events.Event;

/**
 * The interface for listening for events
 * on the event queue.
 */
public interface EventListener {
	/**
	 * Checks what the top event on the event queue is
	 * @return
	 * @throws InterruptedException
	 */
	public Event peek() throws InterruptedException;
	
	/**
	 * Removes an event off of the event queue
	 * @param e
	 * @return
	 */
	public boolean remove(Event e);

}
