package com.anthony.chessgame;
import java.util.ArrayList;
//Class representing KING
public class King extends Piece {
//false ; set to true after first move[condition for Castling]
private boolean immobile;

//CONSTRUCTOR : create a PIECE of COLOR C at POSITION P, and his NAME becomes "Kw"/"Kb", it is IMMOBILE(false) on creation 
public King(int P,int C) {
super(P,C);
immobile = true;
if (C==1) setName("Kw");
else setName("Kb");
}

//GETTER for IMMOBILE
public boolean isImmobile(){return immobile;}
//One-way SETTER in case it moves
public boolean isMobile(){return immobile=false;}

//Movement allowed, stopping to the first obstacle[non VOID] : all 8 adjacent cases 
//RETURNS true if the move is allowed
public boolean checkMove(int Px, int Py,boolean W,Player J,ArrayList<Piece> B){
int Dx = Px - getPosx();
int Dy = Py - getPosy();	
if ((Math.abs(Dx)==1)&&(Math.abs(Dy)==1)||(Math.abs(Dx)==0)&&(Math.abs(Dy)==1)||(Math.abs(Dx)==1)&&(Math.abs(Dy)==0))
{
	char A ;
	if (W) A = 'w';
	else A = 'b';	
	if(((getPieceN(B,Px,Py)).charAt(1))!=A)	return true;
	else return false;
}
//Rock Right
else if ((isImmobile())&&(Dy==0)&&(Dx==2)&&((getPiece(B,Px+1,Py)).isImmobile()))
{
	   
       boolean interpiecevide=true;
       for (int i =-1;i<1;i++)
	  {
	    if (!(Calc.isVoid(B,Px+i,Py))) interpiecevide= false ;
	  }
      if (interpiecevide) 
      {  
      //Vérifier que les cases sont safe pour le King!
    	  int pKing = getPos();
    	  boolean check = Calc.isThreaten(this);
    	  ArrayList<Piece> Bmind = new ArrayList<Piece>();
    	  for (int i=0;i<2;i++)
    	  {
    		Bmind=Calc.cloneAL(B);
    		moveKingInMind(pKing,pKing+1,Bmind);
    		if (Calc.isThreaten(this)) check=true;
    	  }
    	  if (!check) return true;
    	  else return false;
	  }
      else return false;
}
//Rock Left
else if ((isImmobile())&&(Dy==0)&&(Dx==-2)&&((getPiece(B,Px-2,Py)).isImmobile()))
{
       boolean interpiecevide=true;
       for (int i =-1;i<2;i++)
	  {
	    if (!(Calc.isVoid(B,Px-i,Py))) interpiecevide= false ;
	  }
       if (interpiecevide) 
       {  
       //Vérifier que les cases sont safe pour le King!
     	  int pKing = getPos();
     	  boolean check = Calc.isThreaten(this);
     	  ArrayList<Piece> Bmind = new ArrayList<Piece>();
     	  for (int i=0;i<2;i++)
     	  {
     		Bmind=Calc.cloneAL(B);
     		moveKingInMind(pKing,pKing-1,Bmind);
     		if (Calc.isThreaten(this)) check=true;
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
  addThreatening(getPiece(B,X2,Y2));
}
return false;
}

//Functions needed to verify KING is not in Check if Castling 
//Same ones taken from CLASS CHESSGAME
public void setPiece(Piece p,int P,ArrayList<Piece> Board){
Board.set(P,p);
(getPiece(P,Board)).setPos(P);
(getPiece(P,Board)).setCoord();
(getPiece(P,Board)).setLCoord();
}
public Piece getPiece(int P,ArrayList <Piece>B){return B.get(P);} 
//Moves King to adjacent case, using a copy of the BOARD B, named BMIND 
public void moveKingInMind(int Pinit,int Pfinal,ArrayList <Piece> Board){
Piece moving = getPiece(Pinit,Board);	
	
//Moving pieces 	
setPiece(new Nothing(Pinit,0),Pinit,Board);
setPiece(moving,Pfinal,Board);
}
}