package com.schneide.smsdisplay;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

public class GUI {

	private final JWindow window;
	private final JLabel smsLabel;
	private final JLabel backgroundImage;


	public GUI() throws Exception {
		super();
		this.window = new JWindow();
		final BufferedImage background = ImageIO.read(ClassLoader.getSystemResourceAsStream("com/schneide/smsdisplay/rahmen.jpg"));
		this.backgroundImage = new JLabel(new ImageIcon(background));
		this.smsLabel = new JLabel();
		EventQueue.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				window.getContentPane().setLayout(null);
				smsLabel.setOpaque(false);
				smsLabel.setFont(new Font("Verdana", Font.PLAIN, 45));
				smsLabel.setVerticalTextPosition(SwingConstants.TOP);
				smsLabel.setVerticalAlignment(SwingConstants.TOP);
				smsLabel.setHorizontalTextPosition(SwingConstants.LEADING);
				window.getContentPane().add(smsLabel);
				window.getContentPane().add(backgroundImage);
				backgroundImage.setLocation(0, 0);
				backgroundImage.setSize(1280, 720);
				smsLabel.setLocation(160, 110);
				smsLabel.setSize(640, 450);
			}
		});
	}

	public void show() throws Exception {
		EventQueue.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				GraphicsDevice device = ge.getDefaultScreenDevice();
				device.setFullScreenWindow(window);
				window.setVisible(true);
			}
		});
	}

	public void display(final String text) throws Exception {
		EventQueue.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				smsLabel.setText("<html>" + text + "</html>");
				window.revalidate();
			}
		});
	}
}
