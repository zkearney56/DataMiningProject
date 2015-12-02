package main.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import main.structure.DMArrayList;
import main.structure.DataList;
/**
 * Dialog used to run the data mining algorithms.
 * 
 * @author Zachary Kearney
 * 
 * Last Edited: 12/1/2015
 *
 */
@SuppressWarnings("serial")
public class RunDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox mainclass, trim, percent, model, algorithm;
	private DMArrayList<String> ignored;
	private DataList list;

	/**
	 * Create the dialog.
	 */

	public RunDialog() {
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void run(DataList list) {
		ignored = new DMArrayList<String>();
		setModal(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
			txtpnTrim.setEditable(false);
			contentPanel.add(txtpnTrim);
		}
		{
			JTextPane txtpnAlgorithm = new JTextPane();
			txtpnAlgorithm.setBounds(10, 65, 55, 20);
			txtpnAlgorithm.setText("Algorithm");
			txtpnAlgorithm.setBackground(SystemColor.menu);
			txtpnAlgorithm.setEditable(false);
			contentPanel.add(txtpnAlgorithm);
		}
		{
			JTextPane txtpnTrainingSet = new JTextPane();
			txtpnTrainingSet.setContentType("text/plain/center");
			txtpnTrainingSet.setText("Training Set Model");
			txtpnTrainingSet.setBackground(SystemColor.menu);
			txtpnTrainingSet.setBounds(150, 10, 113, 20);
			txtpnTrainingSet.setEditable(false);
			contentPanel.add(txtpnTrainingSet);
		}
		{
			JTextPane txtpnPercentTraining = new JTextPane();
			txtpnPercentTraining.setText("Percent");
			txtpnPercentTraining.setBackground(SystemColor.menu);
			txtpnPercentTraining.setBounds(150, 65, 79, 20);
			txtpnPercentTraining.setEditable(false);
			contentPanel.add(txtpnPercentTraining);
		}
		{
			JTextPane txtpnMainClass = new JTextPane();
			txtpnMainClass.setText("Main Class");
			txtpnMainClass.setBackground(SystemColor.menu);
			txtpnMainClass.setBounds(10, 120, 64, 20);
			txtpnMainClass.setEditable(false);
			contentPanel.add(txtpnMainClass);
		}

		trim = new JComboBox();
		trim.setModel(new DefaultComboBoxModel(new Object[] { 0.0, 10.0, 20.0, 30.0, 40.0, 50.0, 60.0, 70.0, 80.0, 90.0,
				95.0, 96.0, 97.0, 98.0, 99.0 }));
		trim.setBounds(10, 37, 113, 20);
		contentPanel.add(trim);

		percent = new JComboBox();
		percent.setModel(new DefaultComboBoxModel(new Object[] { 1.0, 2.0, 3.0, 4.0, 5.0, 10.0, 15.0, 20.0, 25.0, 30.0,
				40.0, 50.0, 60.0, 70.0, 75.0, 80.0, 85.0, 90.0, 95.0, 96.0, 97.0, 98.0, 99.0 }));
		percent.setBounds(150, 92, 113, 20);
		contentPanel.add(percent);
		percent.setEnabled(false);

		model = new JComboBox();
		model.setModel(new DefaultComboBoxModel(new String[] { "Every Other", "Percentage" }));
		model.setBounds(150, 34, 113, 20);
		contentPanel.add(model);
		model.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (model.getSelectedIndex() == 0) {
					percent.setEnabled(false);
				} else if (model.getSelectedIndex() == 1) {
					percent.setEnabled(true);
				}

			}
		});

		algorithm = new JComboBox();
		algorithm.setModel(new DefaultComboBoxModel(new String[] { "Entropy", "Gini" }));
		algorithm.setBounds(10, 92, 113, 20);
		contentPanel.add(algorithm);

		String[] headers = new String[list.getLength()];
		Iterator itr = list.dataHeadersIterator();
		int i = 0;
		while (itr.hasNext()) {
			headers[i] = (String) itr.next();
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
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						execute();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

	}

	private void execute() {

		if ((double) trim.getSelectedItem() != 0.0) {
			double per = (double) trim.getSelectedItem() / 100;
			double size = (list.getNumRows());
			size = size * per;
			list.trimList((int) size);
		}

		list.setClass(mainclass.getSelectedIndex());
		Object[] sets = new Object[1];
		if (model.getSelectedIndex() == 0) { // EveryOther
			sets = list.everyOther();
		}

		else if (model.getSelectedIndex() == 1) {
			float percentage = ((Double) percent.getSelectedItem()).floatValue();
			sets = list.percentSplit(percentage);// Percentage
		}

		String algorithmString = (String) algorithm.getSelectedItem();

		ResultGui result = new ResultGui(sets, algorithmString, ignored);
		RunDialog.this.dispose();
		result.setVisible(true);
	}

	private void cleanList() {

		for (int i = 0; i < list.getLength(); i++) {
			if (list.getAttribute(i).getIgnore()) {
				list.removeColumn(i);
				ignored.add((String) list.getHead(i));
			}
		}
	}
}
