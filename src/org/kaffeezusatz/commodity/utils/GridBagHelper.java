package org.kaffeezusatz.commodity.utils;

import java.awt.Component;
import java.awt.GridBagConstraints;

import javax.swing.JComponent;

public class GridBagHelper {
	private JComponent comp;
	public GridBagConstraints gbc;
	
	public GridBagHelper(JComponent comp, GridBagConstraints gbc) {
		this.comp = comp;
		this.gbc = gbc;
	}
	
	public GridBagHelper(JComponent comp) {
		this(comp, new GridBagConstraints());
	}
	
	public GridBagConstraints getConstraints() {
		return gbc;
	}
	
	public GridBagHelper addRow() {
		getConstraints().gridy++;
		getConstraints().gridx = 1;
		
		return this;
	}
	
	public void add(Component... comps) {
		for (Component component : comps) {
			comp.add(component, getConstraints());
			getConstraints().gridx++;
		}
	}

}
