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
	private JTextField openReceiptText;
	private JTextField closeReceiptText;
	private JTextField totalReceiptText;
	private JTextField grossSalesText;
	private JTextField taxText;
	private JTextField miscTaxText;
	private JTextField svrChargeText;
	private JTextField miscChargeText;
	private JTextField discountText;
	private JTextField roundingText;
	private JTextField netSalesText;

	private SalesSummary sales;

	public DetailsPanel(SalesSummary sales) {
		this.sales = sales;
		init();
	}

	private void init() {
		datePicker = UIFactory.createDatePicker(new Date());
		openReceiptText = UIFactory.createTextField("");
		closeReceiptText = UIFactory.createTextField("");
		totalReceiptText = UIFactory.createTextField("");
		grossSalesText = UIFactory.createTextField("");
		taxText = UIFactory.createTextField("");
		miscTaxText = UIFactory.createTextField("");
		svrChargeText = UIFactory.createTextField("");
		miscChargeText = UIFactory.createTextField("");
		discountText = UIFactory.createTextField("");
		roundingText = UIFactory.createTextField("");
		netSalesText = UIFactory.createTextField("");

		GridLayout gridLayout = new GridLayout(0, 2);
		this.setLayout(gridLayout);
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		this.add(UIFactory.createLabel("Date (dd/mm/yyyy)*:"));
		this.add(datePicker);
		this.add(UIFactory.createLabel("Open Receipt*:"));
		this.add(openReceiptText);
		this.add(UIFactory.createLabel("Close Receipt*:"));
		this.add(closeReceiptText);
		this.add(UIFactory.createLabel("Total Receipt*:"));
		this.add(totalReceiptText);
		this.add(UIFactory.createLabel("Gross Sales*:"));
		this.add(grossSalesText);
		this.add(UIFactory.createLabel("Tax:"));
		this.add(taxText);
		this.add(UIFactory.createLabel("Misc Tax:"));
		this.add(miscTaxText);
		this.add(UIFactory.createLabel("Service Charge:"));
		this.add(svrChargeText);
		this.add(UIFactory.createLabel("Misc Charge:"));
		this.add(miscChargeText);
		this.add(UIFactory.createLabel("Discount:"));
		this.add(discountText);
		this.add(UIFactory.createLabel("Rounding Amount:"));
		this.add(roundingText);
		this.add(UIFactory.createLabel("Net Sales*:"));
		this.add(netSalesText);
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
		this.sales.setOpenReceipt(openReceiptText.getText().trim());
		this.sales.setCloseRecipt(closeReceiptText.getText().trim());
		this.sales.setTotalRecipt(Integer.parseInt(totalReceiptText.getText().trim()));
		this.sales.setGrossSales(getDouble(grossSalesText));
		this.sales.setTax(getDouble(taxText));
		this.sales.setMiscTax(getDouble(miscTaxText));
		this.sales.setSvrCharge(getDouble(svrChargeText));
		this.sales.setMiscCharge(getDouble(miscChargeText));
		this.sales.setDiscount(getDouble(discountText));
		this.sales.setRoundingAmt(getDouble(roundingText));
		this.sales.setNetSales(getDouble(netSalesText));
	}

	private void validateInputs() throws ValidationException {
		if(datePicker.getDate() == null) {
			throw new ValidationException("Date is needed!");
		}
		if (openReceiptText.getText().trim().length() == 0) {
			throw new ValidationException("Open Receipt is needed!");
		}
		if (closeReceiptText.getText().trim().length() == 0) {
			throw new ValidationException("Close Receipt is needed!");
		}
		validateNumber(totalReceiptText, true, "Total Receipt");
		validateNumber(grossSalesText, true, "Gross Sales");
		validateNumber(netSalesText, true, "Net Sales");
		validateNumber(taxText, false, "Tax");
		validateNumber(miscTaxText, false, "Misc Charge");
		validateNumber(svrChargeText, false, "Service Charge");
		validateNumber(miscChargeText, false, "Misc Charge");
		validateNumber(discountText, false, "Discount");
		validateNumber(roundingText, false, "Rounding");
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
