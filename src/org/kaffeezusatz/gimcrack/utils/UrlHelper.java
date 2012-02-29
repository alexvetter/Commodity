package org.kaffeezusatz.gimcrack.utils;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author
 */
public class UrlHelper {

	public static URL createUrl(String url) {
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	public static URL getUrl(Object... parts) {
		return getUrl(Protocol.http, parts);
	}
	
	public static URL getUrl(Protocol protocol, Object... parts) {
		return createUrl(addProtocol(Joiner.on("/").setIgnoreNull().join(parts), protocol));
	}
	
	public enum Protocol {
		http, https, ftp
	}

	public static String addProtocol(String url) {
		return addProtocol(url, Protocol.http);
	}

	public static String addProtocol(String url, Protocol protocol) {
		Pattern regex = Pattern.compile("^([a-z]*)?://", Pattern.CASE_INSENSITIVE);

		Matcher matcher = regex.matcher(url);

		if (matcher.find()) {
			String currentProtocol = matcher.group(0);
			if (currentProtocol.equals("http://") && protocol == Protocol.https) {
				//convertion from http to https is fine
				url = url.replace("http://", "");
			} else if (currentProtocol.equals("https://") && protocol == Protocol.http) {
				//we should not make https to http
				return url;
			} else if (!currentProtocol.equals(protocol.toString()+"://")) {
				throw new IllegalArgumentException("url already has a protocol");
			} else {
				return url;
			}
		}
		
		return protocol.toString() + "://" + url;
	}
}
