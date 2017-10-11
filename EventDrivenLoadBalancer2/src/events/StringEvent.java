package events;

public class StringEvent extends Event{
	private final String msg;
	
	public StringEvent(String id, Object originator, EventType et, boolean broadcast, String msg) {
		super(id, originator, et, broadcast);
		this.msg = msg;
	}
	
	public String getValue(){
		return this.msg;
	}

}
