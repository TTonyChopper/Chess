package com.anthony.chessgame.view.components;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class MovesPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1936343202046900296L;
	int moveCounter;
	
	public MovesPanel() {
		super();
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(20,10,10,20));
		initComponents();
	}
	
	private void initComponents() {
		JTextPane txt = new JTextPane();
		txt.setEditable(false);
		txt.setText("");
		JScrollPane paneScrollPane = new JScrollPane(txt);
        paneScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(paneScrollPane);
	}
}
