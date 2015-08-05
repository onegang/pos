package com.sky.pos.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SalesSummary {
	
	public static final double EMPTY = Double.MAX_VALUE;

	private Tenant tenant;
	
	private POS pos;
	
	private Date saleDate;
	
	private String openReceipt;
	
	private String closeRecipt;
	
	private int totalRecipt;
	
	private double grossSales;
	
	private double tax;
	
	private double miscTax;
	
	private double svrCharge;
	
	private double miscCharge;
	
	private double discount;
	
	private double roundingAmt;
	
	private double netSales;
	
	public SalesSummary(Tenant tenant, POS pos) {
		this.tenant = tenant;
		this.pos = pos;
		totalRecipt = 0;
		grossSales = EMPTY;
		tax = EMPTY;
		miscTax = EMPTY;
		svrCharge = EMPTY;
		miscCharge = EMPTY;
		discount = EMPTY;
		roundingAmt = EMPTY;
		netSales = EMPTY;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public POS getPos() {
		return pos;
	}

	public String getTenantID() {
		return tenant.getId();
	}

	public String getPosNo() {
		return pos.getId();
	}

	public String getSaleDate() {
		return new SimpleDateFormat("yyyyMMdd").format(saleDate);
	}
	
	public String getFileDate() {
		Date fileDate = new Date();
		fileDate.setDate(saleDate.getDate());
		fileDate.setMonth(saleDate.getMonth());
		fileDate.setYear(saleDate.getYear());
		return new SimpleDateFormat("yyMMddHHmm").format(fileDate);
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public int getTranFileNo() {
		return pos.getTransactionNo();
	}

	public String getOpenReceipt() {
		return openReceipt;
	}

	public void setOpenReceipt(String openReceipt) {
		this.openReceipt = openReceipt;
	}

	public String getCloseRecipt() {
		return closeRecipt;
	}

	public void setCloseRecipt(String closeRecipt) {
		this.closeRecipt = closeRecipt;
	}

	public int getTotalRecipt() {
		return totalRecipt;
	}

	public void setTotalRecipt(int totalRecipt) {
		this.totalRecipt = totalRecipt;
	}

	public double getGrossSales() {
		return grossSales;
	}

	public void setGrossSales(double grossSales) {
		this.grossSales = grossSales;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getMiscTax() {
		return miscTax;
	}

	public void setMiscTax(double miscTax) {
		this.miscTax = miscTax;
	}

	public double getSvrCharge() {
		return svrCharge;
	}

	public void setSvrCharge(double svrCharge) {
		this.svrCharge = svrCharge;
	}

	public double getMiscCharge() {
		return miscCharge;
	}

	public void setMiscCharge(double miscCharge) {
		this.miscCharge = miscCharge;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getRoundingAmt() {
		return roundingAmt;
	}

	public void setRoundingAmt(double roundingAmt) {
		this.roundingAmt = roundingAmt;
	}

	public double getNetSales() {
		return netSales;
	}

	public void setNetSales(double netSales) {
		this.netSales = netSales;
	}
	
}
