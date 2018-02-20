package testing.IDGenerator;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import server.IDGenerator;

public class TestIDGenerator {

	@Test
	public void testInitialization() {
		IDGenerator idg = new IDGenerator();
		String newID = idg.getFreeID();
		
		System.out.println(TestIDGenerator.class + ".testInitialization");
		assertTrue(newID.length() == 64);
	}
	
	@Test
	public void testBatch0_Size() {
		IDGenerator idg = new IDGenerator();
		List<String> batch = idg.getBatchIDs(0);
		
		System.out.println(TestIDGenerator.class + ".testBatch0_Size");
		assertTrue(batch.size() == 0);
	}
	
	@Test
	public void testBatch1_Size() {
		IDGenerator idg = new IDGenerator();
		List<String> batch = idg.getBatchIDs(1);
		
		System.out.println(TestIDGenerator.class + ".testBatch1_Size");
		assertTrue(batch.size() == 1);
	}
	
	@Test
	public void testBatch10_Size() {
		IDGenerator idg = new IDGenerator();
		List<String> batch = idg.getBatchIDs(10);
		
		System.out.println(TestIDGenerator.class + ".testBatch10_Size");
		assertTrue(batch.size() == 10);
	}
	
	@Test
	public void testBatch1000000_Size() {
		IDGenerator idg = new IDGenerator();
		
		Long time1 = System.currentTimeMillis();
		List<String> batch = idg.getBatchIDs(1000000);
		Long time2 = System.currentTimeMillis();
		
		Long difference = time2 - time1;
		double seconds = difference / 1000.0;
		
		System.out.println(TestIDGenerator.class + ".testBatch1000000_Size");
		System.out.println("Seconds to create: " + seconds);
		assertTrue(batch.size() == 1000000);
	}
	
	@Test
	public void testBatch5000000_Size() {
		IDGenerator idg = new IDGenerator();
		
		Long time1 = System.currentTimeMillis();
		List<String> batch = idg.getBatchIDs(5000000);
		Long time2 = System.currentTimeMillis();
		
		Long difference = time2 - time1;
		double seconds = difference / 1000.0;
		
		System.out.println(TestIDGenerator.class + ".testBatch5000000_Size");
		System.out.println("Seconds to create: " + seconds);
		assertTrue(batch.size() == 5000000);
	}
	
	@Test
	public void testFreedID() {
		IDGenerator idg = new IDGenerator();
		String id1 = idg.getFreeID();
		
		idg.addFreeID(id1);
		String id2 = idg.getFreeID();
		
		System.out.println(TestIDGenerator.class + ".testFreedID");
		assertTrue(id1.equals(id2));
	}
}
