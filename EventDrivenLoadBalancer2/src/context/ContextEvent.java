package context;

import events.Event;
import events.EventType;

public class ContextEvent extends Event{
	private final ConnectionContext cc;
	
	public ContextEvent(String id, Object originator, EventType et, ConnectionContext cc) {
		super(id, originator, et);
		this.cc = cc;
	}

	public ConnectionContext getContext(){
		return this.cc;
	}
}
