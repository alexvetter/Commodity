package org.kaffeezusatz.commodity.utils;

public class ServletUtils {

	private ServletUtils() {
		//util class
	}

	public static String getServletPath(String contextPath, Object... parts) {
		Object[] args = new Object[parts.length + 1];
		int i = 0;
		
		args[i] = contextPath;
		for (Object part : parts) {
			args[++i] = part;
		}
		
		return Joiner.removeTrailingJoiner(UrlHelper.getUrl(args).toString(), "/");
	}
	
	public static String removeContextPath(String contextPath, String url) {
        if (url.startsWith(contextPath) && (!"/".equals(contextPath))) {
            url = url.substring(contextPath.length());        
        }
        
        return Joiner.removeTrailingJoiner(url, "/");
    }
}
