package org.kaffeezusatz.commodity.utils;
import static org.junit.Assert.*;

import org.junit.Test;


public class JoinerTest {

	@Test
	public void testJoin() {
		assertEquals("a.b.c.d", Joiner.on(".").join("a", "b", "c.", ".d"));
		assertEquals("a.b.c.d.", Joiner.on(".").join("a", "b", "c.", ".d."));
		assertEquals("http://google.de/test/Hello", Joiner.on("/").join("http://", "google.de", "test", "Hello"));
	}
	
	@Test
	public void testNull() {
		assertEquals("", Joiner.on(".").setIgnoreNull().join(null, null, null));
		assertEquals("null.null.null", Joiner.on(".").setNullString("null").join(null, null, null));
	}
}
