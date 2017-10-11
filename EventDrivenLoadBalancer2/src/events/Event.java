package events;

public class Event {
	private final Object originator;
	private final String id;
	private final EventType et; //phone home
	private final boolean broadcast;
	
	public Event(String id, Object originator, EventType et, boolean broadcast){
		this.id = id;
		this.originator = originator;
		this.et = et;
		this.broadcast = broadcast;
	}
	
	public String getId(){
		return this.id;
	}
	
	public Object getOriginator(){
		return this.getOriginator();
	}
	
	public EventType getEventType(){
		return this.et;
	}
	
	public boolean getBroadcast(){
		return this.broadcast;
	}
}
