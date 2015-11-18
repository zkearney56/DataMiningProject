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
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.border.BevelBorder;
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
	private JTextField foldTrain;
	private JTextField percentTrain;
	private JComboBox trainingComboBox, nFold, pTrain, algorithm;
	private String trainingMode = "NFOLD";
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
		setBounds(100, 100, 1171, 465);
		
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
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 11, 1150, 330);
		contentPane.add(panel);
		
		JTextPane txtpnAlgorithm = new JTextPane();
		txtpnAlgorithm.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnAlgorithm.setBackground(SystemColor.menu);
		txtpnAlgorithm.setText("Algorithm");
		txtpnAlgorithm.setBounds(10, 352, 161, 20);
		contentPane.add(txtpnAlgorithm);
		
		algorithm = new JComboBox();
		algorithm.setEnabled(false);
		algorithm.setModel(new DefaultComboBoxModel(new String[] {"Entropy"}));
		algorithm.setBounds(10, 383, 161, 20);
		contentPane.add(algorithm);
		
		btnExecute = new JButton("Execute");
		btnExecute.setBounds(1019, 352, 136, 23);
		contentPane.add(btnExecute);
		
		JTextPane txtpnTrainingSet = new JTextPane();
		txtpnTrainingSet.setText("Training Set");
		txtpnTrainingSet.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnTrainingSet.setBackground(SystemColor.menu);
		txtpnTrainingSet.setBounds(202, 352, 161, 20);
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
		trainingComboBox.setBounds(202, 383, 161, 20);
		contentPane.add(trainingComboBox);
		
		JTextPane txtpnNumfolds = new JTextPane();
		txtpnNumfolds.setText("Number of Folds");
		txtpnNumfolds.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnNumfolds.setBackground(SystemColor.menu);
		txtpnNumfolds.setBounds(377, 352, 108, 20);
		contentPane.add(txtpnNumfolds);
		
		JTextPane txtpnPercentTraining = new JTextPane();
		txtpnPercentTraining.setText("Percent Training");
		txtpnPercentTraining.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtpnPercentTraining.setBackground(SystemColor.menu);
		txtpnPercentTraining.setBounds(508, 352, 136, 20);
		contentPane.add(txtpnPercentTraining);	
		
		nFold = new JComboBox();
		nFold.setEnabled(false);
		nFold.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "15", "20", "25", "50", "100"}));
		nFold.setBounds(373, 383, 112, 20);
		contentPane.add(nFold);
		
		pTrain = new JComboBox();
		pTrain.setEnabled(false);
		pTrain.setModel(new DefaultComboBoxModel(new String[] {"10", "20", "30", "40", "50", "60", "70", "80", "90"}));
		pTrain.setBounds(508, 383, 112, 20);
		contentPane.add(pTrain);
		
	}
	
	void showData(){
		
		setupPanel = new SetupPanel(dataList);
		setupPanel.setBounds(0, 0, 1150, 320);
		scrollPane = new JScrollPane(setupPanel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 10, 1150, 330);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);
		
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
			if(trainingMode.equals("NFOLD")){
				int folds = Integer.parseInt((String)nFold.getSelectedItem());
				testData.nFoldCrossValid(folds);
			}
			else if(trainingMode.equals("EVERYOTHER"))
			{
				testData.everyOther();	
			}
			else if(trainingMode.equals("RANDOM")){
				float percent = Float.parseFloat((String)pTrain.getSelectedItem());
				testData.randomShuffle(percent);
			}
			
			ResultGui result = new ResultGui(testData);
			result.setVisible(true);
			System.out.println("Pause");
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
