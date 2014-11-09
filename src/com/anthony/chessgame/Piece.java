package com.anthony.chessgame;
import java.util.ArrayList;
//MOTHER Class for every PIECE, CHECKMOVE & SETTHREATS are OVERRIDEN
public class Piece {

//Contains each PIECE attacking this PIECE
private ArrayList<Piece> threaten = new ArrayList<Piece>();
//Contains each PIECE(friendly or not) attacked by this PIECE, can be OutOfBoard though 
private ArrayList<Piece> threatening = new ArrayList<Piece>();
//String of two char caracterizing the PIECE
private String name;
//COLOR of this PIECE : 0 for NEUTRAL, 1 for WHITE, 2 for BLACK
private int color;
//Position of the PIECE on the 64 cases BOARD, from 0(a1) to 63(h8) 
private int pos;
//Coordinates of the PIECE
private int posx;
private int posy;
//Char corresponding to the coordinates ('a' for x=0,'1' for y=0)
private char lposx;
private char lposy;

//CONSTRUCTOR : create a PIECE of COLOR C at POSITION P, and his NAME becomes "  " 
public Piece(int P, int C) {
threaten = new ArrayList<Piece>(); 
threatening = new ArrayList<Piece>();
setName("  ");
pos = P;
color = C;
setCoord();
setLCoord();
}

//GETTER for some parameters, some can work with the whole BOARD
public String getPieceN(ArrayList<Piece> B,int Px,int Py){return (B.get(Calc.getPos(Px,Py))).getName();}
public String getPieceN(ArrayList<Piece> B,int P){return (B.get(P)).getName();}
public boolean isImmobile(){return false;}
public boolean isMobile(){return false;}
public Piece getPiece(ArrayList<Piece> B,int Px,int Py){return B.get(Calc.getPos(Px,Py));}
public String getName() {return name;}
public int getColor() {return color;}
public int getPos() {return pos;}
public int getPosx() {return posx;}
public int getPosy() {return posy;}
public char getLposx() {return lposx;}
public char getLposy() {return lposy;}
public ArrayList<Piece> getThreaten(){return threaten;}
public boolean isThreatenKing(int P,Player J){
if (J.isWhite()) return (getPieceN(threaten,P).equals("Kw"));
else if (!(J.isWhite())) return (getPieceN(threaten,P).equals("Kb"));
else return false;
}

//SETTER for some parameters
public String setName(String N) {return name= new String(N);}
public int setPos(int P) {return pos=P;}
public void setCoord(){
posx = pos % 8 ;
posy = pos / 8 ;
}
public void setLCoord(){
  lposx = (char)(posx - 1 + (int)('a'));
  lposy = (char)(posy - 1 + (int)('1'));
}
public void setL2PosCoord(){
posx = ((int)lposx + 1 - (int)('a'));
posy = ((int)lposy + 1 - (int)('1'));
}
//SETTER for every single PIECE on a given BOARD 
public boolean setThreats(ArrayList <Piece> B){return false;}

//Calls checkMove of every PIECE of a given BOARD
public boolean checkMove(int Px, int Py,boolean W,Player J,ArrayList<Piece> B){return false;} 
//Prints each THREATEN PIECE for this one 
public void printThreateningNames(){
 for(int i=0;i<threatening.size();i++)
 {
System.out.println("n "+(i+1)+" : "+(threatening.get(i)).getName());  
 }
}
//Prints each PIECE Attacking this one
public void printThreatenNames(){
	 for(int i=0;i<threaten.size();i++)
	 {
	System.out.println("n "+(i+1)+" : "+(threaten.get(i)).getName());  
	 }
	}
//Resets THREATENING for this PIECE : is always called before updating it by erasing previous data
public void clearThreatening(){threatening=new ArrayList<Piece>();}
//Resets THREATEN for this PIECE : is always called before updating it by erasing previous data
public void clearThreaten(){threaten= new ArrayList<Piece>();}
//Means this PIECE is Attacking the PIECE P
//P is added to THREATENING
//this PIECE is added to THREATEN of P
public void addThreatening(Piece P){threatening.add(P);(P.getThreaten()).add(this);}

}