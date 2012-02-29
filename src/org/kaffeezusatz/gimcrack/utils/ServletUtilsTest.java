package org.kaffeezusatz.gimcrack.utils;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class ServletUtilsTest {

	private static String contextPath;

	@BeforeClass
	public static void beforeClass() {
		contextPath = "https://desw138x/siemens/";
	}
	
	@Test
	public void testGetServletPath() {
		assertEquals("https://desw138x/siemens/bootstrapping/JnlpBrowser", ServletUtils.getServletPath(contextPath, "bootstrapping", "JnlpBrowser"));
		assertEquals("https://desw138x/siemens/bootstrapping/JnlpBrowser", ServletUtils.getServletPath(contextPath, "/bootstrapping/", "/JnlpBrowser/"));
	}
	
	@Test
	public void testRemoveContextPath() {
		assertEquals("bootstrapping/JnlpBrowser", ServletUtils.removeContextPath(contextPath, "https://desw138x/siemens/bootstrapping/JnlpBrowser"));
		assertEquals("bootstrapping", ServletUtils.removeContextPath(contextPath, "https://desw138x/siemens/bootstrapping/"));
	}

}
