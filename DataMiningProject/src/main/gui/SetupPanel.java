package main.gui;

import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JScrollBar;

public class SetupPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	ArrayList<Column> columns;
	ButtonGroup classGrp = new ButtonGroup();
	
	public SetupPanel(DataList dataList) {
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 2, 15));
		setPreferredSize(new Dimension(dataList.getLength()*127,135));
		columns = new ArrayList<Column>();
		for(int i = 0; i < dataList.getLength(); i++){
			Column column = new Column((String)dataList.getHead(i), i);
			column.setSize(125,135);
			add(column);
			classGrp.add(column.getRadio());
			columns.add(column);
		}		
		//for(int i = 0; i< columns.size(); i++){
		//	add(columns.get(i));
		//	classGrp.add(columns.get(i).getRadio());
		//}
	}
}
