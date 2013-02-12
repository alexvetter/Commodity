package org.kaffeezusatz.commodity.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Since {
	private Date since;
	private SimpleDateFormat format;

	public Since(Date since) {
		this(since, "yyyy-MM-dd HH:mm");
	}
	
	public Since(Date since, String format) {
		this.since = since;
		this.format = new SimpleDateFormat(format);
	}

	public Date getSince() {
		return since;
	}

	public Date getNow() {
		return new Date();
	}

	/**
	 * Returns difference between since and now in milliseconds.
	 * 
	 * @return difference in milliseconds
	 */
	public long getDiff() {
		return getSince().getTime() - getNow().getTime();
	}

	public String toString() {
		long diff = getDiff();
		// Difference in seconds
		long s = Math.abs(diff / 1000);

		long d = s / (3600 * 24);

		if (d == 0) {
			if (s == 0)
				return "Now";
			if (s == 1)
				return "one second ago";
			if (s < 60)
				return s + " seconds ago";
			if (s < 120)
				return "one minute ago";
			if (s < 3600)
				return (s / 60) + " minutes ago";
			if (s < 7200)
				return "one hour ago";
			if (s < 86400)
				return (s / 3600) + " hours ago";
		}
		if (d == 1)
			return "Yesterday";
		if (d < 7)
			return d + " days ago";
		if (d < 14)
			return "one week ago";
		
		return format.format(getSince());
	}
}
