package com.anthony.chessgame;
import java.util.ArrayList;
import java.util.Scanner;

import com.anthony.chessgame.Piece.colorPiece;
//This class tries to countain every important methods, those using System.out.print for example or Scanner
//Has to be modified to become a graphic game
//This class is not instanciated : everything is STATIC
public class Calc {	

//SCANNER for interactions with PLAYER
private static Scanner sc = new Scanner(System.in);
//Char version of coordinates
private static int destx;
private static int desty;
//Initial POSITION of a PIECE which is being played
private static int pinit;
//Potential Final POSITION of a PIECE which is being played
private static int pfinal;

//GETTER for parameters
public static int getPinit(){ return pinit;}
public static int getPfinal(){ return pfinal;}
//Transforms and Returns COORDINATES into POSITION 
public static int getPos(int Px, int Py){
if (Px>=0&&Py>=0&&Px<8&&Py<8) return 8*Py+Px;
else return 64;
}
//Transforms COORDINATES into CHAR
public static void setDest(String L){
	destx = (int)((int)(L.charAt(0))-(int)'a');
	desty = (int)((int)(L.charAt(1))-(int)'1');
}

//Asks and Returns a name for the PLAYER of COLOR W
public static String askName(boolean W){
String pname=null;
if (W)
{
System.out.println("Player 1 will play white.");
System.out.println("Enter your name...");
pname = sc.nextLine();
System.out.println(pname+" will play white.");
}
else
{
System.out.println("");	 
System.out.println("Player 2 will play black.");
System.out.println("Enter your name...");
pname = sc.nextLine();
System.out.println(pname+" will play black.");
}
return pname;
}
//Asks move proposed by PLAYER, modifying parameters destx,destx,pinit,pfinal
public static void askMove(Player J,ArrayList<Piece> Board){
String L = null;
boolean moveisok = false;
boolean pieceisok = false;
while (!moveisok) 
{
//Partie 1 : Origin	
do
{
System.out.println("Write a position fo a piece to move.");
while(((L=sc.nextLine()).length())!=2)
{
  System.out.println("Error.\nTry again.");
}	
int x = (int)L.charAt(0) - (int) 'a';
int y = (int)L.charAt(1) - (int) '1';
pinit = 8*y+x; 
pfinal =0;
pieceisok=comparePieceC(pinit,Board,J.isWhite());
if (!pieceisok) System.out.println("Invalid piece.");
}
while(!pieceisok);

//Partie 2 : Destination
	System.out.println("Where to move it ?");
	if ((L=sc.nextLine()).length()==2)
	{
		setDest(L);	
		pfinal = Calc.getPos(destx, desty);
		moveisok = getPiece(pinit,Board).checkMove(destx,desty,J.isWhite(),J,Board);
		if (!moveisok) System.out.println("Coup non valide.");
	}
	else System.out.println("Erreur.");
}
}
//Clears THREATEN for every PIECE on a given BOARD
public static void resetThreaten(ArrayList<Piece> B){
	for (int i=0;i<64;i++)
	{
		(B.get(i)).clearThreaten();
	}
}
//SETTER for THREATENING and THREATEN for every PIECE of a given BOARD
public static void setThreatsOnBoard(ArrayList<Piece> B){
resetThreaten(B);
for (int t=0;t<64;t++)
 {
	String name =(B.get(t)).getName();
	if (!(name.equals("  "))) (B.get(t)).setThreats(B);
 }
}
//Prints THREATENING[every PIECE attacked by a given PIECE, one by one] of every PIECE of a given BOARD
public static void printThreateningOnBoard(ArrayList<Piece> B){
for (int t=0;t<64;t++)
 {
	String name =(B.get(t)).getName();
	if (!(name.equals("  ")))
	{
		System.out.println(name);
		System.out.println("x : "+(B.get(t)).getPosx()+" y : "+(B.get(t)).getPosy());
		(B.get(t)).printThreateningNames();
	}
 }
}	
//Prints THREATEN[every PIECE attacking a given PIECE, one by one] of every PIECE of a given BOARD
public static void printThreatenOnBoard(ArrayList<Piece> B){
for (int t=0;t<64;t++)
 {
	String name =(B.get(t)).getName();
	if (!(name.equals("  ")))
	{
		System.out.println(name);
		System.out.println("x : "+(B.get(t)).getPosx()+" y : "+(B.get(t)).getPosy());
		(B.get(t)).printThreatenNames();
	}
 }
}	
//EVOLVED GETTER for accessing one given PIECE, and/or his parameters, on a given BOARD
public static Piece getPiece(int P,ArrayList<Piece> B){return B.get(P);} 
public static String getPieceN(int P,ArrayList<Piece> B){return (B.get(P)).getName();} 
public static colorPiece getPieceC(int P,ArrayList<Piece> B){return (B.get(P)).getColor();} 
//Returns true if the PIECE at (Px,Py) is VOID[NOTHING]
public static boolean isVoid(ArrayList<Piece> B,int Px,int Py){return (((B.get(Calc.getPos(Px,Py))).getName()).equals("  "));}
//Returns true if the PIECE at P is of COLOR W(0 for BLACK, 1 for WHITE)
public static Boolean comparePieceC(int P,ArrayList<Piece> B,boolean W){
	Boolean myW = B.get(P).isWhite();
	if (myW == null) return false;
	else {
		if (myW) return W;
		else return !W;
	}
} 

//Clones and Returns the copy of a given BOARD, to keep a BACKUP 
public static ArrayList <Piece> cloneAL(ArrayList<Piece> B1){
ArrayList <Piece> B2 = new ArrayList <Piece>();
for (int i=0;i<65;i++){ B2.add(B1.get(i));}
return B2;
}
//Given a PIECE, returns true if Attacked by an Foe PIECE
public static boolean isThreaten(Piece P){
ArrayList<Piece>T = P.getThreaten();
boolean check = false;
Boolean myW = P.isWhite();
if (myW == null) return false;
for (int i=0;i<T.size();i++)
{
	if (comparePieceC(i,T,!myW)) check = true;
}
return check;
}
//Returns true if the KING of the PLAYER J is attacked[PLAYER in CHESS]
public static boolean isInCheck(ArrayList<Piece> B,Player J){
Piece king=null;
if (J.isWhite()) king=getPiece(ChessGame.getPosK1(),B);
else if (!(J.isWhite())) king=getPiece(ChessGame.getPosK2(),B);
return isThreaten(king);
}
//Prints and inform the PLAYER he has put his Foe in CHESS
public static boolean oppInCheck(ArrayList<Piece> B,Player J2){
boolean checkmate = false;
System.out.println("You have put your opponent in check !");
return checkmate;
}
//Prints and inform the PLAYER he is in CHESS
public static void printCheck(){
System.out.println("You are in Check, look closely !");
}
//Prints whose turn it is
public static void printBoardState(ArrayList<Piece> B,Player J,int N){
	System.out.println("Player "+N+"("+J.getPname()+") : Your turn to play!");
}
//Prints the actual BOARD state
public static void printBoard(ArrayList<Piece> B){
System.out.println("");	
System.out.println("      a    b    c    d    e    f    g    h   ");	
System.out.println("   +----+----+----+----+----+----+----+----+");
System.out.print(" 8 | "+getPieceN(56,B)+" | ");
for (int i = 1;i<64;i++)
{
	int Y = i % 8;
	int newpos = 64 - ((i / 8)+1)*8 + Y;
	char digit = (char) (((int)'8') - i/8);
	char digit2 = (char) (((int)'8') - i/8+1);
	
	if (Y==0) 
	{	
	 System.out.print(digit2+"\n");
	 System.out.println("   +----+----+----+----+----+----+----+----+");
	 System.out.print(" "+digit+" | "+getPieceN(newpos,B)+" | ");
	}
	else System.out.print(getPieceN(newpos,B)+" | ");
	//System.out.println("i "+i);
	//System.out.println("pos "+newpos);
}
System.out.println("1");
System.out.println("   +----+----+----+----+----+----+----+----+");
System.out.println("     a    b    c    d    e    f    g    h  ");
}
//Shows PLAYER STATS
public static void printStats(){ 
}	
	
}
