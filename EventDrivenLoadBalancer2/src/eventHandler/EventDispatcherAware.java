package eventHandler;

/**
 * Classes that implements this interface
 * can use an <EventDispatcher>
 */
public interface EventDispatcherAware {
	/**
	 * For retrieving the an <EventDispatcher>
	 * @return
	 */
	public EventDispatcher getEventDispatcher();
	
	/**
	 * Sets the <EventDispatcher>
	 * @param eventDispatcher
	 */
	public void setEventDispatcher(EventDispatcher eventDispatcher);
}
