package testing.IDGenerator;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import eventHandler.EventHandler;
import events.Event;
import events.EventType;
import events.StringEvent;
import server.IDGenerationService;
import server.IDGenerator;

public class TestIDGenerationService {

	@Test
	public void testGet1ID() {
		EventHandler handler = new EventHandler();
		IDGenerationService idgs = new IDGenerationService(handler, handler, null, new IDGenerator());
		Thread serviceThread = new Thread(idgs);
		serviceThread.start();
		int size = 0;
		Event event = null;
		
		try {
			handler.put(new Event(null, this, EventType.NEW_ID,false));
			List<Event> events = handler.getAllEvents((EventType phoneHome)->{
				return (phoneHome.equals(EventType.RESPONSE_ID));
			});
			
			size = events.size();
			event = events.get(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(TestIDGenerationService.class + ".testGet1ID");
		assertTrue(size == 1 && event instanceof StringEvent);
		serviceThread.interrupt();
		handler.setRelease(true);
	}



	@Test
	public void testGet10ID() {
		EventHandler handler = new EventHandler();
		IDGenerationService idgs = new IDGenerationService(handler, handler, null, new IDGenerator());
		Thread serviceThread = new Thread(idgs);
		serviceThread.start();
		int size = 0;
		Event event = null;
		
		try {
			handler.put(new Event(null, this, EventType.NEW_ID,false));
			List<Event> events = handler.getAllEvents((EventType phoneHome)->{
				return (phoneHome.equals(EventType.RESPONSE_ID));
			});
			
			size = events.size();
			event = events.get(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(TestIDGenerationService.class + ".testGet1ID");
		assertTrue(size == 1 && event instanceof StringEvent);
		serviceThread.interrupt();
		handler.setRelease(true);
	}
}
