package main;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.Font;

public class Column extends JPanel {

	/**
	 * Create the panel.
	 */
	JTextPane headerName;
	JCheckBox ignoreBox;
	JRadioButton classBtn;
	
	public Column() {
		setLayout(null);
		
		classBtn = new JRadioButton("Set Main Class");
		classBtn.setHorizontalAlignment(SwingConstants.CENTER);
		classBtn.setBounds(6, 75, 109, 23);
		add(classBtn);
		
		ignoreBox = new JCheckBox("Ignore");
		ignoreBox.setHorizontalAlignment(SwingConstants.CENTER);
		ignoreBox.setBounds(6, 101, 97, 23);
		add(ignoreBox);
		
		JTextPane txtpnId = new JTextPane();
		txtpnId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtpnId.setEditable(false);
		txtpnId.setText("ID");
		txtpnId.setBounds(6, 11, 100, 23);
		add(txtpnId);
		
		headerName = new JTextPane();
		headerName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		headerName.setEditable(false);
		headerName.setBounds(6, 45, 103, 23);
		add(headerName);

	}
}
