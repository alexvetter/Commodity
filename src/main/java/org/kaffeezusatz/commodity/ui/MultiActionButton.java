package org.kaffeezusatz.commodity.ui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;

public class MultiActionButton extends JButton {
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> actions;
	private String defaultAction;
	
	public MultiActionButton(Object defaultAction, String defaultText) {
		this.actions = new HashMap<String, String>();
		this.defaultAction = String.valueOf(defaultAction);
		
		addAction(String.valueOf(defaultAction), defaultText);
		defaultAction();
	}
	
	public MultiActionButton addAction(Object internal, String text) {
		actions.put(String.valueOf(internal), text);
		
		return this;
	}
	
	public MultiActionButton switchAction(Object internal) {
		setActionCommand(String.valueOf(internal));
		setText(actions.get(String.valueOf(internal)));
		
		return this;
	}
	
	public MultiActionButton defaultAction() {
		switchAction(defaultAction);
		
		return this;
	}
}
