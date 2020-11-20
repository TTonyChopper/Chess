package com.anthony.chessgame.util;

import java.util.ArrayList;
import java.util.List;

import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.piece.Piece;
import com.anthony.chessgame.piece.Piece.colorPiece;
import com.anthony.chessgame.piece.Piece.TypePiece;

public class Utils {

	//EVOLVED GETTER for accessing one given PIECE, and/or his parameters, on a given BOARD
	/**
	 * 
	 * @param B
	 * @param P
	 * @return
	 */
	public static Piece getPiece(Piece[] B,int P){return B[P];} 
	/**
	 * 
	 * @param B
	 * @param Px
	 * @param Py
	 * @return
	 */
	public static Piece getPiece(Piece[] B,int Px,int Py){return B[getPos(Px,Py)];}
	/**
	 * 
	 * @param B
	 * @param P
	 * @return
	 */
	public static String getPieceN(Piece[] B,int P){return getPiece(B,P).getName();}
	/**
	 * 
	 * @param B
	 * @param Px
	 * @param Py
	 * @return
	 */
	public static String getPieceN(Piece[] B,int Px,int Py){return getPiece(B,Px,Py).getName();}
	/**
	 * 
	 * @param B
	 * @param P
	 * @return
	 */
	public static colorPiece getPieceC(Piece[] B,int P){return B[P].getColor();} 
	/**
	 * Transforms and Returns COORDINATES into POSITION 
	 * @param Px
	 * @param Py
	 * @return
	 */
	public static int getPos(int Px, int Py){
		if (Px>=0&&Py>=0&&Px<Piece.BOARD_SIZE&&Py<Piece.BOARD_SIZE) return Piece.BOARD_SIZE*Py+Px;
		else return 64;
	}
	/**
	 * Clears THREATEN for every PIECE on a given BOARD
	 * @param B
	 */
	public static void resetThreaten(Piece[] B){
		for (int i=0;i<64;i++)
		{
			B[i].clearThreaten();
		}
	}
	/**
	 * SETTER for THREATENING and THREATEN for every PIECE of a given BOARD
	 * @param B
	 */
	public static void setThreatsOnBoard(Piece[] B){
		resetThreaten(B);
		for (int t=0;t<64;t++)
		{
			if (!isVoid(B,t)) B[t].setThreats(B);
		}
	}
	/**
	 * Clones and Returns the copy of a given BOARD, to keep a BACKUP 
	 * @param B1
	 * @return
	 */
	public static Piece[] cloneAL(Piece[] B1){
		Piece[] B2 = new Piece[B1.length];
		for (int i=0;i<65;i++){ B2[i] = B1[i];}
		return B2;
	}
	/**
	 * Returns true if the PIECE at P is of COLOR W(0 for BLACK, 1 for WHITE)
	 * @param B
	 * @param P
	 * @param W
	 * @return
	 */
	public static Boolean comparePieceC(ArrayList<Piece> B,int P,boolean W){
		Boolean myW = B.get(P).isWhite();
		if (myW == null) return false;
		else return myW == W;
	}
	/**
	 * Returns true if the PIECE at (Px,Py) is of COLOR W(0 for BLACK, 1 for WHITE)
	 * @param B
	 * @param Px
	 * @param Py
	 * @param W
	 * @return
	 */
	public static Boolean comparePieceC(ArrayList<Piece> B,int Px,int Py,boolean W){
		return comparePieceC(B,Utils.getPos(Px, Py),W);
	}
	/**
	 * Returns true if the PIECE at P is of COLOR W(0 for BLACK, 1 for WHITE)
	 * @param B
	 * @param P
	 * @param W
	 * @return
	 */
	public static Boolean comparePieceC(Piece[] B,int P,boolean W){
		Boolean myW = B[P].isWhite();
		if (myW == null) return false;
		else return myW == W;
	}
	/**
	 * Returns true if the PIECE at (Px,Py) is of COLOR W(0 for BLACK, 1 for WHITE)
	 * @param B
	 * @param Px
	 * @param Py
	 * @param W
	 * @return
	 */
	public static Boolean comparePieceC(Piece[] B,int Px,int Py,boolean W){
		return comparePieceC(B,Utils.getPos(Px, Py),W);
	}
	/**
	 * Returns true if the PIECE at P is VOID[NOTHING]
	 * @param B
	 * @param P
	 * @return
	 */
	public static boolean isVoid(Piece[] B,int P){return (B[P].getType()) == TypePiece.N;}
	/**
	 * Returns true if the PIECE at (Px,Py) is VOID[NOTHING]
	 * @param B
	 * @param Px
	 * @param Py
	 * @return
	 */
	public static boolean isVoid(Piece[] B,int Px,int Py){return isVoid(B,Utils.getPos(Px,Py));}
	/**
	 * Returns true if the PIECE at P is White
	 * @param B
	 * @param P
	 * @return
	 */
	public static boolean isWhite(Piece[] B,int P){return B[P].isWhite();}
	/**
	 * Returns true if the PIECE at (Px,Py) is White
	 * @param B
	 * @param Px
	 * @param Py
	 * @return
	 */
	public static boolean isWhite(Piece[] B,int Px,int Py){return isWhite(B,Utils.getPos(Px,Py));}
	/**
	 * Returns true if the PIECE at P is Pawn
	 * @param B
	 * @param P
	 * @return
	 */
	public static boolean isPawn(Piece[] B,int P){return ((B[P].getType()) == TypePiece.P);}
	/**
	 * Returns true if the PIECE at P is King
	 * @param B
	 * @param P
	 * @return
	 */
	public static boolean isKing(Piece[] B,int P){return ((B[P].getType()) == TypePiece.K);}
	/**
	 * Returns true if the PIECE at P is King
	 * @param B
	 * @param P
	 * @return
	 */
	public static boolean isKing(List<Piece> B,int P){return ((B.get(P).getType()) == TypePiece.K);}
	/**
	 * Returns true if the PIECE at P is White King
	 * @param B
	 * @param P
	 * @return
	 */
	public static boolean isKingW(List<Piece> B,int P){return( (isKing(B,P)) && (B.get(P).isWhite()) );}
	/**
	 * Returns true if the PIECE at P is Black King
	 * @param B
	 * @param P
	 * @return
	 */
	public static boolean isKingB(List<Piece> B,int P){return( (isKing(B,P)) && !(B.get(P).isWhite()) );}
	/**
	 * Returns true if the PIECE at P is Rook
	 * @param B
	 * @param P
	 * @return
	 */
	public static boolean isRook(Piece[] B,int P){return ((B[P].getType()) == TypePiece.R);}
	/**
	 * Returns true if the PIECE at P is Rook and has Special Move
	 * @param B
	 * @param P
	 * @return
	 */
	public static boolean isRookAndSpecial(Piece[] B,int P){return( (isRook(B,P)) && !(B[P].hasSpecialMove()) );}
	/**
	 * Given a PIECE, returns true if Attacked by an Foe PIECE
	 * @param P
	 * @return
	 */
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
	/**
	 * Returns true if the KING of the PLAYER J is attacked[PLAYER in CHECK]
	 * @param B
	 * @param J
	 * @param posK1
	 * @param posK2
	 * @return
	 */
	public static boolean isInCheck(Piece[] B,Player J,int posK1, int posK2){
		Piece king=null;
		if (J.isWhite()) king=getPiece(B,posK1);
		else if (!(J.isWhite())) king=getPiece(B,posK2);
		return isThreaten(king);
	}

	public static int FromNotToPos(String column, String row) {
		int a = column.charAt(0) - 'a';
		int b = row.charAt(0) - '0';
		return b == 9 ? 64 : (8-b)*8 + a;
	}

	public static int[] FromNot(String column,String row) {
		int a = column.charAt(0) - 'a';
		int b = row.charAt(0) - '0';
		int[]r = new int[2];
		r[0] = a;
		r[1] = (8-b);
		return r;
	}
}
