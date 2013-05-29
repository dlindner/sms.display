package com.schneide.smsdisplay.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import com.schneide.smsdisplay.model.SMS;
import com.schneide.smsdisplay.model.SMSProvider;

public class MobilePartnerSMSProvider implements SMSProvider {

	private final File smsDataFile;

	public MobilePartnerSMSProvider(File smsDataFile) {
		super();
		this.smsDataFile = smsDataFile;
	}

	@Override
	public Iterable<SMS> currentSMS() {
		try (InputStream input = new FileInputStream(smsDataFile)) {
			return new SMSDataExtractor().extractFrom(input);
		} catch (IOException e) {
			return Collections.emptyList();
		}
	}
}
