package main.old;
/**
 * Author: Zachary Kearney
 * Last Edited: 11/30/2015
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import main.structure.AttributeInterface;

@Deprecated
public class AttributePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	private JTextPane headerName;
	private JCheckBox ignoreBox;
	private JRadioButton classBtn;
	private String head, type;
	private JTextPane txtpnType;
	private JTextPane txtpnMin;
	private JTextPane txtpnMaximum;
	private JTextPane txtpnMean;
	private JTextPane txtpnStddev;
	private JTextPane min;
	private JTextPane max;
	private JTextPane mean;
	private JTextPane stdev;
	private JTextPane Type;
	private JTextPane uniqueVals;
	
	public AttributePanel(AttributeInterface att) {
		setBackground(SystemColor.window);
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(null);
		setPreferredSize(new Dimension(250, 250));
		head = att.getName();
		type = att.getType();
		classBtn = new JRadioButton("Set Main Class");
		classBtn.setBackground(SystemColor.window);
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
		classBtn.setBounds(6, 220, 109, 23);
		classBtn.setActionCommand(head);
		add(classBtn);
		
		ignoreBox = new JCheckBox("Ignore");
		ignoreBox.setBackground(SystemColor.window);
		ignoreBox.setHorizontalAlignment(SwingConstants.CENTER);
		ignoreBox.setBounds(137, 220, 97, 23);
		ignoreBox.setActionCommand(head);
		add(ignoreBox);
		
		JTextPane txtpnId = new JTextPane();
		txtpnId.setEditable(false);
		txtpnId.setBackground(SystemColor.window);
		txtpnId.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnId.setText("Name");
		txtpnId.setBounds(36, 11, 49, 23);
		add(txtpnId);
		
		headerName = new JTextPane();
		headerName.setEditable(false);
		headerName.setBackground(SystemColor.controlShadow);
		headerName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		headerName.setText(head);
		headerName.setBounds(6, 45, 109, 23);
		add(headerName);
		
		txtpnType = new JTextPane();
		txtpnType.setEditable(false);
		txtpnType.setText("Type");
		txtpnType.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnType.setBackground(SystemColor.window);
		txtpnType.setBounds(161, 11, 49, 23);
		add(txtpnType);
		
		txtpnMin = new JTextPane();
		txtpnMin.setEditable(false);
		txtpnMin.setBackground(Color.WHITE);
		txtpnMin.setText("Minimum");
		txtpnMin.setBounds(6, 80, 109, 20);
		add(txtpnMin);
		
		txtpnMaximum = new JTextPane();
		txtpnMaximum.setEditable(false);
		txtpnMaximum.setText("Maximum");
		txtpnMaximum.setBackground(Color.WHITE);
		txtpnMaximum.setBounds(6, 105, 109, 20);
		add(txtpnMaximum);
		
		txtpnMean = new JTextPane();
		txtpnMean.setEditable(false);
		txtpnMean.setText("Mean");
		txtpnMean.setBackground(Color.WHITE);
		txtpnMean.setBounds(6, 130, 109, 20);
		add(txtpnMean);
		
		txtpnStddev = new JTextPane();
		txtpnStddev.setEditable(false);
		txtpnStddev.setText("StdDev");
		txtpnStddev.setBackground(Color.WHITE);
		txtpnStddev.setBounds(6, 155, 109, 20);
		add(txtpnStddev);
		
		min = new JTextPane();
		min.setEditable(false);
		min.setBackground(Color.WHITE);
		min.setBounds(125, 80, 109, 20);
		add(min);
		
		max = new JTextPane();
		max.setEditable(false);
		max.setBackground(Color.WHITE);
		max.setBounds(125, 105, 109, 20);
		add(max);
		
		mean = new JTextPane();
		mean.setEditable(false);
		mean.setBackground(Color.WHITE);
		mean.setBounds(125, 130, 109, 20);
		add(mean);
		
		stdev = new JTextPane();
		stdev.setEditable(false);
		stdev.setBackground(Color.WHITE);
		stdev.setBounds(125, 155, 109, 20);
		add(stdev);
		
		Type = new JTextPane();
		Type.setFont(new Font("Tahoma", Font.PLAIN, 11));
		Type.setEditable(false);
		Type.setBackground(SystemColor.controlShadow);
		Type.setBounds(131, 45, 109, 23);
		add(Type);
		
		JTextPane txtPaneUniqueVals = new JTextPane();
		txtPaneUniqueVals.setText("UniqueVals");
		txtPaneUniqueVals.setEditable(false);
		txtPaneUniqueVals.setBackground(Color.WHITE);
		txtPaneUniqueVals.setBounds(6, 180, 109, 20);
		add(txtPaneUniqueVals);
		
		uniqueVals = new JTextPane();
		uniqueVals.setEditable(false);
		uniqueVals.setBackground(Color.WHITE);
		uniqueVals.setBounds(125, 180, 109, 20);
		add(uniqueVals);
		
		switch (type){
		case "Categorial": 	Type.setText(type);
							min.setText(Double.toString(att.getMin()));
							max.setText(Double.toString(att.getMax()));
							mean.setText(Double.toString(att.getMean()));
							stdev.setText(Double.toString(att.getStdDev()));
							uniqueVals.setText(Integer.toString(att.getNumUniqueVals()));
							break;
							
		case "Numeric": 	
							Type.setText(type);
							min.setText(Double.toString(att.getMin()));
							max.setText(Double.toString(att.getMax()));
							mean.setText(Double.toString(att.getMean()));
							stdev.setText(Double.toString(att.getStdDev()));
							break;
		
		default: break;
		}
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

