package context;

import events.Event;
import events.EventType;

/**
 * The event object for sending <ConnectionContext>s to
 * the event queue.
 */
public class ContextEvent extends Event{
	private final ConnectionContext cc;
	
	public ContextEvent(String id, Object originator, EventType et, boolean broadcast, ConnectionContext cc) {
		super(id, originator, et, broadcast);
		this.cc = cc;
	}

	public ConnectionContext getContext(){
		return this.cc;
	}
}
