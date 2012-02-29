package org.kaffeezusatz.gimcrack.utils;
import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;


public class UrlHelperTest extends UrlHelper {

	@Test
	public void testAddProtocol() throws MalformedURLException {
		assertEquals("http://test.com", addProtocol("test.com"));
		assertEquals("http://test.com", addProtocol("http://test.com"));
		assertEquals("https://test.com", addProtocol("test.com", Protocol.https));
		
		try {
		assertEquals("ftp://test.com", addProtocol("http://test.com", Protocol.ftp));
		} catch (IllegalArgumentException e) {
			//that's okay
		}
		
		assertEquals("https://test.com", addProtocol("http://test.com", Protocol.https));
		assertEquals("https://test.com", addProtocol("https://test.com", Protocol.http));
		
		assertEquals("http://test.com/context/servlet", getUrl(Protocol.http, "test.com", "context", "servlet").toString());
		assertEquals("http://test.com/siemens/servlet/", getUrl(Protocol.http, new URL("http://test.com/siemens/"), "/servlet/").toString());
	}
}
