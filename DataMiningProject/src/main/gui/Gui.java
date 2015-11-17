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

public class Gui extends JFrame {

	private JPanel contentPane;
	private DataList dataList;
	private File file;
	JFileChooser fc;
	JScrollPane scrollPane;
	SetupPanel setupPanel;
	private JMenuItem mntmRun;
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
		setBounds(100, 100, 1171, 547);
		
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
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 11, 1145, 445);
		contentPane.add(panel);
		
	}
	
	void showData(){
		setupPanel = new SetupPanel(dataList);
		setupPanel.setBounds(0, 0, 1145, 241);
		scrollPane = new JScrollPane(setupPanel);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 10, 1145, 475);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);
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
