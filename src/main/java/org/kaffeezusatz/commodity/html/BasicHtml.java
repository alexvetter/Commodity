package org.kaffeezusatz.commodity.html;

public class BasicHtml {

	private HtmlTag root;
	private HtmlTag head;
	private HtmlTag body;

	public BasicHtml() {
		this.root = new HtmlTag("html");
		this.head = new HtmlTag("head");
		this.body = new HtmlTag("body");
		
		this.root.appendValue(head).appendValue(body);
	}

	public HtmlTag getHead() {
		return head;
	}

	public HtmlTag getBody() {
		return body;
	}
	
	public BasicHtml addHeadTag(HtmlTag tag) {
		getHead().appendValue(tag);
		return this;
	}
	
	public BasicHtml addBodyTag(HtmlTag tag) {
		getBody().appendValue(tag);
		return this;
	}
	
	public BasicHtml setTitle(String title) {
		addHeadTag(new HtmlTag("title").setValue(title));
		return this;
	}
	
	public String toString() {
		return this.root.toString();
	}
}
