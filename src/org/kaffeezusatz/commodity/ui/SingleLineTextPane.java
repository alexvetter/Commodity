package org.kaffeezusatz.commodity.ui;

import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.StyledDocument;

public class SingleLineTextPane extends JTextPane {
	private static final long serialVersionUID = 1L;

	public SingleLineTextPane() {
		super();

		TransferFocus.patch(this);
		
		StyledDocument doc = this.getStyledDocument();

		if (doc instanceof AbstractDocument) {
			((AbstractDocument) doc).setDocumentFilter(new NewLineFilter());
		}
	}

	private class NewLineFilter extends DocumentFilter {
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
			fb.insertString(offset, string.replaceAll("\\n", ""), attr);
		}

		public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
			fb.insertString(offset, string.replaceAll("\\n", ""), attr);
		}
	}
}
