package main.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.structure.DataList;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class NewGui extends JFrame {

	private JPanel contentPane;
	private JFileChooser fc;
	private DataPanel dataPanel;
	private boolean initialized = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewGui frame = new NewGui();
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
	public NewGui() {
		setResizable(false);
		
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileFilter filter = new FileNameExtensionFilter("CSV File", "csv");
		fc.addChoosableFileFilter(filter);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 455);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenCsv = new JMenuItem("Open Csv");
		mntmOpenCsv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				            int returnVal = fc.showOpenDialog(NewGui.this);
				 
				            if (returnVal == JFileChooser.APPROVE_OPTION) {
				                File file = fc.getSelectedFile();
				                if(getExtension(file).equals("csv")){
				                	if(initialized){
				                		contentPane.remove(dataPanel);
				                	}
				            		dataPanel = new DataPanel(file);
				            		contentPane.add(dataPanel);
				            		initialized = true;
				                	System.out.println("Pause");
				                }
				                else{
				                	System.out.println("Err, invalid file");
				                }
				            } else {
				            }
					}
				});
		mnFile.add(mntmOpenCsv);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	

	}
	
	private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
