package com.schneide.smsdisplay;

import java.io.File;

import com.schneide.smsdisplay.database.MobilePartnerSMSProvider;
import com.schneide.smsdisplay.gui.GUI;
import com.schneide.smsdisplay.model.SMSProvider;

public class Main {

	public static void main(String[] arguments) throws Exception {
		String smsDataFilePath = "D:/Programme/Mobile Partner/userdata/SMS.DTC";
		if (arguments.length > 0) {
			smsDataFilePath = arguments[0];
		}
		String backgroundImagePath = "rahmen.jpg";
		if (arguments.length > 1) {
			backgroundImagePath = arguments[1];
		}
		final File smsDataFile = new File(smsDataFilePath);
		final SMSProvider smsProvider = new MobilePartnerSMSProvider(smsDataFile);
		final GUI gui = new GUI(backgroundImagePath);
		final Engine engine = new Engine(smsProvider, gui);
		engine.start();
	}
}
