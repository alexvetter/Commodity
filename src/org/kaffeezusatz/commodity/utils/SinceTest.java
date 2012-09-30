package org.kaffeezusatz.commodity.utils;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class SinceTest {

	@Test
	public final void test() throws ParseException {
		Date now = new Date();
		
		assertEquals("Now", new Since(new Date()).toString());
		assertEquals("one second ago", new Since(new Date(now.getTime() - 1000)).toString());
		assertEquals("30 seconds ago", new Since(new Date(now.getTime() - 30 * 1000)).toString());
		
		assertEquals("one minute ago", new Since(new Date(now.getTime() - 60 * 1000)).toString());
		assertEquals("2 minutes ago", new Since(new Date(now.getTime() - 120 * 1000)).toString());
		assertEquals("4 minutes ago", new Since(new Date(now.getTime() - 240 * 1000)).toString());
		
		assertEquals("one hour ago", new Since(new Date(now.getTime() - 3600 * 1000)).toString());
		assertEquals("2 hours ago", new Since(new Date(now.getTime() - 2 * 3600 * 1000)).toString());
		assertEquals("5 hours ago", new Since(new Date(now.getTime() - 5 * 3600 * 1000)).toString());
		
		assertEquals("Yesterday", new Since(new Date(now.getTime() - 24 * 3600 * 1000)).toString());
		assertEquals("2 days ago", new Since(new Date(now.getTime() - 2 * 24 * 3600 * 1000)).toString());
		assertEquals("5 days ago", new Since(new Date(now.getTime() - 5 * 24 * 3600 * 1000)).toString());
		
		assertEquals("one week ago", new Since(new Date(now.getTime() - 7 * 24 * 3600 * 1000)).toString());
		
		String defaultFormat = "yyyy-MM-dd HH:mm";
		SimpleDateFormat format = new SimpleDateFormat(defaultFormat);
		String dateString = new Since(new Date(now.getTime() - 16 * 24 * 3600 * 1000)).toString();
		
		System.out.println(dateString);
		
		Date date = format.parse(dateString);
		
		String diffString = new Since(new Date(date.getTime() + 16 * 24 * 3600 * 1000)).toString();
		
		if (!diffString.endsWith("seconds ago")) {
			fail();
		}
	}

}
