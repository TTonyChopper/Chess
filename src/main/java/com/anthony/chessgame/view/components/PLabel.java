package com.anthony.chessgame.view.components;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

import com.anthony.chessgame.piece.Piece;

public class PLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2856733113022275577L;
	Piece p;
	int pos;
	
	/**
	 * 
	 * @param i
	 * @param p
	 * @param pos
	 */
	public PLabel(ImageIcon i,Piece p,int pos) {
		super(i);
		this.p = p;
		this.pos = pos;
	}
	
	/**
	 * 
	 * @return
	 */
	public Piece getPiece () {
		return p;
	}
	/**
	 * 
	 * @return
	 */
	public int getPos() {
		return pos;
	}
	public void select() {
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));	
	}
	public void unselect() {
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}
}
