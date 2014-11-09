package com.anthony.chessgame;

import java.util.ArrayList;

public class Utils {

	public static Piece getPiece(ArrayList<Piece> B,int Px,int Py){
		return B.get(Calc.getPos(Px,Py));
	}
	
	public static String getPieceN(ArrayList<Piece> B,int Px,int Py){
		return getPiece(B,Px,Py).getName();
	}
	
	public static String getPieceN(ArrayList<Piece> B,int P){
		int Px = P % 8 ;
		int Py = P / 8 ;
		return getPiece(B,Px,Py).getName();
	}
	
}
