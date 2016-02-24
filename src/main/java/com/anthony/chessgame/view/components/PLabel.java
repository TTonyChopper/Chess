package com.anthony.chessgame.view.components;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.anthony.chessgame.piece.Piece;

public class PLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2856733113022275577L;
	Piece p = null;
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
}
