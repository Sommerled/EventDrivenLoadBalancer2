package eventHandler;

import java.util.List;

import events.Event;

/**
 * The interface for listening for events
 * on the event queue.
 */
public interface EventListener {
	
	/**
	 * Returns a list of all events that tester.TestType 
	 * declares to have a valid <EventType> and <null>
	 * if no valid events were found.
	 * 
	 * @param tester
	 * @return
	 * @throws InterruptedException 
	 */
	public List<Event> getAllEvents(EventTypeTester tester) throws InterruptedException;

}
