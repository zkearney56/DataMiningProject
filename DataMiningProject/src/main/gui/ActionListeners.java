package main.gui;

/**
 * @author Zachary Kearney
 * Contains ActionListeners for open, save, and export functions.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.structure.DataList;

public class ActionListeners {

	public static void exportData(DataList list, JFrame component) {

		FileNameExtensionFilter exportFilter = new FileNameExtensionFilter("Csv File", "csv");
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setApproveButtonText("Export");
		fc.setFileFilter(exportFilter);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnval = fc.showSaveDialog(component);
		if (returnval == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (file.exists()) {
				int n = JOptionPane.showConfirmDialog(component, "File Already Exists, Do You Want To OverWrite?",
						"File Already Exists", JOptionPane.YES_NO_OPTION);
				if (n != JOptionPane.YES_OPTION) {
					return;
				}
			}
			if (!file.getName().endsWith(".csv")) {
				file = new File(file.getAbsolutePath() + ".csv");
			}
			list.writeToCSV(file.getAbsolutePath());
		}
	}

	public static void exportAttributes(DataList list, JFrame component) {

		FileNameExtensionFilter exportFilter = new FileNameExtensionFilter("Csv File", "csv");
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setApproveButtonText("Export");
		fc.setFileFilter(exportFilter);
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnval = fc.showSaveDialog(component);
		if (returnval == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (file.exists()) {
				int n = JOptionPane.showConfirmDialog(component, "File Already Exists, Do You Want To OverWrite?",
						"File Already Exists", JOptionPane.YES_NO_OPTION);
				if (n != JOptionPane.YES_OPTION) {
					return;
				}
			}
			if (!file.getName().endsWith(".csv")) {
				file = new File(file.getAbsolutePath() + ".csv");
			}
			list.attributeToCsv(file.getAbsolutePath());
		}

	}

	public static void save(JTextArea textArea, JFrame component) {

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Text File", "txt");
		JFileChooser fcsave = new JFileChooser();
		fcsave.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fcsave.setApproveButtonText("Save");
		fcsave.setFileFilter(filter);
		fcsave.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnval = fcsave.showSaveDialog(component);
		if (returnval == JFileChooser.APPROVE_OPTION) {
			File file = fcsave.getSelectedFile();
			if (file.exists()) {
				int n = JOptionPane.showConfirmDialog(component, "File Already Exists, Do You Want To OverWrite?",
						"File Already Exists", JOptionPane.YES_NO_OPTION);
				if (n != JOptionPane.YES_OPTION) {
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
	}

	public static File open(JFrame frame) {
		File file = new File("null");
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		FileFilter filter = new FileNameExtensionFilter("CSV File", "csv");
		fc.addChoosableFileFilter(filter);
		fc.setDialogTitle("Import from CSV");
		fc.setApproveButtonText("Open");
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
		int returnVal = fc.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fc.getSelectedFile();
			return file;
		}
		return file;
	}

	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

}
