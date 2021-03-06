package com.anthony.chessgame.piece;
import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.util.Utils;

//Class representing KNIGHT
public class Knight extends Piece {

	/**
	 * CONSTRUCTOR : create a PIECE of COLOR C at POSITION P, and his NAME becomes "Nw"/"Nb" 
	 * @param P
	 * @param C
	 */
	public Knight(int P,colorPiece C) {
		super(P);
		definePiece(TypePiece.Kn,C);
	}
	
	@Override
	/*
	  Movement allowed, 8 destination cases : L-like move 2/1 in one direction, 1/2 orthogonally
      RETURNS true if the move is allowed
	 */
	public boolean checkMove(int Px, int Py,Player J,Piece[] B){
		int Dx = Px - getPosx();
		int Dy = Py - getPosy();
		boolean W = J.isWhite();
		if ((Math.abs(Dx) ==1 && Math.abs(Dy) ==2)||(Math.abs(Dx) ==2 && Math.abs(Dy) ==1))
		{
			if (!Utils.comparePieceC(B,Px,Py,W)) return true;
			else return false;
		}
		else return false;
	}
	@Override
	/*
	  SETTER for THREATENING : contains the destination PIECE(friendly or not)
	  If destination if out of the board, puts an OutOfBoard object instead(NAME "XX")
	  8 concrete PIECE threaten
	 */
	public boolean setThreats(Piece[] B)
	{
		clearThreatening();
		clearPossibleMoves();
		int X = getPosx();	
		int Y = getPosy();
		int X2 = X;
		int Y2 = Y;
		int Dx[] = {-2,-1,1,2,2,1,-1,-2};
		int Dy[] = {1,2,2,1,-1,-2,-2,-1};
		for (int i=0;i<BOARD_SIZE;i++)
		{
			X2 = X+Dx[i];
			Y2 = Y+Dy[i];
			Piece obstacle = Utils.getPiece(B,X2,Y2);
			if ( ((obstacle.getType()) == TypePiece.N) || ((obstacle.isWhite()!=null) && (isWhite()!=obstacle.isWhite())) ) {
				possibleMoves.add(obstacle.getPos());
			}
			addThreatening(Utils.getPiece(B,X2,Y2));
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
}