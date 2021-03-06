package com.schneide.smsdisplay;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.schneide.smsdisplay.gui.GUI;
import com.schneide.smsdisplay.model.SMS;
import com.schneide.smsdisplay.model.SMSProvider;

public class Engine {

	private final SMSProvider smsSource;
	private final GUI gui;
	private final BlockingQueue<SMS> pendingSMS;
	private final Set<SMS> processed;

	public Engine(SMSProvider smsSource, GUI gui) {
		super();
		this.smsSource = smsSource;
		this.gui = gui;
		this.pendingSMS = new LinkedBlockingQueue<>();
		this.processed = new HashSet<>();
	}

	public void start() {
		try {
			this.gui.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		startConsumer();
		startProducer();
		System.out.println("Started producer and consumer thread");
	}

	protected void startProducer() {
		Thread producer = new Thread() {
			public void run() {
				while (true) {
					Iterable<SMS> allSMS = smsSource.currentSMS();
					for (SMS each : allSMS) {
						if (!processed.contains(each)) {
							schedule(each);
						}
					}
					sleepFor(5000L);
				}
			}
		};
		producer.start();
	}

	public void schedule(SMS sms) {
		try {
			pendingSMS.put(sms);
			processed.add(sms);
			System.out.println("added " + sms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void startConsumer() {
		Thread consumer = new Thread() {
			public void run() {
				while (true) {
					try {
						SMS current = pendingSMS.take();
						gui.display(current.message());
					} catch (Exception e) {
						e.printStackTrace();
					}
					sleepFor(15000L);
				}
			}
		};
		consumer.start();
	}

	protected void sleepFor(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
