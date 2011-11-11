package org.kaffeezusatz.gimcrack.html.htmltag;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

public class HtmlTag {
		
	private String tag;
	private Map<String, String> attributes;
	private StringBuffer value;
	private boolean close;
	
	public HtmlTag(String name) {
		this.tag = name;
		this.attributes = new HashMap<String, String>();
		this.value = new StringBuffer();
		this.close = false;
	}
	
	public String getTag() {
		return tag;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public String getValue() {
		return value.toString();
	}
	
	public boolean isClosed() {
		return this.close;
	}
	
	private void checkClosed() {
		if (this.close) {
			throw new HtmlTagAlreadyClosedException();
		}
	}

	public void addAttribute(String name, String value) {
		checkClosed();
		
		this.attributes.put(name, value);
	}
	
	public void removeAttribute(String name) {
		checkClosed();
		
		this.attributes.remove(name);
	}
	
	public void addAttributes(Map<String, String> m) {
		checkClosed();
		
		this.attributes.putAll(m);
	}
	
	public void setValue(String value) {
		checkClosed();
		
		this.value = new StringBuffer(StringEscapeUtils.escapeHtml(value));
	}
	
	public void appendValue(String value) {
		checkClosed();
		
		this.value.append(value);
	}
	
	public void setValue(HtmlTag value) {
		checkClosed();
		
		this.value = new StringBuffer(value.toString());
	}
	
	public void appendValue(HtmlTag value) {
		checkClosed();
		
		this.value.append(value.toString());
	}
	
	public void close() {
		this.close = true;
	}
	
	
	public String toString() {
		if (!isClosed()) {
			throw new HtmlTagIsNotClosedException();
		}
		
		StringBuffer r = new StringBuffer();
		
		r.append('<');
		r.append(this.tag);
		r.append(' ');
		
		for (String name : this.attributes.keySet()) {
			r.append(name);
			r.append('=');
			r.append('"');
			r.append(this.attributes.get(name));
			r.append('"');
			r.append(' ');
		}
		
		if (value.toString().isEmpty()) {
			r.append('/');
		} else {
			r.append('>');
			r.append(value.toString());
			r.append('<');
			r.append('/');
			r.append(this.tag);
		}
		
		r.append('>');
		
		return r.toString();
	}
}
