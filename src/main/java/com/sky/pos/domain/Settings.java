package com.sky.pos.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Properties;

public class Settings {
	
	private static final String DEFAULT_PATH = "C:/pos/data/";
	
	private static final String DEFAULT_HOSTNAME = "172.30.1.10";
	private static final int DEFAULT_PORT = 21;
	private static final String DEFAULT_USERNAME = "0700144";
	private static final String DEFAULT_PASSWORD = "7m2z7E8b";
	private static final String DEFAULT_TENANTID = "0700144";
	private static final int DEFAULT_TRANSACTIONNO = 0;

	private String hostname;
	
	private int port;
	
	private String username;
	
	private String password;
	
	private String tenantId;
	
	private int transactionNo;
	
	private SalesSummary sales;
	
	public Settings(SalesSummary sales) {
		this.sales = sales;
	}
	
	public String getPath() {
		return DEFAULT_PATH;
	}
	
	public void load() throws FileNotFoundException, IOException {
		File file = new File(DEFAULT_PATH + "settings.properties");
		if(file.exists()) {
			Properties prop = new Properties();
			prop.load(new FileInputStream(file));
			hostname = prop.getProperty("hostname");
			port = Integer.parseInt(prop.getProperty("port"));
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			tenantId = prop.getProperty("tenantId");
			transactionNo = Integer.parseInt(prop.getProperty("transactionNo"));
		} else {
			hostname = DEFAULT_HOSTNAME;
			port = DEFAULT_PORT;
			username = DEFAULT_USERNAME;
			password = DEFAULT_PASSWORD;
			tenantId = DEFAULT_TENANTID;
			transactionNo = DEFAULT_TRANSACTIONNO;
		}
		copyToSales();
	}
	
	private void copyToSales() {	
		sales.getTenant().setId(tenantId);
		sales.setTransactionNo(String.format("%03d", transactionNo));
	}
	
	public void save() throws FileNotFoundException {
		File file = new File(DEFAULT_PATH + "settings.properties");
		file.getParentFile().mkdirs();
		
		Properties prop = new Properties();
		prop.put("hostname", hostname);
		prop.put("port", String.valueOf(port));
		prop.put("username", username);
		prop.put("password", password);
		prop.put("tenantId", tenantId);
		prop.put("transactionNo", String.valueOf(transactionNo));
		String comments = "Updated on " + (new Date()).toString();
		prop.save(new FileOutputStream(file), comments);
		
		copyToSales();
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public int getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(int transactionNo) {
		this.transactionNo = transactionNo;
	}

}
