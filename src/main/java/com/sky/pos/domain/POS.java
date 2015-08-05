package com.sky.pos.domain;

public class POS {

	private String id;
	
	private int transactionNo;
	
	public POS() {
	}

	public String getId() {
		return id;
	}
	
	public int getTransactionNo() {
		return transactionNo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTransactionNo(int transactionNo) {
		this.transactionNo = transactionNo;
	}
	
}
