package com.schneide.smsdisplay;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		File smsDataFile = new File("D:/Programme/Mobile Partner/userdata/SMS.DTC");
		try (InputStream input = new FileInputStream(smsDataFile)) {
			Iterable<SMS> sms = new SMSDataExtractor().extractFrom(input);
			for (SMS each : sms) {
				System.out.println(each);
			}
		}
	}
}
