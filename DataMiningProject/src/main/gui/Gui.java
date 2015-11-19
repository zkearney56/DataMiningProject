package main.gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.border.BevelBorder;

import main.algorithm.Entropy;
import main.structure.DataList;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFormattedTextField;

public class Gui extends JFrame {

	private JPanel contentPane;
	private DataList dataList;
	private DataList testData;
	private File file;
	JFileChooser fc;
	JScrollPane scrollPane;
	SetupPanel setupPanel;
	private JMenuItem mntmRun;
	private JButton btnExecute;
	private JFormattedTextField trimSize;
	private JComboBox trainingComboBox, nFold, pTrain, algorithm;
	private String trainingMode = "NFOLD";
	private JTextPane sizePane;
	private Format numFormat;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setTitle("Data Mining Project");
		setResizable(false);
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileFilter filter = new FileNameExtensionFilter("CSV File", "csv");
		fc.addChoosableFileFilter(filter);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1171, 700);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenCsv = new JMenuItem("Open CSV");
		mntmOpenCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		            int returnVal = fc.showOpenDialog(Gui.this);
		 
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                file = fc.getSelectedFile();
		                if(getExtension(file).equals("csv")){
		                	dataList = new DataList();
		                	dataList.readFile(file);
		                	showData();
		                	trainingComboBox.setEnabled(true);
		                	nFold.setEnabled(true);
		                	algorithm.setEnabled(true);
		                	trimSize.setEnabled(true);
		                }
		                else{
		                	System.out.println("Err, invalid file");
		                }
		            } else {
		            }
			}
		});
		mnFile.add(mntmOpenCsv);
		
		mntmRun = new JMenuItem("Run");
		mnFile.add(mntmRun);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnAlgorithm = new JTextPane();
		txtpnAlgorithm.setEditable(false);
		txtpnAlgorithm.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnAlgorithm.setBackground(SystemColor.menu);
		txtpnAlgorithm.setText("Algorithm");
		txtpnAlgorithm.setBounds(10, 589, 161, 20);
		contentPane.add(txtpnAlgorithm);
		
		algorithm = new JComboBox();
		algorithm.setEnabled(false);
		algorithm.setModel(new DefaultComboBoxModel(new String[] {"Entropy"}));
		algorithm.setBounds(10, 620, 161, 20);
		contentPane.add(algorithm);
		
		btnExecute = new JButton("Execute");
		btnExecute.setBounds(1019, 528, 136, 23);
		contentPane.add(btnExecute);
		
		JTextPane txtpnTrainingSet = new JTextPane();
		txtpnTrainingSet.setEditable(false);
		txtpnTrainingSet.setText("Training Set");
		txtpnTrainingSet.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnTrainingSet.setBackground(SystemColor.menu);
		txtpnTrainingSet.setBounds(202, 589, 161, 20);
		contentPane.add(txtpnTrainingSet);
		
		trainingComboBox = new JComboBox();
		trainingComboBox.setEnabled(false);
		trainingComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(trainingComboBox.getSelectedIndex() == 0){
					trainingMode = "NFOLD";
					pTrain.setEnabled(false);
					nFold.setEnabled(true);
				}
				else if(trainingComboBox.getSelectedIndex() == 1){
					trainingMode = "EVERYOTHER";
					pTrain.setEnabled(false);
					nFold.setEnabled(false);
				}
				else if(trainingComboBox.getSelectedIndex() == 2){
					trainingMode = "RANDOM";
					pTrain.setEnabled(true);
					nFold.setEnabled(false);
				}
			}
		});
		trainingComboBox.setModel(new DefaultComboBoxModel(new String[] {"N-Fold Cross Validation", "Every Other", "Random"}));
		trainingComboBox.setBounds(202, 620, 161, 20);
		contentPane.add(trainingComboBox);
		
		JTextPane txtpnNumfolds = new JTextPane();
		txtpnNumfolds.setEditable(false);
		txtpnNumfolds.setText("Number of Folds");
		txtpnNumfolds.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnNumfolds.setBackground(SystemColor.menu);
		txtpnNumfolds.setBounds(377, 589, 108, 20);
		contentPane.add(txtpnNumfolds);
		
		JTextPane txtpnPercentTraining = new JTextPane();
		txtpnPercentTraining.setEditable(false);
		txtpnPercentTraining.setText("Percent Training");
		txtpnPercentTraining.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnPercentTraining.setBackground(SystemColor.menu);
		txtpnPercentTraining.setBounds(508, 589, 136, 20);
		contentPane.add(txtpnPercentTraining);	
		
		nFold = new JComboBox();
		nFold.setEnabled(false);
		nFold.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "15", "20", "25", "50", "100"}));
		nFold.setBounds(373, 620, 112, 20);
		contentPane.add(nFold);
		
		pTrain = new JComboBox();
		pTrain.setEnabled(false);
		pTrain.setModel(new DefaultComboBoxModel(new String[] {"10", "20", "30", "40", "50", "60", "70", "80", "90"}));
		pTrain.setBounds(508, 620, 112, 20);
		contentPane.add(pTrain);
		
		JTextPane txtpnTrim = new JTextPane();
		txtpnTrim.setEditable(false);
		txtpnTrim.setText("Records");
		txtpnTrim.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnTrim.setBackground(SystemColor.menu);
		txtpnTrim.setBounds(10, 528, 161, 20);
		contentPane.add(txtpnTrim);
		
		JTextPane txtpnTrim_1 = new JTextPane();
		txtpnTrim_1.setEditable(false);
		txtpnTrim_1.setText("Trim");
		txtpnTrim_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnTrim_1.setBackground(SystemColor.menu);
		txtpnTrim_1.setBounds(202, 528, 161, 20);
		contentPane.add(txtpnTrim_1);
		
		sizePane = new JTextPane();
		sizePane.setEditable(false);
		sizePane.setBackground(SystemColor.menu);
		sizePane.setBounds(10, 558, 161, 20);
		contentPane.add(sizePane);
		
		trimSize = new JFormattedTextField(NumberFormat.getNumberInstance());
		trimSize.setEnabled(false);
		trimSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to trim?", "Trim?",  JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION)
				{
				   dataList.trimList(Integer.parseInt(trimSize.getText()));
				   update();
				}
			}
		});
		trimSize.setColumns(5);
		trimSize.setBounds(202, 559, 127, 20);
		contentPane.add(trimSize);

	}
	
	void update(){
		sizePane.setText(Integer.toString(dataList.getDataSize()));
	}
	
	void showData(){
		
		setupPanel = new SetupPanel(dataList);
		setupPanel.setBounds(0, 0, 1150, 500);
		scrollPane = new JScrollPane(setupPanel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 10, 1150, 500);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);
		update();
		
		btnExecute.addActionListener(new ActionListener(){	
			DataList testData = dataList;
			public void actionPerformed(ActionEvent arg0){
			Iterator<Object> ignoreItr = setupPanel.ignoreList().iterator();
			while(ignoreItr.hasNext()){
				
				Object remove = ignoreItr.next();
				Iterator<Object> itr2 = testData.dataTypeIterator();
				int x = 0;
				while(itr2.hasNext()){
					Object next = itr2.next();
					if(next.equals(remove)){
						break;
					}
					x++;
				}
				testData.removeColumn(x);
			}
			
			String classification = setupPanel.selectedClass();
			Iterator itr = testData.dataTypeIterator();
			int index = 0;
			while(itr.hasNext()){
				Object element = itr.next();
				if(element.equals(classification)){
					break;
				}
				index++;
			}
			
			testData.setClass(index);
			System.out.println("Test");
			DataList trainingSet = new DataList();
			DataList testSet = new DataList();
			Object[] sets;
			
			switch(trainingMode){
			case "NFOLD":	int folds = Integer.parseInt((String)nFold.getSelectedItem());
							sets = testData.nFoldCrossValid(folds);
							trainingSet = (DataList)sets[0];
							testSet = (DataList)sets[1];
							break;
							
			case "EVERYOTHER":
							sets = testData.everyOther();
							trainingSet = (DataList)sets[0];
							testSet = (DataList)sets[1];
							break;
							
			case "RANDOM":	float percent = Float.parseFloat((String)pTrain.getSelectedItem());
							sets = testData.randomShuffle(percent);
							trainingSet = (DataList)sets[0];
							testSet = (DataList)sets[1];
							break;
							
			default:		break;
			
			}
	
			ResultGui result = new ResultGui(testData);
			result.setVisible(true);
			System.out.println("Pause");
			Entropy e = new Entropy(trainingSet);
			System.out.println(e.test());
		}
	});
		
	/**
		mntmRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Iterator<Object> ignoreItr = setupPanel.ignoreList().iterator();
				while(ignoreItr.hasNext()){
					Object remove = ignoreItr.next();
					Iterator<Object> itr2 = dataList.dataTypeIterator();
					int x = 0;
					while(itr2.hasNext()){
						Object next = itr2.next();
						if(next.equals(remove)){
							break;
						}
						x++;
					}
					dataList.removeColumn(x);
				}
				String classification = setupPanel.selectedClass();
				Iterator itr = dataList.dataTypeIterator();
				int index = 0;
				while(itr.hasNext()){
					Object element = itr.next();
					if(element.equals(classification)){
						break;
					}
					index++;
				}
				
				dataList.setClass(index);
				System.out.println("Test");
			}
		});
		*/
	}
	
	  public String getExtension(File f) {
	        String ext = null;
	        String s = f.getName();
	        int i = s.lastIndexOf('.');

	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i+1).toLowerCase();
	        }
	        return ext;
	    }
}
