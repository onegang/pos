package com.sky.pos.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class SalesFile {
	
	private static final String PREFIX = "D";
	
	private static final String FILE_DELIMITER = ".";
	
	private SalesSummary sales;
	
	public SalesFile(SalesSummary sales) {
		this.sales = sales;
	}
	
	public String getFileName() {
		StringBuffer buf = new StringBuffer();
		buf.append(PREFIX);
		buf.append(sales.getTenant().getId());
		buf.append(FILE_DELIMITER);
		buf.append(sales.getTransactionNo());
		return buf.toString();
	}
	
	public File toFile(File target) throws IOException {
		StringBuffer buf = new StringBuffer();
		buf.append(PREFIX);
		buf.append(sales.getTenant().getId());
		buf.append(sales.getSaleDate());
		buf.append(String.format("%011.2f", sales.getGrossSales()));
		
		target.getParentFile().mkdirs();
		PrintWriter pw = new PrintWriter(new FileWriter(target));
		pw.println(buf.toString());
		pw.close();
		return target;
	}
	
}
