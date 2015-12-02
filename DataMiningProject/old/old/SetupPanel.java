package main.old;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;

/**
 * Author: Zachary Kearney
 * Last Edited: 11/30/2015
 */
import main.structure.Attribute;
import main.structure.AttributeInterface;
import main.structure.DMArrayList;

@Deprecated
public class SetupPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private DMArrayList<AttributePanel> attributePanel;
	private DMArrayList<Attribute> attributes;
	
	private ButtonGroup classGrp = new ButtonGroup();

	public SetupPanel(DMArrayList<Attribute> attributes) {
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 2, 15));

		attributePanel = new DMArrayList<AttributePanel>();
		this.attributes = attributes;
		Iterator itr = attributes.iterator();
		while(itr.hasNext()){
			AttributePanel att = new AttributePanel((AttributeInterface)itr.next());
			att.setSize(250,250);
			add(att);
			classGrp.add(att.getRadio());
			attributePanel.add(att);
		}

		attributePanel.get(0).getRadio().setSelected(true);
		setPreferredSize(new Dimension((attributes.size()*140),216*2));
	}
	
	public String selectedClass()
	{
		return classGrp.getSelection().getActionCommand();
	}
	
	public DMArrayList<Object> ignoreList(){
		DMArrayList<Object> list = new DMArrayList<Object>();
		Iterator<AttributePanel> itr = attributePanel.iterator();
		while(itr.hasNext()){
			AttributePanel attr = itr.next();
			if(attr.enabled().isSelected()){
				list.add(attr.getHead());
			}
		}
		return list;
	}
	
}