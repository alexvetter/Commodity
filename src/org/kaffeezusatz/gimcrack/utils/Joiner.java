package org.kaffeezusatz.gimcrack.utils;
import java.util.Arrays;
import java.util.Collection;

public class Joiner {
	private String joiner;
	private boolean ignoreNull = false;
	private boolean removeTrailingJoiner = false;
	
	private Joiner(String joiner) {
		this.joiner = joiner;
	}
	
	public Joiner setIgnoreNull() {
		this.ignoreNull = true;
		return this;
	}
	
	public Joiner setRemoveTrailingJoiner() {
		this.removeTrailingJoiner  = true;
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
			part = removeLeadingJoiner(part, this.joiner);
			
			joined.append(part);
		}
		
		if (removeTrailingJoiner) {
			return removeTrailingJoiner(joined.toString(), this.joiner);
		}
		
		return joined.toString();
	}
	
	public String join(Object... parts) {
		return join(Arrays.asList(parts));
	}
	
	public static String removeLeadingJoiner(String string, String joiner) {
		if (string.startsWith(joiner)) {
			return string.substring(joiner.length());
		}
		
		return string;
	}
	
	public static String removeTrailingJoiner(String string, String joiner) {
		if (string.endsWith(joiner)) {
			return string.substring(0, string.length() - joiner.length());
		}
		
		return string;
	}
}
