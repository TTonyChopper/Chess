package com.anthony.chessgame.piece;
import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.util.Utils;

//Class representing ROOK
public class Rook extends Piece {

	//false ; set to true after first move[condition for Castling]
	private boolean immobile;
	////1 for column a ; 2 for column h
	//private int number;

	/**
	 * CONSTRUCTOR : create a PIECE of COLOR C at POSITION P, and his NAME becomes "Rw"/"Rb", it is IMMOBILE(false) on creation 
	 * @param P
	 * @param C
	 */
	public Rook(int P,colorPiece C) {
		super(P);
		definePiece(TypePiece.R,C);
		immobile = true;
	}

	/**
	 * 
	 */
	public boolean hasSpecialMove(){return immobile;}
	/**
	 * One-way SETTER in case it moves
	 */
	public boolean loseSpecialMove(){return immobile=false;}

	@Override
	/**
	 * Movement allowed, stopping to the first obstacle[non VOID] : TOP-DOWN-LEFT-RIGHT
	 * RETURNS true if the move is allowed
	 */
	public boolean checkMove(int Px, int Py,Player J,Piece[] B){
		int Dx = Px - getPosx();
		int Dy = Py - getPosy();
		boolean W = J.isWhite();
		boolean moveok = false;
		//Looking for an obstacle(friendly or not[non VOID]) on the way
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

		//Test final : case de destination
		if (Utils.comparePieceC(B,Px,Py,W)) moveok = false;

		return moveok;
	}
	
	@Override
	/**
	 * SETTER for THREATENING : contains the first obstacle(friendly or not)
	 * If no piece is on the way, puts an OutOfBoard object instead(NAME "XX") 
	 * 4 concrete PIECE threaten 
	 */
	public boolean setThreats(Piece[] B){
		clearThreatening();
		clearPossibleMoves();
		addThreatening(LineL(B));
		addThreatening(ColumnU(B));
		addThreatening(LineR(B));
		addThreatening(ColumnD(B));
		return false;
	}
	
	@Override
	/**
	 * Returns number of potential moves
	 */
	public int scanPotentialMoves() {
		return possibleMoves.size();
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
	private boolean checkMoveColumnU(int Px, int Py,int Dx, int Dy,boolean W,Piece[] B){
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
	private boolean checkMoveColumnD(int Px, int Py,int Dx, int Dy,boolean W,Piece[] B){
		//Dx==0 et Dy<0
		boolean obstacle = false;
		for (int i=-1;i>Dy;i--)
		{
			if (!(Utils.isVoid(B,Px,Py-i))) obstacle=true;
		}
		return !obstacle;
	}	
	/**
	 * Checks move on the left
	 * @param Px
	 * @param Py
	 * @param Dx
	 * @param Dy
	 * @param W
	 * @param B
	 * @return
	 */
	private boolean checkMoveLineL(int Px, int Py,int Dx, int Dy,boolean W,Piece[] B){
		//Dx<0 et Dy==0
		boolean obstacle = false;
		for (int i=-1;i>Dx;i--)
		{
			if (!(Utils.isVoid(B,Px-i,Py))) obstacle=true;
		}
		return !obstacle;
	}	
	/**
	 * Checks move on the right
	 * @param Px
	 * @param Py
	 * @param Dx
	 * @param Dy
	 * @param W
	 * @param B
	 * @return
	 */
	private boolean checkMoveLineR(int Px, int Py,int Dx, int Dy,boolean W,Piece[] B){
		//Dx>0 et Dy==0
		boolean obstacle = false;
		for (int i=1;i<Dx;i++)
		{
			if (!(Utils.isVoid(B,Px-i,Py))) obstacle=true;
		}
		return !obstacle;
	}	
	/**
	 * 
	 * @param B
	 * @param X
	 * @param Y
	 * @return
	 */
	private Piece checkCase(Piece[] B,int X, int Y) {
		if (!(Utils.isVoid(B,X,Y))) {
			Piece obstacle = Utils.getPiece(B,X,Y);
			if ((obstacle.isWhite()!=null) && (isWhite()!=obstacle.isWhite())) {
				possibleMoves.add(obstacle.getPos());
			}
			return Utils.getPiece(B,X,Y);
		} else {
			possibleMoves.add(Utils.getPos(X,Y));
			return null;
		}
	}
	/**
	 * Checks obstacle upward
	 * @param B
	 * @return
	 */
	private Piece ColumnU(Piece[] B)
	{
		int i;
		Piece obstacle = null;
		for (i = getPosy()+1;(i<BOARD_SIZE)&&(obstacle==null);i++) {
			obstacle = checkCase(B,getPosx(),i);
		}
		if (obstacle == null) obstacle = Utils.getPiece(B,BOARD_SIZE,BOARD_SIZE);
		return obstacle;
	}
	/**
	 * Checks obstacle downward
	 * @param B
	 * @return
	 */
	private Piece ColumnD(Piece[] B)
	{
		int i;
		Piece obstacle = null;
		for (i = getPosy()-1;(i>-1)&&(obstacle==null);i--)
		{ 
			obstacle = checkCase(B,getPosx(),i);
		}
		if (obstacle == null) obstacle = Utils.getPiece(B,BOARD_SIZE,BOARD_SIZE);
		return obstacle;
	}
	/**
	 * Checks obstacle on the left
	 * @param B
	 * @return
	 */
	private Piece LineL(Piece[] B)
	{
		int i;
		Piece obstacle = null;
		for (i = getPosx()-1;(i>-1)&&(obstacle==null);i--)
		{  
			obstacle = checkCase(B,i,getPosy());
		}
		if (obstacle == null) obstacle = Utils.getPiece(B,BOARD_SIZE,BOARD_SIZE);
		return obstacle;	
	}
	/**
	 * Checks obstacle on the right
	 * @param B
	 * @return
	 */
	private Piece LineR(Piece[] B)
	{
		int i;
		Piece obstacle = null;
		for (i = getPosx()+1;(i<BOARD_SIZE)&&(obstacle==null);i++)
		{ 
			obstacle = checkCase(B,i,getPosy());
		}
		if (obstacle == null) obstacle = Utils.getPiece(B,BOARD_SIZE,BOARD_SIZE);
		return obstacle;
	}
}