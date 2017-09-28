package eventHandler;

public interface EventDispatcherAware {
	public EventDispatcher getEventDispatcher();
	public void setEventDispatcher(EventDispatcher eventDispatcher);
}
