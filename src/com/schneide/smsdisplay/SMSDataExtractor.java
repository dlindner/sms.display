package com.schneide.smsdisplay;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class SMSDataExtractor {

	public SMSDataExtractor() {
		super();
	}

	public String extractFrom(InputStream smsDataStream) throws IOException {
		final StringBuilder result = new StringBuilder();
		int current = -1;
		boolean separatorInserted = false;
		while ((current = smsDataStream.read()) != -1) {
			char currentChar = (char) current;
			if (Character.isLetterOrDigit(currentChar) || Character.isWhitespace(currentChar) || isPunctuation(currentChar)) {
				result.append(currentChar);
				separatorInserted = false;
			} else {
				if (!separatorInserted) {
					result.append("|");
				}
				separatorInserted = true;
			}
		}
		return result.toString();
	}

	protected boolean isPunctuation(char character) {
		return Pattern.matches("\\p{Punct}", String.valueOf(character));
	}
}
