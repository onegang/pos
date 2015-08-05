package com.sky.pos.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.pos.domain.SalesFile;
import com.sky.pos.domain.SalesSummary;
import com.sky.pos.domain.Settings;
import com.sky.pos.domain.Tenant;

//TODO package into standalone runnable
//TODO help menu
public class Application extends JFrame {

	private static final long serialVersionUID = 1L;

	private static Logger LOGGER = LoggerFactory.getLogger(Application.class);

	private Settings settings;

	private DetailsPanel detailsPane;

	private SalesSummary sales;

	public Application() {
		super("POS Sales Transfer");
		settings = new Settings(getSalesSummary());
		init();
	}

	private void loadSettings() {
		try {
			settings.load();
		} catch (Exception ex) {
			LOGGER.error("Error in loading settings", ex);
			JOptionPane.showMessageDialog(this, "Error in loading settings: "
					+ ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void init() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		try {
			boolean found = false;
			for (javax.swing.UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					found = true;
					break;
				}
			}
			if (!found) {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			}
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		} catch (UnsupportedLookAndFeelException e) {
		}
		initMenu();
		initPanel();
	}

	private void startTransfer() throws IOException {
		SalesSummary sales = getSalesSummary();
		SalesFile salesFile = new SalesFile(sales);
		File destFile = new File(settings.getPath() + "archive/"
				+ salesFile.getFileName());
		destFile = salesFile.toFile(destFile);
		LOGGER.info("Generated file: {}", destFile.getAbsolutePath());

		FTPUpload ftp = new FTPUpload(this.settings);
		ftp.upload(destFile);
	}

	private SalesSummary getSalesSummary() {
		if (sales == null) {
			Tenant tenant = new Tenant();
			sales = new SalesSummary(tenant);
		}

		return sales;
	}

	private void initPanel() {
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		detailsPane = new DetailsPanel(getSalesSummary());
		contentPane.add(detailsPane, BorderLayout.CENTER);
		contentPane.add(
				UIFactory.createButton(new AbstractAction("Transfer POS file") {

					private static final long serialVersionUID = 1L;

					public void actionPerformed(ActionEvent e) {
						((JButton) e.getSource()).setEnabled(false);
						try {
							detailsPane.save();
							startTransfer();
							incrementTransactionNo();
							JOptionPane.showMessageDialog(Application.this, "Sucessfully transferred file", "Success", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception ex) {
							LOGGER.error("Unable to initiate transfer", ex);
							JOptionPane.showMessageDialog(Application.this,
									"Unable to transfer file: " + ex.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						} finally {
							((JButton) e.getSource()).setEnabled(true);
						}
					}

				}), BorderLayout.SOUTH);

		getContentPane().add(contentPane, BorderLayout.CENTER);
	}

	private void incrementTransactionNo() throws FileNotFoundException {
		int transactionNo = settings.getTransactionNo();
		transactionNo++;
		if(transactionNo > 999)
			transactionNo = 0;
		settings.setTransactionNo(transactionNo);
		settings.save();
	}

	private void initMenu() {
		JPanel toolBar = new JPanel();
		toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.LINE_AXIS));
		toolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JButton configItem = UIFactory.createButton(new AbstractAction(
				"Settings") {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				ConfigSettingsDialog dialog = new ConfigSettingsDialog(
						Application.this, settings);
				SwingUtilities.updateComponentTreeUI(dialog);
				dialog.pack();
				dialog.setLocationRelativeTo(Application.this);
				dialog.setVisible(true);
			}

		});
		
		JButton helpItem = UIFactory.createButton(new AbstractAction(
				"Help") {

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog(Application.this, "Help");
				JTextArea help = new JTextArea();
				try {
					help.read(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("help.txt")), help);
				} catch (FileNotFoundException ex) {
					LOGGER.error("Unable to read help file", ex);
				} catch (IOException ex) {
					LOGGER.error("Unable to read help file", ex);
				}
				help.setEditable(false);
				help.setLineWrap(true);
				help.setWrapStyleWord(true);
				dialog.setContentPane(new JScrollPane(help));
				dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				dialog.setSize(new Dimension(400, 400));
				dialog.setLocationRelativeTo(Application.this);
				dialog.setVisible(true);
			}

		});
		
		configItem.setAlignmentX(Component.RIGHT_ALIGNMENT);
		helpItem.setAlignmentX(Component.RIGHT_ALIGNMENT);

		toolBar.add(configItem);
		toolBar.add(helpItem);
		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	public static void main(String[] args) {
		try {
			Application app = new Application();
			app.setSize(400, 200);
			app.setLocationRelativeTo(null);
			SwingUtilities.updateComponentTreeUI(app);
			app.setVisible(true);
			app.loadSettings();
		} catch (Exception ex) {
			LOGGER.error("Error in running", ex);
		}

	}

}
