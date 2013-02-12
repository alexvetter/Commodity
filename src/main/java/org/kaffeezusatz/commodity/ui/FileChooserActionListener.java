package org.kaffeezusatz.commodity.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.text.JTextComponent;

public class FileChooserActionListener implements ActionListener {
	
	private final JFileChooser fc = new JFileChooser();
	
	private Component parent;
	private JTextComponent field;
	
	private File file;
	
	public FileChooserActionListener(final Component parent, final JTextComponent field) {
		this.parent = parent;
		this.field = field;
	}
	
	public void setFileSelectionMode(int mode) {
		fc.setFileSelectionMode(mode);
	}
	
	public void setDirectory(File directory) {
		if (directory.exists() && directory.isDirectory()) {
			fc.setSelectedFile(directory);
			file = directory;
			field.setText(directory.getAbsolutePath());
		}
	}
	
	public void actionPerformed(final ActionEvent e) {
		//In response to a button click:
		int returnVal = fc.showOpenDialog(parent);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            field.setText(file.getAbsolutePath());
        } else {
        	//
        	file = null;
        }
	}
	
	public File getSelectedFile() {
		return file;
	}
}