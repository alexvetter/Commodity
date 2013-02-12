package org.kaffeezusatz.commodity.html;

import static org.junit.Assert.*;

import org.junit.Test;

public class HtmlTagTest {

	@Test
	public final void testHtmlTag() {
		HtmlTag html = new HtmlTag("html");
		
		assertEquals("<html />", html.toString());
	}

	@Test
	public final void testGetTag() {
		HtmlTag html = new HtmlTag("html");
		
		assertEquals("html", html.getName());
	}

	@Test
	public final void testGetAttributes() {
		HtmlTag html = new HtmlTag("html");
		html.addAttribute("test", "t");
		html.addAttribute("t", "test");
		
		assertEquals("t", html.getAttributes().get("test"));
		assertEquals("test", html.getAttributes().get("t"));
		
		assertEquals("<html t=\"test\" test=\"t\" />", html.toString());
	}

	@Test
	public final void testValue() {
		HtmlTag html = new HtmlTag("html").addAttribute("test", "test").appendValue(new HtmlTag("test").setValue("test"));
		
		assertEquals("<html test=\"test\"><test>test</test></html>", html.toString());
	}
}
