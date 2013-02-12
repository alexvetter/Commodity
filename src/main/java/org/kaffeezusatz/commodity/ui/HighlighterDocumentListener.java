package org.kaffeezusatz.commodity.ui;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class HighlighterDocumentListener implements DocumentListener {

	final Set<String> highlightWords = new HashSet<String>();

	DefaultHighlighter.DefaultHighlightPainter highlightPainter;
	Highlighter highlighter;

	public HighlighterDocumentListener(Highlighter highlighter, Color color) {
		this.highlighter = highlighter;
		this.highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(color);
	}
	
	public HighlighterDocumentListener(Highlighter highlighter) {
		this(highlighter, Color.YELLOW);
	}

	public Highlighter getHighlighter() {
		return highlighter;
	}

	public void setHighlighter(Highlighter highlighter) {
		this.highlighter = highlighter;
	}

	protected void highlightWords(final String text) {
		getHighlighter().removeAllHighlights();
		
		for (final String word : highlightWords) {
			if (!text.contains(word)) {
				continue;
			}

			int pos = 0;
			while ((pos = text.indexOf(word, pos)) >= 0) {
				final int b = pos;

				try {
					getHighlighter().addHighlight(b, pos + word.length(), highlightPainter);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
				
				pos += word.length();
			}
		}
	}

	public void addHighlight(String... words) {
		for (String word : words) {
			highlightWords.add(word);
		}
	}
	
	public void removeHighlight(String... words) {
		for (String word : words) {
			highlightWords.remove(word);
		}
	}

	public void insertUpdate(DocumentEvent e) {
		changedUpdate(e);
	}

	public void removeUpdate(DocumentEvent e) {
		changedUpdate(e);
	}

	public void changedUpdate(final DocumentEvent e) {
		final Runnable r = new Runnable() {
			public void run() {
				try {
					highlightWords(e.getDocument().getText(0, e.getDocument().getLength()));
				} catch (BadLocationException ignore) {
					//should not happen
				}
			} 
		};
		
		SwingUtilities.invokeLater(r);
	}
}
