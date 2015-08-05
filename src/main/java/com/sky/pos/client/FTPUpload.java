package com.sky.pos.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.pos.domain.Settings;

public class FTPUpload {

	private static Logger LOGGER = LoggerFactory.getLogger(FTPUpload.class);

	private String hostname;

	private int port;

	private String userid;

	private String password;

	public FTPUpload(String hostname, int port, String userid, String password) {
		super();
		this.hostname = hostname;
		this.port = port;
		this.userid = userid;
		this.password = password;
	}

	public FTPUpload(Settings settings) {
		hostname = settings.getHostname();
		port = settings.getPort();
		userid = settings.getUsername();
		password = settings.getPassword();
	}

	public boolean testConnection() throws IOException {
		FTPClient ftp = new FTPClient();
		ftp.connect(hostname, port);
		boolean login = ftp.login(userid, password);
		ftp.disconnect();
		return login;
	}

	public void upload(File file) throws IOException {
		FTPClient ftp = new FTPClient();
		FileInputStream input = new FileInputStream(file);
		try {
			ftp.connect(hostname, port);
			boolean login = ftp.login(userid, password);
			if (login) {
				LOGGER.info("Login to FTP");

				ftp.setFileType(FTP.ASCII_FILE_TYPE);
				ftp.storeFile(file.getName(), input);
				LOGGER.info("Transeferred file {} to FTP", file.getName());
			} else {
				LOGGER.info("Fail to login to FTP");
			}
		} finally {
			input.close();
		}
		ftp.disconnect();
	}
}
