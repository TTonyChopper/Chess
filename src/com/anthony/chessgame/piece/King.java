package com.anthony.chessgame.piece;
import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.util.Utils;

//Class representing KING
public class King extends Piece {
	//true; set to false after first move[condition for Castling]
	private boolean still;

    /**
	 * CONSTRUCTOR : create a PIECE of COLOR C at POSITION P, and his NAME becomes "Kw"/"Kb", it is still(true) on creation 
	 * @param P
	 * @param C
	 */
	public King(int P,colorPiece C) {
		super(P);
		definePiece(TypePiece.K,C);
		still = true;
	}

	@Override
	/**
	 * King may castle as long as still is true
	 */
	public boolean hasSpecialMove(){return still;}
	@Override
	/**
	 * One-way SETTER in case it moves
	 */
	public boolean loseSpecialMove(){return still=false;}
	@Override
	/**
	 * Movement allowed, stopping to the first obstacle[non VOID] : all 8 adjacent cases 
	 * RETURNS true if the move is allowed
	 */
	public boolean checkMove(int Px, int Py,Player J,Piece[] B){
		int Dx = Px - getPosx();
		int Dy = Py - getPosy();
		boolean W = J.isWhite();
		if ((Math.abs(Dx)==1)&&(Math.abs(Dy)==1)||(Math.abs(Dx)==0)&&(Math.abs(Dy)==1)||(Math.abs(Dx)==1)&&(Math.abs(Dy)==0))
		{	
			if (!Utils.comparePieceC(B,Px,Py,W)) return true;
			else return false;
		}
		//Rock Right
		else if ((hasSpecialMove())&&(Dy==0)&&(Dx==2)&&((Utils.getPiece(B,Px+1,Py)).hasSpecialMove()))
		{

			boolean interpiecevide=true;
			for (int i =-1;i<1;i++)
			{
				if (!(Utils.isVoid(B,Px+i,Py))) interpiecevide= false ;
			}
			if (interpiecevide) 
			{  
				//Check if King route is safe!
				int pKing = getPos();
				boolean check = Utils.isThreaten(this);
				Piece[] Bmind = new Piece[B.length];
				for (int i=0;i<2;i++)
				{
					Bmind=Utils.cloneAL(B);
					moveKingInMind(pKing,pKing+1,Bmind);
					if (Utils.isThreaten(this)) check=true;
				}
				if (!check) return true;
				else return false;
			}
			else return false;
		}
		//Rock Left
		else if ((hasSpecialMove())&&(Dy==0)&&(Dx==-2)&&((Utils.getPiece(B,Px-2,Py)).hasSpecialMove()))
		{
			boolean voidInterpiece=true;
			for (int i =-1;i<2;i++)
			{
				if (!(Utils.isVoid(B,Px-i,Py))) voidInterpiece= false ;
			}
			if (voidInterpiece) 
			{  
				//Check if King route is safe!
				int pKing = getPos();
				boolean check = Utils.isThreaten(this);
				Piece[] Bmind = new Piece[B.length];
				for (int i=0;i<2;i++)
				{
					Bmind=Utils.cloneAL(B);
					moveKingInMind(pKing,pKing-1,Bmind);
					if (Utils.isThreaten(this)) check=true;
				}
				if (!check) return true;
				else return false;
			}
		}
		else return false;
		return false;
	}
	@Override
	/**
	 * SETTER for THREATENING : contains the first obstacle(friendly or not)
	 * If no piece is on the way, puts an OutOfBoard object instead(NAME "XX") 
	 * 8 concrete PIECE threaten
	 */
	public boolean setThreats(Piece[] B)
	{
		clearThreatening();
		clearPossibleMoves();
		int X = getPosx();	
		int Y = getPosy();
		int X2 = X;
		int Y2 = Y;
		int Dx[] = {-1,-1,0,1,1,1,0,-1};
		int Dy[] = {0,1,1,1,0,-1,-1,-1};
		for (int i=0;i<BOARD_SIZE;i++)
		{
			X2 = X+Dx[i];
			Y2 = Y+Dy[i];
			Piece obstacle = Utils.getPiece(B,X2,Y2);
			if ( ((obstacle.getType()) == TypePiece.N) || ((obstacle.isWhite()!=null) && (isWhite()!=obstacle.isWhite())) ) {
				possibleMoves.add(obstacle.getPos());
			}
			addThreatening(obstacle);
		}
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
	 * Functions needed to verify KING is not in Check if Castling 
	 * Same ones taken from CLASS CHESSGAME 
	 * @param p
	 * @param P
	 * @param Board
	 */
	private void setPiece(Piece p,int P,Piece[] Board){
		Board[P] = p;
		Board[P].setPos(P);
		Board[P].setCoord();
		Board[P].setLCoord();
	}
	/**
	 * Moves King to adjacent case, using a copy of the BOARD B, named BMIND 
	 * @param Pinit
	 * @param Pfinal
	 * @param Board
	 */
	private void moveKingInMind(int Pinit,int Pfinal,Piece[] Board){
		Piece moving = Board[Pinit];	

		//Moving pieces 	
		setPiece(new Nothing(Pinit,colorPiece.NONE),Pinit,Board);
		setPiece(moving,Pfinal,Board);
	}
}