package main.gui;
/**
 * Author: Zachary Kearney
 * Last Edited: 11/30/2015
 * GUI that displays the results from running the data mining algorithms.
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.algorithm.Algorithm;
import main.algorithm.Entropy;
import main.algorithm.Gini;
import main.structure.DMArrayList;
import main.structure.DataList;
import main.tree.DecisionTree;

@SuppressWarnings("serial")
public class ResultGui extends JFrame {

	private JPanel contentPane;
	private DataList trainingSet;
	private DataList testSet;
	private JFileChooser fcsave;
	private JFileChooser fcexport;
	private JTextArea textArea;
	/**
	 * Create the frame.
	 */
	public ResultGui(Object[] dataSets, String algorithm, DMArrayList<String> ignored) {

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt");
		fcsave = new JFileChooser();
		fcsave.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fcsave.setApproveButtonText("Save");
		fcsave.setFileFilter(filter);
		
		fcexport = new JFileChooser();
		fcexport.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fcexport.setApproveButtonText("Export");
		fcsave.setFileFilter(filter);
		
		trainingSet = (DataList)dataSets[0];
		testSet = (DataList)dataSets[1];
		setTitle("Results");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		mntmSave.addActionListener(save());
		
		JMenuItem exportTest = new JMenuItem("Export Test Set");
		mnFile.add(exportTest);
		exportTest.addActionListener(export(testSet));
		
		JMenuItem exportTrain = new JMenuItem("Export Training Set");
		mnFile.add(exportTrain);
		exportTrain.addActionListener(export(trainingSet));
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
			dispose();
			}
			
		});
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		textArea = new JTextArea (25, 80);
		System.out.println("Pause");	
		
		contentPane.setLayout (new BorderLayout ());
        contentPane.add (
            new JScrollPane (
                textArea, 
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
            BorderLayout.CENTER);
        pack ();
        setVisible (true);
        
        GuiOutputStream out = new GuiOutputStream (textArea);
        System.setOut (new PrintStream (out));
        
        System.out.println("Ignored Attributes:");
        ignored.printArray();
        System.out.println("");
        System.out.println("Main Class: " + (String)trainingSet.getClassification());
        System.out.println("");
		System.out.println("Training Set Size: " + trainingSet.getNumRows());
		System.out.println("Test Set Size: " + testSet.getNumRows());
		System.out.println("");
		System.out.println("Algorithm: " + algorithm);
		System.out.println("");
		Algorithm a;
		switch(algorithm){
		case "Entropy":	a = new Entropy(trainingSet);
						break;
						
		case "Gini":	a = new Gini(trainingSet);
						break;
						
		default:		a = new Entropy(trainingSet);
						break;
		}
		DecisionTree t = new DecisionTree(a);
		System.out.println("Training Tree...");
		t.trainTree();
		System.out.println("Results:");
		System.out.println("");
		t.inOrderPrint();
		int total = testSet.getNumRows();
		int correct = 0;
		for(int i = 0; i < testSet.getNumRows(); i++){
			if(testSet.getRow(i).getClassification().equals(t.classify(testSet.getRow(i)))){
				correct++;
			}
			System.out.println(testSet.getRow(i).getClassification() + " = " + t.classify(testSet.getRow(i)));
		}
		System.out.println("Percent correctly classified: " + Float.toString((float)correct/(float)total));
	}
	
	private ActionListener export(DataList list){
		
		final DataList exportList = list;
		
		ActionListener export = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnval = fcexport.showSaveDialog(ResultGui.this);
				if(returnval == JFileChooser.APPROVE_OPTION){
					File file = fcexport.getSelectedFile();
					if(file.exists()){
						int n = JOptionPane.showConfirmDialog(
							    ResultGui.this,
							    "File Already Exists, Do You Want To OverWrite?",
							    "File Already Exists",
							    JOptionPane.YES_NO_OPTION);
						if(n!=JOptionPane.YES_OPTION){
							return;
						}
					}
				      if (!file.getName().endsWith(".csv")) {
				         file = new File(file.getAbsolutePath() + ".csv");
				      }
				      exportList.writeToCSV(file.getAbsolutePath());			      
				}
				// TODO Auto-generated method stub
				
			}
			
		};
		return export;
	}
	private ActionListener save(){
		
		ActionListener save = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnval = fcsave.showSaveDialog(ResultGui.this);
				if(returnval == JFileChooser.APPROVE_OPTION){
					File file = fcsave.getSelectedFile();
					if(file.exists()){
						int n = JOptionPane.showConfirmDialog(
							    ResultGui.this,
							    "File Already Exists, Do You Want To OverWrite?",
							    "File Already Exists",
							    JOptionPane.YES_NO_OPTION);
						if(n!=JOptionPane.YES_OPTION){
							return;
						}
					}
				      if (!file.getName().endsWith(".txt")) {
				         file = new File(file.getAbsolutePath() + ".txt");
				      }
				      try {
						FileWriter fw = new FileWriter(file.getAbsolutePath());
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(textArea.getText());
						bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				      
				}
				// TODO Auto-generated method stub
				
			}
			
		};
		return save;
	}
	
	}
