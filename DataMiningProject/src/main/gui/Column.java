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
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Column extends JPanel {

	/**
	 * Create the panel.
	 */
	private JTextPane headerName;
	private JCheckBox ignoreBox;
	private JRadioButton classBtn;
	private String head;
	
	public Column(String head) {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);
		setPreferredSize(new Dimension(125, 138));
		this.head = head;
		classBtn = new JRadioButton("Set Main Class");
		classBtn.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					ignoreBox.setSelected(false);
					ignoreBox.setEnabled(false);
				}
				else if(e.getStateChange() == ItemEvent.DESELECTED){
					ignoreBox.setEnabled(true);
				}
			}
		});
		classBtn.setHorizontalAlignment(SwingConstants.CENTER);
		classBtn.setBounds(6, 75, 109, 23);
		classBtn.setActionCommand(head);
		add(classBtn);
		
		ignoreBox = new JCheckBox("Ignore");
		ignoreBox.setHorizontalAlignment(SwingConstants.CENTER);
		ignoreBox.setBounds(6, 101, 97, 23);
		ignoreBox.setActionCommand(head);
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
	
	public JCheckBox enabled(){
		return ignoreBox;
	}
	
	public String getHead(){
		return head;
	}
}
