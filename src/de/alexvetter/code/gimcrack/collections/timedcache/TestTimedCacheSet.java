package de.alexvetter.code.gimcrack.collections.timedcache;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

/**
 * 
 */
public class TestTimedCacheSet extends TestCase {
	public void test() throws InterruptedException {
		Set<String> cs = Collections.synchronizedSet(new TimedCacheSet<String>(100));
		
		assertTrue(cs.add("hallo0"));
		assertTrue(cs.add("hallo1"));
		assertTrue(cs.add("hallo2"));
		assertTrue(cs.add("hallo3"));
		assertTrue(cs.add("hallo4"));
		assertFalse(cs.add("hallo4"));
		
		assertEquals(5, cs.size());
		
		assertTrue(cs.contains("hallo0"));
		assertFalse(cs.contains("hallo8"));
		assertTrue(cs.contains("hallo2"));
		assertTrue(cs.contains("hallo4"));
		assertTrue(cs.contains("hallo3"));
		
		Thread.sleep(100);
		
		assertFalse(cs.contains("hallo3"));
		assertFalse(cs.contains("hallo5"));
		
		assertEquals(0, cs.size());
		
		cs = null;
	}
	
	public void testTransforming() throws InterruptedException {
		Set<String> s = Collections.synchronizedSet(new HashSet<String>());
		
		assertTrue(s.add("hallo0"));
		assertTrue(s.add("hallo1"));
		assertTrue(s.add("hallo2"));
		assertTrue(s.add("hallo3"));
		assertTrue(s.add("hallo4"));
		assertFalse(s.add("hallo4"));
		
		Set<String> cs = Collections.synchronizedSet(new TimedCacheSet<String>(s,100));
		
		assertTrue(cs.containsAll(s));
		
		Thread.sleep(100);
		
		assertFalse(cs.containsAll(s));
		
		cs = null;
		s = null;
	}	
	
	public void testAliveReset() throws InterruptedException {
		Set<String> cs = Collections.synchronizedSet(new TimedCacheSet<String>(100));
		
		assertTrue(cs.add("hallo0"));
		assertTrue(cs.add("hallo1"));
		assertTrue(cs.add("hallo2"));
		assertTrue(cs.add("hallo3"));
		assertTrue(cs.add("hallo4"));
		
		Thread.sleep(95);
		
		assertTrue(cs.size() == 5);
		
		Thread.sleep(200);
		
		assertTrue(cs.size() == 0);
		
		cs = null;
	}
	
	public void testIterator() throws InterruptedException {
		Set<String> cs = Collections.synchronizedSet(new TimedCacheSet<String>(100));
		
		cs.add("hallo0");
		cs.add("hallo1");
		cs.add("hallo2");
		cs.add("hallo3");
		cs.add("hallo4");

		assertFalse(cs.isEmpty());
		
		assertTrue(cs.remove("hallo0"));
		
		assertTrue(cs.removeAll(cs));
		
		assertEquals(0, cs.size());
		
		cs.add("hallo0");
		cs.add("hallo1");
		cs.add("hallo2");
		cs.add("hallo3");
		cs.add("hallo4");
		
		Thread.sleep(100);
		
		assertTrue(cs.isEmpty());
		
		cs = null;
	}
}