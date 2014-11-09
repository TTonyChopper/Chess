package com.anthony.chessgame;

import java.util.ArrayList;

//Class representing VOID case
public class Nothing extends Piece {

	//CONSTRUCTOR : create a PIECE of COLOR C=0[NEUTRAL] at POSITION P, and his NAME stays "  " from PIECE 
	public Nothing(int P,colorPiece C) {
		super(P);
		definePiece(typePiece.N,C);
	}

	//CONSTRUCTOR : create a PIECE of COLOR C=0[NEUTRAL] at COORDINATES(PX,PY), and his NAME stays "  " from PIECE 
	public Nothing(int Px,int Py,colorPiece C) {
		super(8*Py+Px);
		definePiece(typePiece.N,C);
	}

	//OVERRIDE
	@Override
	public boolean checkMove(int Px, int Py, boolean W, Player J, ArrayList<Piece> B) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean setThreats(ArrayList<Piece> B) {
		// TODO Auto-generated method stub
		return false;
	}
}