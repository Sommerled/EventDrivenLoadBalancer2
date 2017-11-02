package context;

import events.Event;
import events.EventType;

/**
 * An event for passing two <ConnectionContext>
 * objects to the event queue.
 */
public class DoubleContextEvent extends Event{
	private final ConnectionContext c1;
	private final ConnectionContext c2;
	
	public DoubleContextEvent(String id, Object originator, EventType et, boolean broadcast, ConnectionContext c1, ConnectionContext c2) {
		super(id, originator, et, broadcast);
		this.c1 = c1;
		this.c2 = c2;
	}

	public ConnectionContext getContext1(){
		return this.c1;
	}
	
	public ConnectionContext getContext2(){
		return this.c2;
	}
}
