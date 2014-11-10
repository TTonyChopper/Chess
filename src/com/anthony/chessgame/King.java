package com.anthony.chessgame;
import java.util.ArrayList;

//Class representing KING
public class King extends Piece {
	//true; set to false after first move[condition for Castling]
	private boolean immobile;

	////1 for column a ; 2 for column h
	//private int number;

	//CONSTRUCTOR : create a PIECE of COLOR C at POSITION P, and his NAME becomes "Kw"/"Kb", it is IMMOBILE(true) on creation 
	public King(int P,colorPiece C) {
		super(P);
		definePiece(typePiece.K,C);
		immobile = true;
	}

	//OVERRIDE
	@Override
	public boolean hasSpecialMove(){return immobile;}
	//One-way SETTER in case it moves
	@Override
	public boolean loseSpecialMove(){return immobile=false;}

	//Movement allowed, stopping to the first obstacle[non VOID] : all 8 adjacent cases 
	//RETURNS true if the move is allowed
	@Override
	public boolean checkMove(int Px, int Py,boolean W,Player J,ArrayList<Piece> B){
		int Dx = Px - getPosx();
		int Dy = Py - getPosy();	
		if ((Math.abs(Dx)==1)&&(Math.abs(Dy)==1)||(Math.abs(Dx)==0)&&(Math.abs(Dy)==1)||(Math.abs(Dx)==1)&&(Math.abs(Dy)==0))
		{
			char A ;
			if (W) A = 'w';
			else A = 'b';	
			if(((Utils.getPieceN(B,Px,Py)).charAt(1))!=A)	return true;
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
				//Vérifier que les cases sont safe pour le King!
				int pKing = getPos();
				boolean check = Utils.isThreaten(this);
				ArrayList<Piece> Bmind = new ArrayList<Piece>();
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
			boolean interpiecevide=true;
			for (int i =-1;i<2;i++)
			{
				if (!(Utils.isVoid(B,Px-i,Py))) interpiecevide= false ;
			}
			if (interpiecevide) 
			{  
				//Vérifier que les cases sont safe pour le King!
				int pKing = getPos();
				boolean check = Utils.isThreaten(this);
				ArrayList<Piece> Bmind = new ArrayList<Piece>();
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
	//SETTER for THREATENING : contains the first obstacle(friendly or not)
	//If no piece is on the way, puts an OutOfBoard object instead(NAME "XX") 
	//8 concrete PIECE threaten
	@Override
	public boolean setThreats(ArrayList <Piece> B)
	{
		clearThreatening();
		int X = getPosx();	
		int Y = getPosy();
		int X2 = X;
		int Y2 = Y;
		int Dx[] = {-1,-1,0,1,1,1,0,-1};
		int Dy[] = {0,1,1,1,0,-1,-1,-1};
		for (int i=0;i<8;i++)
		{
			X2 = X+Dx[i];
			Y2 = Y+Dy[i];
			addThreatening(Utils.getPiece(B,X2,Y2));
		}
		return false;
	}

	
	//Functions needed to verify KING is not in Check if Castling 
	//Same ones taken from CLASS CHESSGAME
	private void setPiece(Piece p,int P,ArrayList<Piece> Board){
		Board.set(P,p);
		(getPiece(P,Board)).setPos(P);
		(getPiece(P,Board)).setCoord();
		(getPiece(P,Board)).setLCoord();
	}
	private Piece getPiece(int P,ArrayList <Piece>B){return B.get(P);} 
	//Moves King to adjacent case, using a copy of the BOARD B, named BMIND 
	private void moveKingInMind(int Pinit,int Pfinal,ArrayList <Piece> Board){
		Piece moving = getPiece(Pinit,Board);	

		//Moving pieces 	
		setPiece(new Nothing(Pinit,colorPiece.NONE),Pinit,Board);
		setPiece(moving,Pfinal,Board);
	}
}