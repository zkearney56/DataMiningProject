package main.gui;

/**
 * Author: Zachary Kearney
 * Last Edited: 12/1/2015
 * Initial GUI loaded upon startup. Allows you to load a new csv file.
 */

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class InitGui extends JFrame {

	private JPanel contentPane;
	private DataPanel dataPanel;
	private boolean initialized = false;
	private JMenuItem mntmExportCsv;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InitGui frame = new InitGui();
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
	public InitGui() {
		setTitle("Decision Tree GUI");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 657, 455);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenCsv = new JMenuItem("Open CSV");
		
		mntmOpenCsv.addActionListener(open());
		
		mnFile.add(mntmOpenCsv);
		
		mntmExportCsv = new JMenuItem("Export Attributes");
		mntmExportCsv.addActionListener(export());
		mnFile.add(mntmExportCsv);
		mntmExportCsv.setEnabled(false);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0){
				InitGui.this.dispose();
			}
		});
		mnFile.add(mntmExit);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				JOptionPane.showMessageDialog(InitGui.this,
						"CPSC 464\n" +
						"Decision Tree Algorithm Project\n" +
					    "Created By Zachary Kearney and Dan Martin",
					    "About",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		mnFile.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

	}
	
	/**
	 * ActionListener to open a csv file.
	 * @return
	 */
	
	private ActionListener open(){
		ActionListener open = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		File file = ActionListeners.open(InitGui.this);
		if(ActionListeners.getExtension(file).equals("csv")){
			if(initialized){
        		contentPane.remove(dataPanel);
        	}
    		dataPanel = new DataPanel(file);
    		contentPane.add(dataPanel);
    		initialized = true;
    		mntmExportCsv.setEnabled(true);
		}
		else	{
			JOptionPane.showMessageDialog(InitGui.this,
        		    "Please select a valid csv file.",
        		    "Invalid File Selection",
        		    JOptionPane.ERROR_MESSAGE);
		}
	}
	};
	return open;
	}

	/**
	 * ActionListener to export attributes as a csv file.
	 * @return
	 */
	
	private ActionListener export(){
		
		
		ActionListener export = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ActionListeners.exportAttributes(dataPanel.getList(), InitGui.this);
			}
		};
		return export;
	}
	}
