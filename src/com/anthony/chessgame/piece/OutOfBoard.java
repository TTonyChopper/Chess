package com.anthony.chessgame.piece;

import java.util.ArrayList;

import com.anthony.chessgame.game.Player;

public class OutOfBoard extends Piece{

	/**
	 * CONSTRUCTOR : create a PIECE of COLOR C=0[NEUTRAL] at POSITION 64, and his NAME becomes "XX" 
	 */
	public OutOfBoard()
	{
		super(64);
		definePiece(typePiece.O,colorPiece.NONE);
	}

	@Override
	/**
	 * 
	 */
	public boolean checkMove(int Px, int Py, boolean W, Player J, ArrayList<Piece> B) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	/**
	 * 
	 */
	public boolean setThreats(ArrayList<Piece> B) {
		// TODO Auto-generated method stub
		return false;
	}
}
