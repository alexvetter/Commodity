package de.alexvetter.code.gimcrack.html.htmltag;

public class HtmlTagIsNotClosedException extends RuntimeException {
	
	private static final long serialVersionUID = 6795526863948495215L;

	public HtmlTagIsNotClosedException() {
	}

	public HtmlTagIsNotClosedException(String message) {
		super(message);
	}

	public HtmlTagIsNotClosedException(Throwable cause) {
		super(cause);
	}

	public HtmlTagIsNotClosedException(String message, Throwable cause) {
		super(message, cause);
	}

}
