package main.gui;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * Class to print all output to a JTextComponent within a gui.
 * 
 * @author Zachary Kearney
 * 
 * Last Edited: 12/1/2015
 *
 */
public class GuiOutputStream extends OutputStream {
	private final JTextArea destination;

	public GuiOutputStream(JTextArea destination) {
		if (destination == null)
			throw new IllegalArgumentException("Destination is null");

		this.destination = destination;
	}

	@Override
	/**
	 * Instead of outputting to console, appends the JTextArea
	 */
	public void write(byte[] buffer, int offset, int length) throws IOException {
		final String text = new String(buffer, offset, length);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				destination.append(text);
			}
		});
	}

	@Override
	public void write(int b) throws IOException {
		write(new byte[] { (byte) b }, 0, 1);
	}

}
