package com.schneide.smsdisplay;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.schneide.smsdisplay.util.SectionableInputStream;

public class SMSDataExtractor {

	private static final int INITIAL_OFFSET = 356;
	private static final int NUMBER_TEXT_DISTANCE = 5002;
	private static final int SMS_DISTANCE = 13323;
	private static final int NUL = 0;

	public SMSDataExtractor() {
		super();
	}

	public Iterable<SMS> extractFrom(InputStream smsDataStream) throws IOException {
		final List<SMS> result = new ArrayList<>();
		final SectionableInputStream input = new SectionableInputStream(smsDataStream);
		input.skip(INITIAL_OFFSET);
		while (input.hasNext()) {
			int startPosition = input.position();
			String number = readTextFrom(input);
			input.skipFrom(startPosition, NUMBER_TEXT_DISTANCE);
			String message = readTextFrom(input);
			result.add(new ExtractedSMS(number, message));
			input.skipFrom(startPosition, SMS_DISTANCE);
		}
		return result;
	}

	private String readTextFrom(SectionableInputStream stream) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int current = -1;
		while ((current = stream.nextByte()) != -1) {
			if (NUL == current) {
				break;
			}
			buffer.write(current);
		}
		return new String(buffer.toByteArray(), Charset.forName("utf-8"));
	}

	private static class ExtractedSMS implements SMS {
		private final String sender;
		private final String message;

		public ExtractedSMS(String sender, String message) {
			super();
			this.sender = sender;
			this.message = message;
		}

		@Override
		public String message() {
			return this.message;
		}

		@Override
		public String sender() {
			return this.sender;
		}

		@Override
		public String toString() {
			return sender() + ": " + message();
		}
	}

	protected boolean isPunctuation(char character) {
		return Pattern.matches("\\p{Punct}", String.valueOf(character));
	}
}
