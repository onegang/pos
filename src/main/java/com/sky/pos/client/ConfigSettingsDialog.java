package com.sky.pos.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.pos.domain.Settings;

public class ConfigSettingsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private static Logger LOGGER = LoggerFactory
			.getLogger(ConfigSettingsDialog.class);

	private Settings settings;

	private JTextField hostnameText;
	private JTextField portText;
	private JTextField usernameText;
	private JTextField passwordText;
	private JTextField tenantIdText;
	private JTextField transactionNoText;
	private JTextField posIdText;

	public ConfigSettingsDialog(JFrame parent, Settings settings) {
		super(parent, "Configure Settings", true);
		this.settings = settings;
		init();
	}

	private JPanel toPane(Component comp, String label) {
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		pane.add(new JLabel(label), BorderLayout.WEST);
		pane.add(comp, BorderLayout.CENTER);
		return pane;
	}

	private void init() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		GridLayout gridLayout = new GridLayout(0, 2);
		JPanel contentPane = new JPanel();
		contentPane.setLayout(gridLayout);

		hostnameText = UIFactory.createTextField(settings.getHostname());
		portText = UIFactory
				.createTextField(String.valueOf(settings.getPort()));
		usernameText = UIFactory.createTextField(settings.getUsername());
		passwordText = UIFactory.createTextField(settings.getPassword());
		tenantIdText = UIFactory.createTextField(settings.getTenantId());
		posIdText = UIFactory.createTextField(settings.getPosId());
		transactionNoText = UIFactory.createTextField(String.valueOf(settings
				.getTransactionNo()));

		contentPane.add(UIFactory.createLabel("Hostname:"));
		contentPane.add(hostnameText);
		contentPane.add(UIFactory.createLabel("Port:"));
		contentPane.add(portText);
		contentPane.add(UIFactory.createLabel("Username:"));
		contentPane.add(usernameText);
		contentPane.add(UIFactory.createLabel("Password:"));
		contentPane.add(passwordText);
		contentPane.add(UIFactory.createLabel(""));
		contentPane.add(UIFactory.createButton(new AbstractAction(
				"Test FTP Connection") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				FTPUpload ftp = new FTPUpload(hostnameText.getText(), Integer
						.parseInt(portText.getText()), usernameText.getText(),
						passwordText.getText());
				try {
					boolean success = ftp.testConnection();
					if (success) {
						JOptionPane.showMessageDialog(
								ConfigSettingsDialog.this,
								"Connection success!", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane
								.showMessageDialog(
										ConfigSettingsDialog.this,
										"Unable to connect to FTP server. Please verify your settings. ",
										"Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (IOException ex) {
					LOGGER.error("Failed to connection FTP in test connection",
							ex);
					JOptionPane.showMessageDialog(ConfigSettingsDialog.this,
							"Unable to connect, please verify your settings: "
									+ ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		}));
		contentPane.add(UIFactory.createLabel("TenantID:"));
		contentPane.add(tenantIdText);
		contentPane.add(UIFactory.createLabel("POS ID:"));
		contentPane.add(posIdText);
		contentPane.add(UIFactory.createLabel("Transaction No:"));
		contentPane.add(transactionNoText);
		contentPane.add(UIFactory.createButton(new AbstractAction("OK") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(save()) {
					ConfigSettingsDialog.this.setVisible(false);
					ConfigSettingsDialog.this.dispose();
				}
			}

		}));
		contentPane.add(UIFactory.createButton(new AbstractAction("Cancel") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				ConfigSettingsDialog.this.setVisible(false);
				ConfigSettingsDialog.this.dispose();
			}

		}));

		contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		this.pack();
	}

	private boolean save() {
		try {
			validateInput();

			settings.setHostname(hostnameText.getText());
			settings.setPort(Integer.parseInt(portText.getText()));
			settings.setUsername(usernameText.getText());
			settings.setPassword(passwordText.getText());
			settings.setTenantId(tenantIdText.getText());
			settings.setPosId(posIdText.getText());
			settings.setTransactionNo(Integer.parseInt(transactionNoText
					.getText()));

			settings.save();
			return true;
		} catch (Exception ex) {
			LOGGER.error("Error in saving settings", ex);
			JOptionPane.showMessageDialog(this, "Unable to save settings: "
					+ ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	private void validateInput() throws ValidationException {
		if (tenantIdText.getText().trim().length() == 0) {
			throw new ValidationException("Tenant ID is needed!");
		}
		if (posIdText.getText().trim().length() == 0) {
			throw new ValidationException("POS ID is needed!");
		}
		validateNumber(transactionNoText, true, "Transaction Number");
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
				throw new ValidationException(fieldName + " must be a number!");
			}
		}
	}

}
