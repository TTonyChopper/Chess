package com.anthony.chessgame.piece;

import java.util.ArrayList;

import com.anthony.chessgame.game.Player;

//Class representing VOID case
public class Nothing extends Piece {

	/**
	 * CONSTRUCTOR : create a PIECE of COLOR C=0[NEUTRAL] at POSITION P, and his NAME stays "  " from PIECE 
	 * @param P
	 * @param C
	 */
	public Nothing(int P,colorPiece C) {
		super(P);
		definePiece(typePiece.N,C);
	}
	/**
	 * CONSTRUCTOR : create a PIECE of COLOR C=0[NEUTRAL] at COORDINATES(PX,PY), and his NAME stays "  " from PIECE 
	 * @param Px
	 * @param Py
	 * @param C
	 */
	public Nothing(int Px,int Py,colorPiece C) {
		super(8*Py+Px);
		definePiece(typePiece.N,C);
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