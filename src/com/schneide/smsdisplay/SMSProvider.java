package com.schneide.smsdisplay;

public interface SMSProvider {

	public Iterable<SMS> currentSMS();
}
