package com.anthony.chessgame.piece;
import java.util.ArrayList;

import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.util.Utils;

//Class representing QUEEN on WHITE case
public class Queen extends Piece {

	/**
	 * CONSTRUCTOR : create a PIECE of COLOR C at POSITION P, and his NAME becomes "Qw"/"Qb"
	 * @param P
	 * @param C
	 */
	public Queen(int P,colorPiece C) {
		super(P);
		definePiece(typePiece.Q,C);
	}

	@Override
	/**
	 * Movement allowed, stopping to the first obstacle[non VOID] : TOP-DOWN-LEFT-RIGHT-UP LEFT-UP RIGHT-DOWN RIGHT-DOWN LEFT
	 * RETURNS true if the move is allowed
	 */
	public boolean checkMove(int Px, int Py,boolean W,Player J,ArrayList<Piece> B){
		int Dx = Px - getPosx();
		int Dy = Py - getPosy();
		boolean moveok = false;
		if (Dx==0)
		{
			if (Dy>0) moveok=checkMoveColumnU(Px,Py,Dx,Dy,W,B);
			else if (Dy<0) moveok=checkMoveColumnD(Px,Py,Dx,Dy,W,B);
		}
		else if (Dy==0)
		{
			if (Dx<0) moveok=checkMoveLineL(Px,Py,Dx,Dy,W,B);
			else if (Dx>0) moveok=checkMoveLineR(Px,Py,Dx,Dy,W,B);
		}  
		else if (Dx==-Dy)
		{
			if (Dx<0) moveok=checkMoveDiagUL(Px,Py,Dx,Dy,W,B);
			else if (Dx>0) moveok=checkMoveDiagDR(Px,Py,Dx,Dy,W,B);
		}
		else if (Dx==Dy)
		{
			if (Dx>0) moveok=checkMoveDiagUR(Px,Py,Dx,Dy,W,B);
			else if (Dx<0) moveok=checkMoveDiagDL(Px,Py,Dx,Dy,W,B);
		}

		//Test final : case de destination
		if (Utils.comparePieceC(B,Px,Py,W)) moveok = false;

		return moveok;
	}

	@Override
	/**
	 * SETTER for THREATENING : contains the first obstacle(friendly or not)
	 * If no piece is on the way, puts an OutOfBoard object instead(NAME "XX") 
	 * 8 concrete PIECE threaten
	 */
	public boolean setThreats(ArrayList <Piece> B){
		clearThreatening();
		addThreatening(LineL(B));
		addThreatening(DiagUL(B));
		addThreatening(ColumnU(B));
		addThreatening(DiagUR(B));
		addThreatening(LineR(B));
		addThreatening(DiagDR(B));
		addThreatening(ColumnD(B));	  
		addThreatening(DiagDL(B));
		clearPossibleMoves();
		return false;
	}


	/**
	 * Checks move Upward  
	 * @param Px
	 * @param Py
	 * @param Dx
	 * @param Dy
	 * @param W
	 * @param B
	 * @return
	 */
	private boolean checkMoveColumnU(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
		//Dx==0 et Dy>0
		boolean obstacle = false;
		for (int i=1;i<Dy;i++)
		{
			if (!(Utils.isVoid(B,Px,Py-i))) obstacle=true;
		}
		return !obstacle;
	}
	/**
	 * Checks move Downward 
	 * @param Px
	 * @param Py
	 * @param Dx
	 * @param Dy
	 * @param W
	 * @param B
	 * @return
	 */
	private boolean checkMoveColumnD(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
		//Dx==0 et Dy<0
		boolean obstacle = false;
		for (int i=-1;i>Dy;i--)
		{
			if (!(Utils.isVoid(B,Px,Py-i))) obstacle=true;
		}
		return !obstacle;
	}	
	/**
	 * Checks move on the Left
	 * @param Px
	 * @param Py
	 * @param Dx
	 * @param Dy
	 * @param W
	 * @param B
	 * @return
	 */
	private boolean checkMoveLineL(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
		//Dx<0 et Dy==0
		boolean obstacle = false;
		for (int i=-1;i>Dx;i--)
		{
			if (!(Utils.isVoid(B,Px-i,Py))) obstacle=true;
		}
		return !obstacle;
	}	
	/**
	 * Checks move on the Right 
	 * @param Px
	 * @param Py
	 * @param Dx
	 * @param Dy
	 * @param W
	 * @param B
	 * @return
	 */
	private boolean checkMoveLineR(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
		//Dx>0 et Dy==0
		boolean obstacle = false;
		for (int i=1;i<Dx;i++)
		{
			if (!(Utils.isVoid(B,Px-i,Py))) obstacle=true;
		}
		return !obstacle;
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
		for (int i=1;i<Dx;i++)
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
	 * Checks obstacle Upward
	 * @param B
	 * @return
	 */
	private Piece ColumnU(ArrayList <Piece> B){
		int i;
		for (i = getPosy()+1;i<8;i++)
		{
			if (!(Utils.isVoid(B,getPosx(),i))) return Utils.getPiece(B,getPosx(),i);
		}
		return Utils.getPiece(B,getPosx(),i);	
	}
	/**
	 * Checks obstacle Downward
	 * @param B
	 * @return
	 */
	private Piece ColumnD(ArrayList <Piece> B){
		int i;
		for (i = getPosy()-1;i>-1;i--)
		{ 
			if (!(Utils.isVoid(B,getPosx(),i))) return Utils.getPiece(B,getPosx(),i);
		}
		return Utils.getPiece(B,getPosx(),i);	
	}
	/**
	 * Checks obstacle on the Left
	 * @param B
	 * @return
	 */
	private Piece LineL(ArrayList <Piece> B){
		int i;
		for (i = getPosx()-1;i>-1;i--)
		{ 
			if (!(Utils.isVoid(B,i,getPosy()))) return Utils.getPiece(B,i,getPosy());
		}
		return Utils.getPiece(B,i,getPosy());	
	}
	/**
	 * Checks obstacle on the Right
	 * @param B
	 * @return
	 */
	private Piece LineR(ArrayList <Piece> B){
		int i;
		for (i = getPosx()+1;i<8;i++)
		{ 
			if (!(Utils.isVoid(B,i,getPosy()))) return Utils.getPiece(B,i,getPosy());
		}
		return Utils.getPiece(B,i,getPosy());
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