package main.gui;
/**
 * Author: Zachary Kearney
 * Last Edited: 11/30/2015
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.algorithm.Algorithm;
import main.algorithm.Entropy;
import main.algorithm.Gini;
import main.structure.DataList;
import main.tree.DecisionTree;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class ResultGui extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ResultGui(Object[] dataSets, String algorithm) {
		
		DataList trainingSet = (DataList)dataSets[0];
		DataList testSet = (DataList)dataSets[0];
		
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
		t.trainTree();
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

		setTitle("Results");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		System.out.println("Pause");
	}
}
