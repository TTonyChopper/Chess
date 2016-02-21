package com.anthony.chessgame.piece;
import java.util.ArrayList;

import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.util.Utils;

//Class representing BISHOP
public class Bishop extends Piece {

	/**
	 * CONSTRUCTOR : create a PIECE of COLOR C at POSITION P, and his NAME becomes "Qw"/"Qb"
	 * @param P
	 * @param C
	 */
	public Bishop(int P,colorPiece C){
		super(P);
		definePiece(typePiece.Bb,C);
	}  

	@Override
	/**
	 * Movement allowed, stopping to the first obstacle[non VOID] : diagonals UP LEFT-UP RIGHT-DOWN RIGHT-DOWN LEFT
	 * RETURNS true if the move is allowed
	 */
	public boolean checkMove(int Px, int Py,boolean W,Player J,ArrayList<Piece> B){
		int Dx = Px - getPosx();
		int Dy = Py - getPosy();
		boolean moveok = false;
		if (Dx==-Dy)
		{
			if (Dx<0) moveok=checkMoveDiagUL(Px,Py,Dx,Dy,W,B);
			else if (Dx>0) moveok=checkMoveDiagDR(Px,Py,Dx,Dy,W,B);
		}
		else if (Dx==Dy)
		{
			if (Dx>0) moveok=checkMoveDiagUR(Px,Py,Dx,Dy,W,B);
			else if (Dx<0) moveok=checkMoveDiagDL(Px,Py,Dx,Dy,W,B);
		}

		//Finaltest: destination case
		if (Utils.comparePieceC(B,Px,Py,W)) moveok=false;

		return moveok;
	}

	
	@Override
	/**
	 * SETTER for THREATENING : contains the first obstacle(friendly or not)
	 * If no piece is on the way, puts an OutOfBoard object instead(NAME "XX") 
	 * 4 concrete PIECE threaten
	 */
	public boolean setThreats(ArrayList <Piece> B)
	{
		clearThreatening();
		addThreatening(DiagUL(B));
		addThreatening(DiagUR(B));
		addThreatening(DiagDR(B));
		addThreatening(DiagDL(B));
		clearPossibleMoves();
		return false;
	}

	/**
	 * Checks move on the Up Left diagonal
	 * @param Px
	 * @param Py
	 * @param Dx
	 * @param Dy
	 * @param W
	 * @param B
	 * @return
	 */
	private boolean checkMoveDiagUL(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
		//Dx==-Dy  Dy>0
		boolean obstacle = false;
		for (int i=1;i<Dy;i++)
		{
			if (!(Utils.isVoid(B,Px+i,Py-i))) obstacle=true;
		}
		return !obstacle;
	}
	/**
	 * Checks move on the Up Right diagonal
	 * @param Px
	 * @param Py
	 * @param Dx
	 * @param Dy
	 * @param W
	 * @param B
	 * @return
	 */
	private boolean checkMoveDiagUR(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
		//Dx==Dy  Dy>0
		boolean obstacle = false;
		for (int i=1;i<Dy;i++)
		{
			if (!(Utils.isVoid(B,Px-i,Py-i))) obstacle=true;
		}
		return !obstacle;
	}
	/**
	 * Checks move on the Down Right diagonal
	 * @param Px
	 * @param Py
	 * @param Dx
	 * @param Dy
	 * @param W
	 * @param B
	 * @return
	 */
	private boolean checkMoveDiagDR(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
		//Dx==-Dy  Dx>0
		boolean obstacle = false;
		for (int i=1;i<Dy;i++)
		{
			if (!(Utils.isVoid(B,Px-i,Py+i))) obstacle=true;
		}
		return !obstacle;
	}
	/**
	 * Checks move on the Down Left diagonal
	 * @param Px
	 * @param Py
	 * @param Dx
	 * @param Dy
	 * @param W
	 * @param B
	 * @return
	 */
	private boolean checkMoveDiagDL(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
		//Dx==Dy  Dx<0
		boolean obstacle = false;
		for (int i=-1;i>Dx;i--)
		{
			if (!(Utils.isVoid(B,Px-i,Py-i))) obstacle=true;
		}
		return !obstacle;
	}

	/**
	 * Checks obstacle on the Up Left diagonal
	 * @param B
	 * @return
	 */
	private Piece DiagUL(ArrayList <Piece> B){
		int X = getPosx();	
		int Y = getPosy();
		for (int i=0;i<8;i++)
		{
			X--;
			Y++;
			if (!(Utils.isVoid(B,X,Y))) return Utils.getPiece(B,X,Y);
		}
		return Utils.getPiece(B,8,8);
	}
	/**
	 * Checks obstacle on the Up Right diagonal
	 * @param B
	 * @return
	 */
	private Piece DiagUR(ArrayList <Piece> B){
		int X = getPosx();	
		int Y = getPosy();
		for (int i=0;i<8;i++)
		{
			X++;
			Y++;
			if (!(Utils.isVoid(B,X,Y))) return Utils.getPiece(B,X,Y);
		}
		return Utils.getPiece(B,8,8);
	}
	/**
	 * Checks obstacle on the Down Right diagonal
	 * @param B
	 * @return
	 */
	private Piece DiagDR(ArrayList <Piece> B){
		int X = getPosx();	
		int Y = getPosy();
		for (int i=0;i<8;i++)
		{
			X++;
			Y--;
			if (!(Utils.isVoid(B,X,Y))) return Utils.getPiece(B,X,Y);
		}
		return Utils.getPiece(B,8,8);
	}
	/**
	 * Checks obstacle on the Down Left diagonal
	 * @param B
	 * @return
	 */
	private Piece DiagDL(ArrayList <Piece> B){
		int X = getPosx();	
		int Y = getPosy();
		for (int i=0;i<8;i++)
		{
			X--;
			Y--;
			if (!(Utils.isVoid(B,X,Y))) return Utils.getPiece(B,X,Y);
		}
		return Utils.getPiece(B,8,8);
	}
}