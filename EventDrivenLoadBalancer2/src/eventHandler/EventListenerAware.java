package eventHandler;
/**
 * Allows for a class to have a reference
 * to an <EventListener>.
 */
public interface EventListenerAware {
	/**
	 * Returns the <EventListener>
	 * @return
	 */
	public EventListener getEventListener();
	
	/**
	 * Sets the <EventListener>
	 * @param eventListener
	 */
	public void setEventListener(EventListener eventListener);
	
}
