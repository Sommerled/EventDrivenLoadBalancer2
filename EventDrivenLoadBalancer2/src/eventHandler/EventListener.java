package eventHandler;

import events.Event;

public interface EventListener {
	public Event peek() throws InterruptedException;
	public boolean remove(Event e);

}
