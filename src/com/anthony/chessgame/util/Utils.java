package com.anthony.chessgame.util;

import java.util.ArrayList;

import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.piece.Piece;
import com.anthony.chessgame.piece.Piece.colorPiece;
import com.anthony.chessgame.piece.Piece.typePiece;

public class Utils {

	//EVOLVED GETTER for accessing one given PIECE, and/or his parameters, on a given BOARD
	public static Piece getPiece(ArrayList<Piece> B,int P){return B.get(P);} 
	public static Piece getPiece(ArrayList<Piece> B,int Px,int Py){return B.get(getPos(Px,Py));}
	public static String getPieceN(ArrayList<Piece> B,int P){return getPiece(B,P).getName();}
	public static String getPieceN(ArrayList<Piece> B,int Px,int Py){return getPiece(B,Px,Py).getName();}
	public static colorPiece getPieceC(ArrayList<Piece> B,int P){return (B.get(P)).getColor();} 

	//Transforms and Returns COORDINATES into POSITION 
	public static int getPos(int Px, int Py){
		if (Px>=0&&Py>=0&&Px<8&&Py<8) return 8*Py+Px;
		else return 64;
	}

	//Clears THREATEN for every PIECE on a given BOARD
	public static void resetThreaten(ArrayList<Piece> B){
		for (int i=0;i<64;i++)
		{
			(B.get(i)).clearThreaten();
		}
	}
	//SETTER for THREATENING and THREATEN for every PIECE of a given BOARD
	public static void setThreatsOnBoard(ArrayList<Piece> B){
		resetThreaten(B);
		for (int t=0;t<64;t++)
		{
			if (!isVoid(B,t)) (B.get(t)).setThreats(B);
		}
	}

	//Clones and Returns the copy of a given BOARD, to keep a BACKUP 
	public static ArrayList <Piece> cloneAL(ArrayList<Piece> B1){
		ArrayList <Piece> B2 = new ArrayList <Piece>();
		for (int i=0;i<65;i++){ B2.add(B1.get(i));}
		return B2;
	}

	//Returns true if the PIECE at P is of COLOR W(0 for BLACK, 1 for WHITE)
	public static Boolean comparePieceC(ArrayList<Piece> B,int P,boolean W){
		Boolean myW = B.get(P).isWhite();
		if (myW == null) return false;
		else return myW == W;
	}
	//Returns true if the PIECE at (Px,Py) is of COLOR W(0 for BLACK, 1 for WHITE)
	public static Boolean comparePieceC(ArrayList<Piece> B,int Px,int Py,boolean W){
		return comparePieceC(B,Utils.getPos(Px, Py),W);
	}

	//Returns true if the PIECE at P is VOID[NOTHING]
	public static boolean isVoid(ArrayList<Piece> B,int P){return (((B.get(P)).getType()) == typePiece.N);}
	//Returns true if the PIECE at (Px,Py) is VOID[NOTHING]
	public static boolean isVoid(ArrayList<Piece> B,int Px,int Py){return isVoid(B,Utils.getPos(Px,Py));}
	//Returns true if the PIECE at P is White
	public static boolean isWhite(ArrayList<Piece> B,int P){return B.get(P).isWhite();}
	//Returns true if the PIECE at (Px,Py) is White
	public static boolean isWhite(ArrayList<Piece> B,int Px,int Py){return isWhite(B,Utils.getPos(Px,Py));}
	//Returns true if the PIECE at P is King
	public static boolean isKing(ArrayList<Piece> B,int P){return (((B.get(P)).getType()) == typePiece.K);}
	//Returns true if the PIECE at P is White King
	public static boolean isKingW(ArrayList<Piece> B,int P){return( (isKing(B,P)) && (B.get(P).isWhite()) );}
	//Returns true if the PIECE at P is Black King
	public static boolean isKingB(ArrayList<Piece> B,int P){return( (isKing(B,P)) && !(B.get(P).isWhite()) );}
	//Returns true if the PIECE at P is Rook
	public static boolean isRook(ArrayList<Piece> B,int P){return (((B.get(P)).getType()) == typePiece.R);}
	//Returns true if the PIECE at P is Rook and has Special Move
	public static boolean isRookAndSpecial(ArrayList<Piece> B,int P){return( (isRook(B,P)) && !(B.get(P).hasSpecialMove()) );}


	//Given a PIECE, returns true if Attacked by an Foe PIECE
	public static boolean isThreaten(Piece P){
		ArrayList<Piece>T = P.getThreaten();
		boolean check = false;
		Boolean myW = P.isWhite();
		if (myW == null) return false;
		for (int i=0;i<T.size();i++)
		{
			if (comparePieceC(T,i,!myW)) check = true;
		}
		return check;
	}

	//Returns true if the KING of the PLAYER J is attacked[PLAYER in CHESS]
	public static boolean isInCheck(ArrayList<Piece> B,Player J,int posK1, int posK2){
		Piece king=null;
		if (J.isWhite()) king=getPiece(B,posK1);
		else if (!(J.isWhite())) king=getPiece(B,posK2);
		return isThreaten(king);
	}
}
