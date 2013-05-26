package com.schneide.smsdisplay;

import java.io.File;

public class Main {

	public static void main(String[] args) throws Exception {
		File smsDataFile = new File("D:/Programme/Mobile Partner/userdata/SMS.DTC");
		final SMSProvider smsProvider = new MobilePartnerSMSProvider(smsDataFile);
		final GUI gui = new GUI();
		final Engine engine = new Engine(smsProvider, gui);
		engine.start();
	}
}
