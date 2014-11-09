package com.anthony.chessgame;
import java.util.ArrayList;

//Class representing QUEEN on WHITE case
public class Queen extends Piece {

//CONSTRUCTOR : create a PIECE of COLOR C at POSITION P, and his NAME becomes "Qw"/"Qb"
public Queen(int P,colorPiece C) {
	super(P);
	definePiece(typePiece.Q,C);
}

//Movement allowed, stopping to the first obstacle[non VOID] : TOP-DOWN-LEFT-RIGHT-UP LEFT-UP RIGHT-DOWN RIGHT-DOWN LEFT
//RETURNS true if the move is allowed
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
if (W&&(((Utils.getPieceN(B,Px,Py)).charAt(1))=='w')) moveok=false;
else if (!W&&(((Utils.getPieceN(B,Px,Py)).charAt(1))=='b')) moveok=false;

return moveok;
}

//Checks move Upward  
public boolean checkMoveColumnU(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
//Dx==0 et Dy>0
boolean obstacle = false;
for (int i=1;i<Dy;i++)
{
if (!(Calc.isVoid(B,Px,Py-i))) obstacle=true;
}
return !obstacle;
}
//Checks move Downward 
public boolean checkMoveColumnD(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
//Dx==0 et Dy<0
boolean obstacle = false;
for (int i=-1;i>Dy;i--)
{
if (!(Calc.isVoid(B,Px,Py-i))) obstacle=true;
}
return !obstacle;
}	
//Checks move on the Left
public boolean checkMoveLineL(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
//Dx<0 et Dy==0
boolean obstacle = false;
for (int i=-1;i>Dx;i--)
{
if (!(Calc.isVoid(B,Px-i,Py))) obstacle=true;
}
return !obstacle;
}	
//Checks move on the Right 
public boolean checkMoveLineR(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
//Dx>0 et Dy==0
boolean obstacle = false;
for (int i=1;i<Dx;i++)
{
if (!(Calc.isVoid(B,Px-i,Py))) obstacle=true;
}
return !obstacle;
}	
//Checks move on the Up Left diagonal 
public boolean checkMoveDiagUL(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
//Dx==-Dy  Dy>0
boolean obstacle = false;
for (int i=1;i<Dy;i++)
{
if (!(Calc.isVoid(B,Px+i,Py-i))) obstacle=true;
}
return !obstacle;
}
//Checks move on the Up Right diagonal 
public boolean checkMoveDiagUR(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
//Dx==Dy  Dy>0
boolean obstacle = false;
for (int i=1;i<Dy;i++)
{
if (!(Calc.isVoid(B,Px-i,Py-i))) obstacle=true;
}
return !obstacle;
}
//Checks move on the Down Right diagonal
public boolean checkMoveDiagDR(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
//Dx==-Dy  Dx>0
boolean obstacle = false;
for (int i=1;i<Dy;i++)
{
if (!(Calc.isVoid(B,Px-i,Py+i))) obstacle=true;
}
return !obstacle;
}
//Checks move on the Down Left diagonal
public boolean checkMoveDiagDL(int Px, int Py,int Dx, int Dy,boolean W,ArrayList<Piece> B){
//Dx==Dy  Dx<0
boolean obstacle = false;
for (int i=-1;i>Dx;i--)
{
if (!(Calc.isVoid(B,Px-i,Py-i))) obstacle=true;
}
return !obstacle;
}

//SETTER for THREATENING : contains the first obstacle(friendly or not)
//If no piece is on the way, puts an OutOfBoard object instead(NAME "XX") 
//8 concrete PIECE threaten
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
return false;
}

//Checks obstacle Upward
public Piece ColumnU(ArrayList <Piece> B){
int i;
for (i = getPosy()+1;i<8;i++)
{
  if (!(Calc.isVoid(B,getPosx(),i))) return Utils.getPiece(B,getPosx(),i);
}
return Utils.getPiece(B,getPosx(),i);	
}
//Checks obstacle Downward
public Piece ColumnD(ArrayList <Piece> B){
int i;
for (i = getPosy()-1;i>-1;i--)
{ 
  if (!(Calc.isVoid(B,getPosx(),i))) return Utils.getPiece(B,getPosx(),i);
}
return Utils.getPiece(B,getPosx(),i);	
}
//Checks obstacle on the Left
public Piece LineL(ArrayList <Piece> B){
int i;
for (i = getPosx()-1;i>-1;i--)
{ 
  if (!(Calc.isVoid(B,i,getPosy()))) return Utils.getPiece(B,i,getPosy());
}
return Utils.getPiece(B,i,getPosy());	
}
//Checks obstacle on the Right
public Piece LineR(ArrayList <Piece> B){
int i;
for (i = getPosx()+1;i<8;i++)
{ 
  if (!(Calc.isVoid(B,i,getPosy()))) return Utils.getPiece(B,i,getPosy());
}
return Utils.getPiece(B,i,getPosy());
}
//Checks obstacle on the Up Left diagonal
public Piece DiagUL(ArrayList <Piece> B){
int X = getPosx();	
int Y = getPosy();
for (int i=0;i<8;i++)
{
  X--;
  Y++;
  if (!(Calc.isVoid(B,X,Y))) return Utils.getPiece(B,X,Y);
}
return Utils.getPiece(B,8,8);
}
//Checks obstacle on the Up Right diagonal
public Piece DiagUR(ArrayList <Piece> B){
int X = getPosx();	
int Y = getPosy();
for (int i=0;i<8;i++)
{
  X++;
  Y++;
  if (!(Calc.isVoid(B,X,Y))) return Utils.getPiece(B,X,Y);
}
return Utils.getPiece(B,8,8);
}
//Checks obstacle on the Down Right diagonal
public Piece DiagDR(ArrayList <Piece> B){
int X = getPosx();	
int Y = getPosy();
for (int i=0;i<8;i++)
{
  X++;
  Y--;
  if (!(Calc.isVoid(B,X,Y))) return Utils.getPiece(B,X,Y);
}
return Utils.getPiece(B,8,8);
}
//Checks obstacle on the Down Left diagonal
public Piece DiagDL(ArrayList <Piece> B){
int X = getPosx();	
int Y = getPosy();
for (int i=0;i<8;i++)
{
  X--;
  Y--;
  if (!(Calc.isVoid(B,X,Y))) return Utils.getPiece(B,X,Y);
}
return Utils.getPiece(B,8,8);
}

}