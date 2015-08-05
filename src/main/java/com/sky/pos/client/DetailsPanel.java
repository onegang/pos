package com.sky.pos.client;

import java.awt.GridLayout;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import com.sky.pos.domain.SalesSummary;

public class DetailsPanel extends JPanel {

	private JXDatePicker datePicker;
	private JTextField grossSalesText;

	private SalesSummary sales;

	public DetailsPanel(SalesSummary sales) {
		this.sales = sales;
		init();
	}

	private void init() {
		datePicker = UIFactory.createDatePicker(new Date());
		grossSalesText = UIFactory.createTextField("");

		GridLayout gridLayout = new GridLayout(0, 2);
		this.setLayout(gridLayout);
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		this.add(UIFactory.createLabel("Date (dd/mm/yyyy)*:"));
		this.add(datePicker);
		this.add(UIFactory.createLabel("Sales Amount*:"));
		this.add(grossSalesText);
	}

	private double getDouble(JTextField field) {
		String value = field.getText().trim();
		if (value.length() == 0)
			return SalesSummary.EMPTY;
		else
			return Double.parseDouble(value);
	}

	public void save() throws ValidationException {
		this.validateInputs();

		this.sales.setSaleDate(datePicker.getDate());
		this.sales.setGrossSales(getDouble(grossSalesText));
	}

	private void validateInputs() throws ValidationException {
		if(datePicker.getDate() == null) {
			throw new ValidationException("Date is needed!");
		}
		validateNumber(grossSalesText, true, "Sales Amount");
	}

	private void validateNumber(JTextField field, boolean compulsory,
			String fieldName) throws ValidationException {
		String value = field.getText().trim();
		if (value.length() == 0) {
			if (compulsory) {
				throw new ValidationException(fieldName + " is needed!");
			}
		} else {
			try {
				Double.parseDouble(value);
			} catch (NumberFormatException ex) {
				throw new ValidationException(fieldName
						+ " must be a number!");
			}
		}
	}

}
