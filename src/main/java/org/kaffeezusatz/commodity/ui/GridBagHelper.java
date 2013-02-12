package org.kaffeezusatz.commodity.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;

public class GridBagHelper {
	private Container comp;
	
	public GridBagConstraints gbc;
	
	public GridBagHelper(Container comp, GridBagConstraints gbc) {
		this.comp = comp;
		this.gbc = gbc;
	}
	
	public GridBagHelper(Container comp) {
		this(comp, new GridBagConstraints());
		getConstraints().gridx = 0;
		getConstraints().gridy = 0;
	}
	
	public GridBagConstraints getConstraints() {
		return gbc;
	}
	
	public GridBagHelper addRow() {
		getConstraints().gridy++;
		getConstraints().gridx = 0;
		
		return this;
	}
	
	public GridBagHelper add(Component... comps) {
		for (Component component : comps) {
			comp.add(component, getConstraints());
			getConstraints().gridx++;
		}
		
		return this;
	}
}
