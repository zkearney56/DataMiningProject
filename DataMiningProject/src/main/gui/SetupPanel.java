package main.gui;

import main.structure.Attribute;
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
	
	//private DMArrayList<Column> columns;
	private DMArrayList<AttributePanel> attributes;
	
	private ButtonGroup classGrp = new ButtonGroup();

	public SetupPanel(DataList dataList) {
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 2, 15));

		//columns = new DMArrayList<Column>();
		attributes = new DMArrayList<AttributePanel>();
		
		for(int i = 0; i < dataList.getLength(); i++){
			AttributePanel att = new AttributePanel(dataList.getAttribute(i));
			//Column column = new Column((String)dataList.getHead(i));
			att.setSize(250,216);
			add(att);
			classGrp.add(att.getRadio());
			attributes.add(att);
			//column.setSize(125,135);
			//add(column);
			//classGrp.add(column.getRadio());
			//columns.add(column);
		}
		attributes.get(0).getRadio().setSelected(true);
		setPreferredSize(new Dimension((dataList.getLength()*254/2),216*2));
	}
	
	public String selectedClass()
	{
		return classGrp.getSelection().getActionCommand();
	}
	
	public DMArrayList<Object> ignoreList(){
		DMArrayList<Object> list = new DMArrayList<Object>();
		Iterator<AttributePanel> itr = attributes.iterator();
		while(itr.hasNext()){
			AttributePanel attr = itr.next();
			if(attr.enabled().isSelected()){
				list.add(attr.getHead());
			}
		}
		return list;
	}
	
}