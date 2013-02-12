package org.kaffeezusatz.commodity.ui;

import java.util.ResourceBundle;

import javax.swing.JFrame;

public abstract class MainApplication extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ResourceBundle locale;

	public MainApplication() {
		locale = getResourceBundle();
	}
	
	public abstract void buildGui();
	public abstract void showGui();
	protected abstract ResourceBundle getResourceBundle();
	
	public final String getLocaleString(final String key) {
		if (key == null) {
			throw new IllegalArgumentException("key can't be null");
		}
		
		try {
			return locale.getString(key);
		} catch (Exception e) {
			return key;
		}
	}
}