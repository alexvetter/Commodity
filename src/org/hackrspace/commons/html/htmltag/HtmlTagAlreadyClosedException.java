/**
 * 
 */
package org.hackrspace.commons.html.htmltag;

/**
 * @author alexandervetter
 *
 */
public class HtmlTagAlreadyClosedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6643692933596277375L;

	/**
	 * 
	 */
	public HtmlTagAlreadyClosedException() {
	}

	/**
	 * @param message
	 */
	public HtmlTagAlreadyClosedException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public HtmlTagAlreadyClosedException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public HtmlTagAlreadyClosedException(String message, Throwable cause) {
		super(message, cause);
	}

}
