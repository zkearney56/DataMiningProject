package main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Sscce extends JPanel{

	
public Sscce(){
	
    final JFrame rootframe = new JFrame("Time Series Mining");
    final JPanel mainPanel = new JPanel(new BorderLayout());
    rootframe.setSize(new Dimension(400,400));
    rootframe.setContentPane(mainPanel);
    mainPanel.setLayout(new BorderLayout());
    JPanel center=new JPanel(new GridLayout(2,1));
    JPanel forSpecific=new JPanel();
    forSpecific.setLayout(new BoxLayout(forSpecific, BoxLayout.X_AXIS));
    JPanel test1 = new JPanel();
    test1.setPreferredSize(new Dimension(1000,1000));
    forSpecific.add(test1);
    test1.setBackground(Color.white);
    final JScrollPane scrollSpecific = new JScrollPane(forSpecific);
    center.add(scrollSpecific);
    rootframe.add(center, BorderLayout.CENTER);
    rootframe.setVisible(true);
    
}
}