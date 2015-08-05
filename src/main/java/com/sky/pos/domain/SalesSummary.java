package com.sky.pos.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SalesSummary {
	
	public static final double EMPTY = Double.MAX_VALUE;

	private Tenant tenant;
	
	private Date saleDate;
		
	private double grossSales;
	
	private String transactionNo;

	
	public SalesSummary(Tenant tenant) {
		this.tenant = tenant;
		grossSales = EMPTY;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public String getSaleDate() {
		return new SimpleDateFormat("yyyyMMdd").format(saleDate);
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public double getGrossSales() {
		return grossSales;
	}

	public void setGrossSales(double grossSales) {
		this.grossSales = grossSales;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	

}
