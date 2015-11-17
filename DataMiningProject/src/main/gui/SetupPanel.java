package main.gui;

import main.structure.DMArrayList;
import main.structure.DataList;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.util.Iterator;
import java.awt.Dimension;

public class SetupPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private DMArrayList<Column> columns;
	private ButtonGroup classGrp = new ButtonGroup();

	public SetupPanel(DataList dataList) {
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 2, 15));

		columns = new DMArrayList<Column>();
		for(int i = 0; i < dataList.getLength(); i++){
			Column column = new Column((String)dataList.getHead(i));
			column.setSize(125,135);
			add(column);
			classGrp.add(column.getRadio());
			columns.add(column);
		}
		columns.get(0).getRadio().setSelected(true);
		setPreferredSize(new Dimension((dataList.getLength()*129/2),135*2));
	}
	
	public String selectedClass()
	{
		return classGrp.getSelection().getActionCommand();
	}
	
	public DMArrayList<Object> ignoreList(){
		DMArrayList<Object> list = new DMArrayList<Object>();
		Iterator<Column> itr = columns.iterator();
		while(itr.hasNext()){
			Column column = itr.next();
			if(column.enabled().isSelected()){
				list.add(column.getHead());
			}
		}
		return list;
	}
	
}