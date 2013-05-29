package com.schneide.smsdisplay.model;


public interface SMSProvider {

	public Iterable<SMS> currentSMS();
}
