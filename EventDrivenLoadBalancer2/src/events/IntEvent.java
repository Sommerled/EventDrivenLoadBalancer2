package events;

public class IntEvent extends Event{
	private final int num;
	
	public IntEvent(String id, Object originator, EventType et, boolean broadcast, int num) {
		super(id, originator, et, broadcast);
		this.num = num;
	}

	public int getValue(){
		return this.num;
	}
}
