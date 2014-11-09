package com.anthony.chessgame;

import java.util.ArrayList;
	
public class OutOfBoard extends Piece{

//CONSTRUCTOR : create a PIECE of COLOR C=0[NEUTRAL] at POSITION 64, and his NAME becomes "XX"
public OutOfBoard()
{
	super(64);
	definePiece(typePiece.O,colorPiece.NONE);
}

//OVERRIDE
@Override
public boolean setThreats(ArrayList<Piece> B) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public boolean checkMove(int Px, int Py, boolean W, Player J, ArrayList<Piece> B) {
	// TODO Auto-generated method stub
	return false;
}

}
