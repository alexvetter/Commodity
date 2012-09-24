package org.kaffeezusatz.commodity.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.text.JTextComponent;

public class AppenderButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JTextComponent component;
	private Object text;

	public AppenderButton(JTextComponent component, String text) {
		super(text);
		this.component = component;
		this.text = text;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.component.setText(this.component.getText() + text);
	}
}
