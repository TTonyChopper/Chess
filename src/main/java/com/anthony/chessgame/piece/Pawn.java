package com.anthony.chessgame.piece;
import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.game.SpecialMoveObserver;
import com.anthony.chessgame.util.Utils;

//Class representing PAWN
public class Pawn extends Piece {

	SpecialMoveObserver obs = null;
	
	/**
	 * CONSTRUCTOR : create a PIECE of COLOR C at POSITION P, and his NAME becomes "Pw"/"Pb"
	 * @param P
	 * @param C
	 */
	public Pawn(int P,colorPiece C) {
		super(P);
		definePiece(TypePiece.P,C);
	}

	/**
	 * Returns true if Passing conditions are met
	 * @param B
	 * @param Px
	 * @param Py
	 * @param W
	 * @return
	 */
	private boolean isEdible(Piece[] B,int Px,int Py,boolean W){
		boolean result = false;
		Piece foePawn = obs.getFoePawn(W);
		Py = (W) ? Py-1 : Py+1;
		if (foePawn==Utils.getPiece(B,Px,Py)) result = true;
		return result;
	}
	@Override
	/*
	  Movement allowed, stopping to the first obstacle[non VOID] : Up/Down[COLOR],
	  or first element Up/Down on diagonal, Left or Right if there is an opponent to capture
	  RETURNS true if the move is allowed
	 */	
	public boolean checkMove(int Px,int Py,Player J,Piece[] B){
		int Dx = Px - getPosx();
		int Dy = Py - getPosy();
		boolean W = J.isWhite();
		boolean nevermoved = false;
		if ((W&&(getPosy()==1))||((!W)&&(getPosy()==6))) nevermoved=true;
		//System.out.println("nevermoved "+nevermoved);
		//System.out.println("Dx "+Dx+" Dy "+Dy);
		if (((W)&&(Dy==1)&&Math.abs(Dx) ==1)||((!W)&&(Dy==-1)&&Math.abs(Dx) ==1))
		{
			if (Utils.comparePieceC(B,Px,Py,!W)){
				return true;
			} else if (Utils.isVoid(B,Px,Py)){
				//Trying "En passant"
				if ((obs!=null) && (isEdible(B,Px,Py,W))) {
					return true;
				}
			}
		}
		else if (Dx==0)
		{
			if (W&&(Dy == 1)&&(Utils.isVoid(B,Px,Py))) return true;
			else if (W&&(nevermoved)&&(Dy == 2)&&((Utils.isVoid(B,Px,Py)))&&
					((Utils.isVoid(B,Px,Py-1)))) {
				fire(W);
				return true;
			} else if ((!W)&&(Dy == -1)&&((Utils.isVoid(B,Px,Py)))) return true;
			else if ((!W)&&(nevermoved)&&(Dy == -2)&&((Utils.isVoid(B,Px,Py)))&&
					((Utils.isVoid(B,Px,Py+1)))) {
				fire(W);
				return true;
			} else return false;	 
		}
		return false;
	}
	@Override
	/*
	  SETTER for THREATENING : contains the first obstacle(friendly or not)
	  If no piece is on the way, puts an OutOfBoard object instead(NAME "XX")
	  2 concrete PIECE threaten
	 */
	public boolean setThreats(Piece[] B)
	{
		clearThreatening();
		clearPossibleMoves();
		addThreatening(DiagL(B));
		addThreatening(DiagR(B));
		//Pawn can move vertically without threating
		if (getColor().getW()) {
			if (getPosy()==1) {
				checkCase(B,getPosx(),getPosy()+2,false);
			}
			checkCase(B,getPosx(),getPosy()+1,false);
		} else {
			if (getPosy()==6) {
				checkCase(B,getPosx(),getPosy()-2,false);
			}
			checkCase(B,getPosx(),getPosy()-1,false);
		}
		
		return false;
	}
	@Override
	/*
	  Returns number of potential moves
	 */
	public int scanPotentialMoves() {
		return possibleMoves.size();
	}	
	/**
	 * 
	 * @param B
	 * @param X
	 * @param Y
	 * @return
	 */
	private Piece checkCase(Piece[] B,int X, int Y,boolean isCapture) {
		if (!(Utils.isVoid(B,X,Y))) {
			Piece obstacle = Utils.getPiece(B,X,Y);
			//Warning : pawn can move only to capture vertically
			if ((isCapture) && (obstacle.isWhite()!=null) && (isWhite()!=obstacle.isWhite())) {
				possibleMoves.add(obstacle.getPos());
			}
			return Utils.getPiece(B,X,Y);
		} else {
			//Warning : pawn can move to void case only when not capturing
			if (!isCapture) possibleMoves.add(Utils.getPos(X,Y));
			return null;
		}
	}
	/**
	 * Looks for a PIECE on the Left diagonal
	 * @param B
	 * @return
	 */
	private Piece DiagL(Piece[] B)
	{
		Piece result;
		if (getColor().getW()) {
			result = Utils.getPiece(B,getPosx()-1,getPosy()+1);
			checkCase(B,getPosx()-1,getPosy()+1,true);
		} else {
			result = Utils.getPiece(B,getPosx()-1,getPosy()-1);
			checkCase(B,getPosx()-1,getPosy()-1,true);
		}
		return result;
	}
	/**
	 * Looks for a PIECE on the Right diagonal
	 * @param B
	 * @return
	 */
	private Piece DiagR(Piece[] B)
	{
		Piece result;
		if (getColor().getW()) {
			result = Utils.getPiece(B,getPosx()+1,getPosy()+1);
			checkCase(B,getPosx()+1,getPosy()+1,true);
		} else {
			result = Utils.getPiece(B,getPosx()+1,getPosy()-1);
			checkCase(B,getPosx()+1,getPosy()-1,true);
		}
		return result;
	}	
	/**
	 * 
	 * @param l
	 */
	public void setSpecialMoveListener(SpecialMoveObserver l){
		obs = l;
	}
	/**
	 * 
	 * @param l
	 */
	public void removeSpecialMoveListener(SpecialMoveObserver l){
		obs = null;
	}
	/**
	 * 
	 * @param W
	 */
	private void fire(boolean W) {
		if (obs != null) obs.bigLeapSpawnSpotted(this,W);
	}
}