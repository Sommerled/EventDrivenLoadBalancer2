package eventHandler;

import events.Event;

public interface EventDispatcher {
	public void put(Event e) throws InterruptedException;
}
