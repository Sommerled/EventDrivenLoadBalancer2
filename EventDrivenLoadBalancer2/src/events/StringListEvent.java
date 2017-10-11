package events;

import java.util.List;

public class StringListEvent extends Event{
	private final List<String> list;
	
	public StringListEvent(String id, Object originator, EventType et, boolean broadcast, List<String> list) {
		super(id, originator, et, broadcast);
		this.list = list;
	}
	
	public List<String> getValue(){
		return this.list;
	}
}
