package com.anthony.chessgame.view.components;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class StatusPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1936343202046900296L;
	
	public final static String s0 = "New game launched! ";
	public final static String s1 = " will play white, ";
	public final static String s2 = " will play black. ";
	public final static String s3 = "Let the game begin!\n";
	private String p1;
	private String p2;
	private JTextPane txt;
	private StringBuilder displayedText;
	
	public StatusPanel(String p1, String p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		displayedText = new StringBuilder();
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(10,20,20,10));
		initComponents();
	}
	
	private void initComponents() {
		txt = new JTextPane();
		displayedText.append(s0);
		displayedText.append(p1);
		displayedText.append(s1);
		displayedText.append(p2);
		displayedText.append(s2);
		displayedText.append(s3);
		txt.setEditable(false);
		txt.setText(displayedText.toString());
		JScrollPane paneScrollPane = new JScrollPane(txt);
        paneScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(paneScrollPane);
	}
	public void appendTextln(String s) {
		appendText(s+"\n");
	}
	public void appendText(String s) {
		displayedText.append(s);
		txt.setText(displayedText.toString());
	}
}
