package org.kaffeezusatz.commodity.html;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

public class HtmlTag {
	private Map<String, String> attributes;
	private String name;
	private StringBuffer value;
	
	public HtmlTag(String name) {
		this.name = name;
		this.attributes = new HashMap<String, String>();
		this.value = new StringBuffer();
	}
	
	public HtmlTag addAttribute(String name, String value) {
		this.attributes.put(name, value);
		return this;
	}

	public HtmlTag setAttributes(Map<String, String> m) {
		this.attributes.clear();
		this.attributes.putAll(m);
		return this;
	}

	public HtmlTag appendValue(HtmlTag value) {
		this.value.append(value.toString());
		return this;
	}

	public HtmlTag appendValue(String value) {
		this.value.append(value);
		return this;
	}
	
	public Map<String, String> getAttributes() {
		return attributes;
	}
	
	public String getName() {
		return name;
	}
	
	public String getValue() {
		return value.toString();
	}
	
	public void removeAttribute(String name) {
		this.attributes.remove(name);
	}
	
	public HtmlTag setValue(HtmlTag value) {
		this.value = new StringBuffer(value.toString());
		return this;
	}
	
	public HtmlTag setValue(String value) {
		this.value = new StringBuffer(StringEscapeUtils.escapeHtml4(value));
		return this;
	}
	
	@Override
	public String toString() {
		final StringBuffer r = new StringBuffer();
		
		r.append('<');
		r.append(this.name);
		
		final Iterator<String> i = this.attributes.keySet().iterator();
		String att = null;
		if (i.hasNext()) {
			r.append(' ');
		}
		while (i.hasNext()) {
			att = i.next();
			
			r.append(att);
			r.append('=');
			r.append('"');
			r.append(this.attributes.get(att));
			r.append('"');
			
			if (i.hasNext()) {
				r.append(' ');
			}
		}
		
		if (value.toString().isEmpty()) {
			r.append(' ');
			r.append('/');
		} else {
			r.append('>');
			r.append(value.toString());
			r.append('<');
			r.append('/');
			r.append(this.name);
		}
		
		r.append('>');
		
		return r.toString();
	}
}
