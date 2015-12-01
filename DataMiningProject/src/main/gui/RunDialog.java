package main.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.structure.DataList;

import javax.swing.JTextPane;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.awt.event.ActionEvent;

public class RunDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	private JComboBox mainclass, trim, percent, model, algorithm;
	private DataList list;
	
	/**
	 * Create the dialog.
	 */
	
	private void cleanList(){
	for(int i = 0; i < list.getLength(); i++){
		if(list.getAttribute(i).getIgnore()){
			list.removeColumn(i);
		}
	}
	}
	
	public RunDialog() {
	}
	
	public void run(DataList list){
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.list = list;
		cleanList();
		setTitle("Run Algorithm");
		setResizable(false);
		setBounds(100, 100, 305, 267);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JTextPane txtpnTrim = new JTextPane();
			txtpnTrim.setBounds(10, 10, 87, 20);
			txtpnTrim.setBackground(SystemColor.menu);
			txtpnTrim.setText("Trim Percent");
			contentPanel.add(txtpnTrim);
		}
		{
			JTextPane txtpnAlgorithm = new JTextPane();
			txtpnAlgorithm.setBounds(10, 65, 55, 20);
			txtpnAlgorithm.setText("Algorithm");
			txtpnAlgorithm.setBackground(SystemColor.menu);
			contentPanel.add(txtpnAlgorithm);
		}
		{
			JTextPane txtpnTrainingSet = new JTextPane();
			txtpnTrainingSet.setContentType("text/plain/center");
			txtpnTrainingSet.setText("Training Set Model");
			txtpnTrainingSet.setBackground(SystemColor.menu);
			txtpnTrainingSet.setBounds(150, 10, 113, 20);
			contentPanel.add(txtpnTrainingSet);
		}
		{
			JTextPane txtpnPercentTraining = new JTextPane();
			txtpnPercentTraining.setText("Percent");
			txtpnPercentTraining.setBackground(SystemColor.menu);
			txtpnPercentTraining.setBounds(150, 65, 79, 20);
			contentPanel.add(txtpnPercentTraining);
		}
		{
			JTextPane txtpnMainClass = new JTextPane();
			txtpnMainClass.setText("Main Class");
			txtpnMainClass.setBackground(SystemColor.menu);
			txtpnMainClass.setBounds(10, 120, 64, 20);
			contentPanel.add(txtpnMainClass);
		}
		
		trim = new JComboBox();
		trim.setModel(new DefaultComboBoxModel(new Object[] {0.0,10.0,20.0,30.0,40.0,50.0,60.0,70.0,80.0,90.0,95.0,96.0,97.0,98.0,99.0}));
		trim.setBounds(10, 37, 113, 20);
		contentPanel.add(trim);
		
		percent = new JComboBox();
		percent.setModel(new DefaultComboBoxModel(new Object[] {1.0,2.0,3.0,4.0,5.0,10.0,15.0,20.0,25.0,30.0,40.0,50.0,60.0,70.0,75.0,80.0,85.0,90.0,95.0,96.0,97.0,98.0,99.0}));
		percent.setBounds(150, 92, 113, 20);
		contentPanel.add(percent);
		
		model = new JComboBox();
		model.setModel(new DefaultComboBoxModel(new String[] {"Every Other", "Percentage"}));
		model.setBounds(150, 34, 113, 20);
		contentPanel.add(model);
		
		algorithm = new JComboBox();
		algorithm.setModel(new DefaultComboBoxModel(new String[] {"Entropy", "Gini"}));
		algorithm.setBounds(10, 92, 113, 20);
		contentPanel.add(algorithm);
		
		String[] headers = new String[list.getLength()];
		Iterator itr = list.dataTypeIterator();
		int i = 0;
		while(itr.hasNext()){
			headers[i] = (String)itr.next();
			i++;
		}
		mainclass = new JComboBox();
		mainclass.setModel(new DefaultComboBoxModel(headers));
		mainclass.setBounds(10, 151, 113, 20);
		contentPanel.add(mainclass);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Execute");
				okButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0){
						execute();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
	private void execute(){
		
		if((double)trim.getSelectedItem() != 0.0){
		double per = (double)trim.getSelectedItem() / 100;
		double size = (list.getNumRows());
		size =  size*per;	
		System.out.println("Pause");
		list.trimList((int)size);
		}

		list.setClass(mainclass.getSelectedIndex());
		Object[] sets = new Object[1];
		if(model.getSelectedIndex()==0){ //EveryOther
			sets = list.everyOther();
		}		
		
		else if(model.getSelectedIndex()==1){
			float percentage = ((Double)percent.getSelectedItem()).floatValue();
			sets = list.randomShuffle(percentage);// Percentage
		}
		
		String algorithmString = (String)algorithm.getSelectedItem();
		
		ResultGui result = new ResultGui(sets,algorithmString);
		result.setVisible(true);
		
		RunDialog.this.dispose();
	}
}
