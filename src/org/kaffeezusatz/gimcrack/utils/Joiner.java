package org.kaffeezusatz.gimcrack.utils;
import java.util.Arrays;
import java.util.Collection;

public class Joiner {
	private String joiner;
	private boolean ignoreNull = false;
	
	private Joiner(String joiner) {
		this.joiner = joiner;
	}
	
	public Joiner ignoreNull() {
		this.ignoreNull = true;
		return this;
	}
	
	public static Joiner on(String joiner) {
		return new Joiner(joiner);
	}
	
	public String join(Collection<?> parts) {
		StringBuilder joined = new StringBuilder();
		
		String part = "";
		for (Object object : parts) {
			if (object == null && ignoreNull) {
				continue;
			}
			
			if (joined.length() > 0 && !part.endsWith(this.joiner)) {
				joined.append(this.joiner);
			}
			
			part = object.toString();
			part = removeLeadingJoiner(part);
			
			joined.append(part);
		}
		
		return joined.toString();
	}
	
	public String join(Object... parts) {
		return join(Arrays.asList(parts));
	}
	
	private String removeLeadingJoiner(String part) {
		if (part.startsWith(this.joiner)) {
			return part.substring(this.joiner.length());
		}
		
		return part;
	}
}
