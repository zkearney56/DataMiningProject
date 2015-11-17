package main.gui;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.EmptyBorder;

public class Column extends JPanel {

	/**
	 * Create the panel.
	 */
	JTextPane headerName;
	JCheckBox ignoreBox;
	JRadioButton classBtn;
	int colIndex;
	
	public Column(String head, int colIndex) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);
		setPreferredSize(new Dimension(125,135));
		this.colIndex = colIndex;
		classBtn = new JRadioButton("Set Main Class");
		classBtn.setHorizontalAlignment(SwingConstants.CENTER);
		classBtn.setBounds(6, 75, 109, 23);
		add(classBtn);
		
		ignoreBox = new JCheckBox("Ignore");
		ignoreBox.setHorizontalAlignment(SwingConstants.CENTER);
		ignoreBox.setBounds(6, 101, 97, 23);
		add(ignoreBox);
		
		JTextPane txtpnId = new JTextPane();
		txtpnId.setBackground(SystemColor.menu);
		txtpnId.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnId.setEditable(false);
		txtpnId.setText("ID");
		txtpnId.setBounds(54, 11, 20, 23);
		add(txtpnId);
		
		headerName = new JTextPane();
		headerName.setBackground(SystemColor.controlShadow);
		headerName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		headerName.setEditable(false);
		headerName.setText(head);
		headerName.setBounds(6, 45, 109, 23);
		add(headerName);

	}
	
	public JRadioButton getRadio(){
		return classBtn;
	}
}
