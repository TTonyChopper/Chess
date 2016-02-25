package com.anthony.chessgame.view.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.anthony.chessgame.game.ChessGame;

public class PlayerDialog extends JDialog implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3166925832335867849L;
	
	private ChessGame c;
	private JTextField name;
	
	public PlayerDialog(boolean w,ChessGame c) {
		super();
		this.c=c;
		initComponents(w);
	}
		
	public void initComponents(boolean w){
		
		add(Box.createRigidArea(new Dimension(0, 10)));
		StringBuffer p = new StringBuffer(""); 
		if (w){
			p.append("Player 1 :");
		} else {
			p.append("Player 2 :");
		}
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(Box.createRigidArea(new Dimension(0, 10)));
		//element 1
		JLabel label = new JLabel("Enter a name and click \"ok\"");
		label.setAlignmentX(0.5f);
		add(label);
		add(Box.createRigidArea(new Dimension(0, 10)));
		//element 2
		JLabel player = new JLabel(p.toString());
		player.setFont(new Font("Serif", Font.BOLD, 13));
		player.setAlignmentX(0.5f);
		add(player);
		add(Box.createRigidArea(new Dimension(0, 20)));
		//element 3
		name = new JTextField();
		name.setAlignmentX(CENTER_ALIGNMENT);
		add(name);
		add(Box.createRigidArea(new Dimension(0, 20)));
		//element 4
		JButton close = new JButton("ok");
		close.addActionListener(this);
		close.setAlignmentX(0.5f);
		add(close);
		add(Box.createRigidArea(new Dimension(0, 10)));
		StringBuffer base = new StringBuffer("Enter a name");
		setTitle(base.toString());
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setSize(300, 200);
		setLocation(size.width/2-getWidth()/2,size.height/2-getHeight()/2);
	}
	private void terminate() {
		synchronized (c) {
			c.setHolder(name.getText());
			c.notifyAll();
		}
		dispose();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		terminate();
	}
}