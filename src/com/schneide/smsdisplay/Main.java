package com.schneide.smsdisplay;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import com.schneide.smsdisplay.util.StringChunker;

public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
		File smsDataFile = new File("D:/Programme/Mobile Partner/userdata/SMS.DTC");
		while (true) {
			try (InputStream input = new FileInputStream(smsDataFile)) {
				String rawData = new SMSDataExtractor().extractFrom(input);
				//System.out.println(rawData);

				String[] tokens = rawData.split("\\|Q\\|");
				//StringChunker tokens = new StringChunker(rawData, "Q+49");
				for (String each : tokens) {
					System.out.println("--> " + each);
				}
				System.out.println("**********************************************************************");

			}
			Thread.sleep(1000L);
		}
	}

}
