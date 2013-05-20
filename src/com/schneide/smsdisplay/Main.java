package com.schneide.smsdisplay;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JLabel;
import javax.swing.JWindow;

public class Main {

	public static void main(String[] args) throws Exception {
		File smsDataFile = new File("D:/Programme/Mobile Partner/userdata/SMS.DTC");
		final Iterable<SMS> sms = loadSMSFrom(smsDataFile);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = ge.getDefaultScreenDevice();
		final JWindow window = new JWindow();
		device.setFullScreenWindow(window);
		window.setVisible(true);
		for (final SMS each : sms) {
			System.out.println(each);
			EventQueue.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					window.getContentPane().removeAll();
					window.getContentPane().add(asLabel(each));
					window.revalidate();
				}
			});
			Thread.sleep(15000L);
		}
	}

	private static JLabel asLabel(SMS sms) {
		JLabel result = new JLabel(sms.message());
		result.setFont(new Font("Verdana", Font.BOLD, 72));
		return result;
	}

	private static Iterable<SMS> loadSMSFrom(File dataFile) throws IOException {
		try (InputStream input = new FileInputStream(dataFile)) {
			return new SMSDataExtractor().extractFrom(input);
		}
	}
}
