package com.schneide.smsdisplay.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class SectionableInputStream {

	private final InputStream original;
	private final List<Integer> peeked;
	private int givenOut;

	public SectionableInputStream(InputStream original) {
		super();
		this.original = original;
		this.peeked = new LinkedList<>();
		this.givenOut = 0;
	}

	public boolean hasNext() throws IOException {
		int next = fetchByte();
		if (-1 == next) {
			return false;
		}
		this.peeked.add(next);
		return true;
	}

	public int nextByte() throws IOException {
		this.givenOut++;
		if (this.peeked.isEmpty()) {
			return fetchByte();
		}
		return this.peeked.remove(0);
	}

	public int position() {
		return this.givenOut;
	}

	public void skipFrom(int position, int byteCount) throws IOException {
		skip(byteCount - (position() - position));
	}

	public void skip(int byteCount) throws IOException {
		for (int i = 0; i < byteCount; i++) {
			nextByte();
		}
	}

	protected int fetchByte() throws IOException {
		return this.original.read();
	}
}
