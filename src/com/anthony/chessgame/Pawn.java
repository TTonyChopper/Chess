package com.anthony.chessgame;
import java.util.ArrayList;
//Class representing PAWN
public class Pawn extends Piece {
	
//CONSTRUCTOR : create a PIECE of COLOR C at POSITION P, and his NAME becomes "Qw"/"Qb"
public Pawn(int P,int C) {
	  super(P,C);
	  if (C==1) setName("Pw");
	  else setName("Pb");
	  }

//Movement allowed, stopping to the first obstacle[non VOID] : Up/Down[COLOR],
//or first element Up/Down on diagonal, Left or Right if there is an opponent to capture
//RETURNS true if the move is allowed
public boolean checkMove(int Px,int Py,boolean W,Player J,ArrayList<Piece> B){
int Dx = Px - getPosx();
int Dy = Py - getPosy();
boolean nevermoved = false;
if ((W&&(getPosy()==1))||((!W)&&(getPosy()==6))) nevermoved=true;
//System.out.println("nevermoved "+nevermoved);
//System.out.println("Dx "+Dx+" Dy "+Dy);
	if (((W)&&(Dy==1)&&Math.abs(Dx) ==1)||((!W)&&(Dy==-1)&&Math.abs(Dx) ==1))
	{
	  char A ;
	  if (W) A = 'b';
	  else A = 'w';
	  if(((B.get(Calc.getPos(Px,Py))).getName()).charAt(1)==A)
	  {
	   return true;
	  }
	}
	else if (Dx==0)
	{
	  if (W&&(Dy == 1)&&(Calc.isVoid(B,Px,Py))) return true;
	  else if (W&&(nevermoved)&&(Dy == 2)&&((Calc.isVoid(B,Px,Py)))&&
			  ((Calc.isVoid(B,Px,Py-1)))) return true;
	  else if ((!W)&&(Dy == -1)&&((Calc.isVoid(B,Px,Py)))) return true;
	  else if ((!W)&&(nevermoved)&&(Dy == -2)&&((Calc.isVoid(B,Px,Py)))&&
			  ((Calc.isVoid(B,Px,Py+1)))) return true;
	  else return false;	 
	}
	return false;
}

//SETTER for THREATENING : contains the first obstacle(friendly or not)
//If no piece is on the way, puts an OutOfBoard object instead(NAME "XX") 
//2 concrete PIECE threaten
public boolean setThreats(ArrayList <Piece> B)
{
	  clearThreatening();
	  addThreatening(DiagL(B));
	  addThreatening(DiagR(B));
	  return false;
}

//Looks for a PIECE on the Left diagonal
public Piece DiagL(ArrayList <Piece> B)
{
      if (getColor()==1) return getPiece(B,getPosx()-1,getPosy()+1);
      else return getPiece(B,getPosx()-1,getPosy()-1);
}
//Looks for a PIECE on the Right diagonal
public Piece DiagR(ArrayList <Piece> B)
{
      if (getColor()==1) return getPiece(B,getPosx()+1,getPosy()+1);
      else return getPiece(B,getPosx()+1,getPosy()-1);
}

}