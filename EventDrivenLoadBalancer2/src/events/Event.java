package events;

public class Event {
	private final Object originator;
	private final String id;
	private final EventType et; //phone home
	
	public Event(String id, Object originator, EventType et){
		this.id = id;
		this.originator = originator;
		this.et = et;
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
}
