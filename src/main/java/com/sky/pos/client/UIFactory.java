package com.sky.pos.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

public class UIFactory {

	public static JButton createButton(Action action) {
		JButton but = new JButton(action);
		but.putClientProperty("JComponent.sizeVariant", "large");
		return but;
	}
	
	public static JTextField createTextField(String value) {
		JTextField text = new JTextField(value);
		text.setColumns(10);
		text.putClientProperty("JComponent.sizeVariant", "large");
		return text;
	}
	
	public static JLabel createLabel(String value) {
		JLabel label = new JLabel(value, JLabel.RIGHT);
		return label;
	}
	
	public static JXDatePicker createDatePicker(Date date) {
		JXDatePicker picker = new JXDatePicker(date);
		SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );
		picker.setFormats(format);
		return picker;
	}
	
}
