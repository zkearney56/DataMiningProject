package main.gui;

/**
 * @author Zachary Kearney
 * Last Edited: 12/1/2015
 * Class to display a data panel from a csv file.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Author: Zachary Kearney
 * Last Edited: 12/1/2015
 * This Panel contains all information on the loaded csv file. Displays it within the InitGui.
 */

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import main.structure.Attribute;
import main.structure.DataList;

@SuppressWarnings("serial")
public class DataPanel extends JPanel {
	private JTextPane relation, instances, attributes, selectName, selectType, selectMin, selectMax, selectUnique,
			selectMean, selectStdDev;
	private DataList dataList;
	private File file;
	private JTable table;
	private JPanel attributePanel;
	private JCheckBox ignoreBox;
	public boolean initialized = false;
	// private JTextPane relation, instances, attributes;

	/**
	 * Updates the panel when a new attribute is selected.
	 */

	private void update() {
		Attribute selected = dataList.getAttributes().get(getSelected());

		selectName.setText(selected.getName());
		selectType.setText(selected.getType());
		selectUnique.setText(Integer.toString(selected.getUniqueVals()));
		selectMin.setText(Double.toString(selected.getMin()));
		selectMax.setText(Double.toString(selected.getMax()));
		selectMean.setText(Double.toString(selected.getMean()));
		selectStdDev.setText(Double.toString(selected.getStdDev()));
		ignoreBox.setSelected(selected.getIgnore());

	}

	/**
	 * Updates the panel when a csv is loaded.
	 */

	private void updateMain() {
		relation.setText(file.getName());
		instances.setText(Integer.toString(dataList.getNumRows()));
		attributes.setText(Integer.toString(dataList.getLength()));
	}

	public DataPanel(File file) {
		initialized = true;
		this.file = file;
		dataList = new DataList();
		dataList.readFile(file);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Current Relation", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 319, 67);
		add(panel);
		panel.setLayout(null);
		setPreferredSize(new Dimension(646, 384));

		JTextPane txtpnRelation = new JTextPane();
		txtpnRelation.setBackground(SystemColor.menu);
		txtpnRelation.setEditable(false);
		txtpnRelation.setText("Relation:");
		txtpnRelation.setBounds(10, 15, 57, 20);
		panel.add(txtpnRelation);

		JTextPane txtpnInstances = new JTextPane();
		txtpnInstances.setText("Instances:");
		txtpnInstances.setEditable(false);
		txtpnInstances.setBackground(SystemColor.menu);
		txtpnInstances.setBounds(10, 39, 57, 20);
		panel.add(txtpnInstances);

		JTextPane txtpnAttributes = new JTextPane();
		txtpnAttributes.setText("Attributes:");
		txtpnAttributes.setEditable(false);
		txtpnAttributes.setBackground(SystemColor.menu);
		txtpnAttributes.setBounds(192, 39, 62, 20);
		panel.add(txtpnAttributes);

		relation = new JTextPane();
		relation.setEditable(false);
		relation.setBackground(SystemColor.menu);
		relation.setText("...");
		relation.setBounds(66, 15, 62, 20);
		panel.add(relation);

		instances = new JTextPane();
		instances.setEditable(false);
		instances.setBackground(SystemColor.menu);
		instances.setText("...");
		instances.setBounds(66, 39, 62, 20);
		panel.add(instances);

		attributes = new JTextPane();
		attributes.setEditable(false);
		attributes.setText("...");
		attributes.setBackground(SystemColor.menu);
		attributes.setBounds(252, 39, 57, 20);
		panel.add(attributes);

		attributePanel = new JPanel();
		attributePanel.setBorder(new TitledBorder(null, "Attributes", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		attributePanel.setBounds(10, 89, 319, 284);
		add(attributePanel);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Selected Attribute",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(339, 11, 297, 223);
		add(panel_2);

		JTextPane txtpnName = new JTextPane();
		txtpnName.setText("Name:");
		txtpnName.setEditable(false);
		txtpnName.setBackground(SystemColor.menu);
		txtpnName.setBounds(10, 15, 57, 20);
		panel_2.add(txtpnName);

		JTextPane txtPaneType = new JTextPane();
		txtPaneType.setText("Type:");
		txtPaneType.setEditable(false);
		txtPaneType.setBackground(SystemColor.menu);
		txtPaneType.setBounds(10, 39, 57, 20);
		panel_2.add(txtPaneType);

		JTextPane txtpnIgnore = new JTextPane();
		txtpnIgnore.setText("Ignore:");
		txtpnIgnore.setEditable(false);
		txtpnIgnore.setBackground(SystemColor.menu);
		txtpnIgnore.setBounds(10, 187, 47, 20);
		panel_2.add(txtpnIgnore);

		selectName = new JTextPane();
		selectName.setEditable(false);
		selectName.setText("...");
		selectName.setBackground(SystemColor.menu);
		selectName.setBounds(66, 15, 62, 20);
		panel_2.add(selectName);

		selectType = new JTextPane();
		selectType.setEditable(false);
		selectType.setText("...");
		selectType.setBackground(SystemColor.menu);
		selectType.setBounds(66, 39, 62, 20);
		panel_2.add(selectType);

		JTextPane txtpnUnique = new JTextPane();
		txtpnUnique.setText("Unique:");
		txtpnUnique.setEditable(false);
		txtpnUnique.setBackground(SystemColor.menu);
		txtpnUnique.setBounds(10, 64, 57, 20);
		panel_2.add(txtpnUnique);

		JTextPane txtpnMin = new JTextPane();
		txtpnMin.setText("Min:");
		txtpnMin.setEditable(false);
		txtpnMin.setBackground(SystemColor.menu);
		txtpnMin.setBounds(10, 89, 57, 20);
		panel_2.add(txtpnMin);

		JTextPane txtpnMax = new JTextPane();
		txtpnMax.setText("Max:");
		txtpnMax.setEditable(false);
		txtpnMax.setBackground(SystemColor.menu);
		txtpnMax.setBounds(10, 113, 57, 20);
		panel_2.add(txtpnMax);

		JTextPane txtpnMean = new JTextPane();
		txtpnMean.setText("Mean:");
		txtpnMean.setEditable(false);
		txtpnMean.setBackground(SystemColor.menu);
		txtpnMean.setBounds(10, 138, 57, 20);
		panel_2.add(txtpnMean);

		JTextPane txtpnStddev = new JTextPane();
		txtpnStddev.setText("StdDev:");
		txtpnStddev.setEditable(false);
		txtpnStddev.setBackground(SystemColor.menu);
		txtpnStddev.setBounds(10, 163, 57, 20);
		panel_2.add(txtpnStddev);

		selectUnique = new JTextPane();
		selectUnique.setEditable(false);
		selectUnique.setText("...");
		selectUnique.setBackground(SystemColor.menu);
		selectUnique.setBounds(66, 64, 62, 20);
		panel_2.add(selectUnique);

		selectMin = new JTextPane();
		selectMin.setEditable(false);
		selectMin.setText("...");
		selectMin.setBackground(SystemColor.menu);
		selectMin.setBounds(66, 89, 62, 20);
		panel_2.add(selectMin);

		selectMax = new JTextPane();
		selectMax.setEditable(false);
		selectMax.setText("...");
		selectMax.setBackground(SystemColor.menu);
		selectMax.setBounds(66, 113, 62, 20);
		panel_2.add(selectMax);

		selectMean = new JTextPane();
		selectMean.setEditable(false);
		selectMean.setText("...");
		selectMean.setBackground(SystemColor.menu);
		selectMean.setBounds(66, 138, 62, 20);
		panel_2.add(selectMean);

		selectStdDev = new JTextPane();
		selectStdDev.setEditable(false);
		selectStdDev.setText("...");
		selectStdDev.setBackground(SystemColor.menu);
		selectStdDev.setBounds(66, 163, 62, 20);
		panel_2.add(selectStdDev);

		ignoreBox = new JCheckBox("");
		ignoreBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ignoreBox.isSelected()) {
					Attribute a = dataList.getAttributes().get(getSelected());
					a.ignore(true);
					dataList.getAttributes().set(getSelected(), a);
				} else if (!ignoreBox.isSelected()) {
					Attribute a = dataList.getAttributes().get(getSelected());
					a.ignore(false);
					dataList.getAttributes().set(getSelected(), a);
				}
			}
		});

		ignoreBox.setBounds(66, 187, 30, 23);
		panel_2.add(ignoreBox);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
		panel_3.setBounds(339, 245, 297, 128);
		add(panel_3);
		panel_3.setLayout(null);

		JButton btnNext = new JButton("Next");
		btnNext.setBounds(171, 94, 116, 23);
		btnNext.addActionListener(next());
		panel_3.add(btnNext);

		addTable();
		updateMain();
		update();
	}

	/**
	 * AbstractTableModel for the display table.
	 * 
	 * @author zrk1002
	 *
	 */

	class AttributePanel extends AbstractTableModel {
		private String[] columnNames = { "No.", "Name" };
		private Object[][] data;

		AttributePanel() {
			data = getList();
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			return data[arg0][arg1];
		}

		Object[][] getList() {
			Object[][] data = new Object[dataList.getLength()][2];
			for (int i = 0; i < dataList.getLength(); i++) {
				data[i][0] = Integer.toString(i);
				data[i][1] = dataList.getHead(i);
			}
			return data;

		}
	}

	/**
	 * Used to force the table to only allow 1 row selection.
	 * 
	 * @author zrk1002
	 *
	 */

	class ForcedListSelectionModel extends DefaultListSelectionModel {

		public ForcedListSelectionModel() {
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}

		@Override
		public void clearSelection() {
		}

		@Override
		public void removeSelectionInterval(int index0, int index1) {
		}

	}

	/**
	 * Adds the table containing csv data.
	 */

	void addTable() {
		attributePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		table = new JTable(new AttributePanel());
		table.setFillsViewportHeight(true);
		table.setBounds(10, 24, 299, 248);
		table.setSelectionModel(new ForcedListSelectionModel());
		JScrollPane pane = new JScrollPane(table);
		pane.setPreferredSize(new Dimension(300, 250));
		attributePanel.add(pane);
		table.changeSelection(0, 0, false, false);
		table.setPreferredSize(new Dimension(300, 250));
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				update();
			}

		});
	}

	/**
	 * ActionListener to open a runDialog using data from the dataList.
	 * 
	 * @return
	 */

	private ActionListener next() {
		ActionListener next = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				DataList testList = new DataList(dataList);
				RunDialog dlg = new RunDialog();
				dlg.run(testList);
				dlg.setVisible(true);
				dlg.setModal(true);
			}

		};
		return next;
	}

	private int getSelected() {
		return table.getSelectedRow();
	}

	public DataList getList() {
		return dataList;
	}
}