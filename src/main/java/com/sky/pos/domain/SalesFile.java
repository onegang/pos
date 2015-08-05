package com.sky.pos.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class SalesFile {
	
	private static final String PREFIX = "d";
	
	private static final String DELIMITER = "|";
	
	private static final String FILE_DELIMITER = "_";
	
	private static final String FILE_EXTENSION = ".txt";

	private SalesSummary sales;
	
	public SalesFile(SalesSummary sales) {
		this.sales = sales;
	}
	
	public String getFileName() {
		StringBuffer buf = new StringBuffer();
		buf.append(PREFIX);
		buf.append(sales.getTenantID());
		buf.append(FILE_DELIMITER);
		buf.append(sales.getPosNo());
		buf.append(FILE_DELIMITER);
		buf.append(sales.getTranFileNo());
		buf.append(FILE_DELIMITER);
		buf.append(sales.getFileDate());
		buf.append(FILE_EXTENSION);
		return buf.toString();
	}
	
	public File toFile(File target) throws IOException {
		StringBuffer buf = new StringBuffer();
		buf.append(sales.getTenantID());
		buf.append(DELIMITER);
		buf.append(sales.getPosNo());
		buf.append(DELIMITER);
		buf.append(sales.getSaleDate());
		buf.append(DELIMITER);
		buf.append(sales.getTranFileNo());
		buf.append(DELIMITER);
		buf.append(sales.getOpenReceipt());
		buf.append(DELIMITER);
		buf.append(sales.getCloseRecipt());
		buf.append(DELIMITER);
		buf.append(sales.getTotalRecipt());
		buf.append(DELIMITER);
		buf.append(toString(sales.getGrossSales(), false));
		buf.append(DELIMITER);
		buf.append(toString(sales.getTax(), true));
		buf.append(DELIMITER);
		buf.append(toString(sales.getMiscTax(), true));
		buf.append(DELIMITER);
		buf.append(toString(sales.getSvrCharge(), true));
		buf.append(DELIMITER);
		buf.append(toString(sales.getMiscCharge(), true));
		buf.append(DELIMITER);
		buf.append(toString(sales.getDiscount(), true));
		buf.append(DELIMITER);
		buf.append(toString(sales.getRoundingAmt(), false));
		buf.append(DELIMITER);
		buf.append(toString(sales.getNetSales(), false));
		
		target.getParentFile().mkdirs();
		PrintWriter pw = new PrintWriter(new FileWriter(target));
		pw.println(buf.toString());
		pw.close();
		return target;
	}
	
	private String toString(double value, boolean omitNull) {
		if(value == SalesSummary.EMPTY) {
			return "";
		} if(omitNull && value == 0) {
			return "";
		} else {
			DecimalFormat f = new DecimalFormat("#0.00");
			return f.format(value);
		}
	}
	
}
