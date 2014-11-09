package com.anthony.chessgame;
import java.util.ArrayList;
//Class representing KNIGHT
public class Knight extends Piece {

//1 for column a ; 2 for column h
private int number;

//CONSTRUCTOR : create a PIECE of COLOR C at POSITION P, and his NAME becomes "Qw"/"Qb"
public Knight(int P,int C) {
super(P,C);
if (C==1) setName("Nw");
else setName("Nb");
}

//Movement allowed, 8 destination cases : L-like move 2/1 in one direction, 1/2 orthogonally
//RETURNS true if the move is allowed
public boolean checkMove(int Px, int Py,boolean W,Player J,ArrayList<Piece> B){
int Dx = Px - getPosx();
int Dy = Py - getPosy();
if ((Math.abs(Dx) ==1 && Math.abs(Dy) ==2)||(Math.abs(Dx) ==2 && Math.abs(Dy) ==1))
{
  char A ;
  if (W) A = 'w';
  else A = 'b';
  if(((getPieceN(B,Px,Py)).charAt(1))!=A)
  {
   return true;
  }
  else return false;
}
else return false;
}

//SETTER for THREATENING : contains the destination PIECE(friendly or not)
//If destination if out of the board, puts an OutOfBoard object instead(NAME "XX") 
//8 concrete PIECE threaten
public boolean setThreats(ArrayList <Piece> B)
{
clearThreatening();
int X = getPosx();	
int Y = getPosy();
int X2 = X;
int Y2 = Y;
int Dx[] = {-2,-1,1,2,2,1,-1,-2};
int Dy[] = {1,2,2,1,-1,-2,-2,-1};
for (int i=0;i<8;i++)
{
  X2 = X+Dx[i];
  Y2 = Y+Dy[i];
  addThreatening(getPiece(B,X2,Y2));
}
return false;
}

}