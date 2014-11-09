package com.anthony.chessgame;
import java.util.ArrayList;
//Main Class for the GAMES OF CHESS
public class ChessGame
{
	
private static char[] l1 = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
private static char[] l2 = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
private static char[] l3 = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
private static char[] l4 = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
private static char[] l5 = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
private static char[] l6 = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
private static char[] l7 = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
private static char[] l8 = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
private static char[] l10 = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
private static char[] l11 = {' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' '};
private Player P1;
private Player P2;
private static ArrayList<String> wcaptures = new ArrayList<String>();
private static ArrayList<String> bcaptures = new ArrayList<String>();
private ArrayList <Piece> B = new ArrayList <Piece>();
private ArrayList <Piece> Bfuture = new ArrayList <Piece>();
private static int posK1= 4;
private static int posK1bu= 4;
private static int posK2= 60;
private static int posK2bu= 60;
private boolean checkmate;
private boolean checkmate2;
private final static int pieces[]={5,2,4,6,7,3,2,5,1,1,1,1,1,1,1,1,
				     			   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				     			   0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
				     			   1,1,1,1,1,1,1,1,5,2,3,6,7,4,2,5};

//CONSTRUCTOR : 
public ChessGame(){
 System.out.println("      Chess HumanVsHuman(By Anthony)\n\n");	
	
 createPlayers();
 
 checkmate = false;
 checkmate2 = false;
 for (int i=0;i<64;i++) 
 {
  if (i/16==0) putPiece(i,1); 
  else if (i/16==3)	putPiece(i,2);  
  else putPiece(i,0); 	  
 }	  
 B.add(new OutOfBoard());
 Calc.printBoard(B);
 Calc.setThreatsOnBoard(B);
 
 while (!checkmate2)
 {
	 //Calc.printThreateningOnBoard(B);
	 //Calc.printThreatenOnBoard(B);
	 checkmate = playTurn(P1,P2);
	 if (checkmate) break;
	 checkmate2 = playTurn(P2,P1);
 }
}

//GETTER for a PIECE parameter, for a given POSITION P
public Piece getPiece(int P,ArrayList <Piece>B){return B.get(P);}  
public int getPieceC(int P){return (B.get(P)).getColor();} 	 
public String getPieceN(int P){return (B.get(P)).getName();} 	
public int getPiecePos(int P){return (B.get(P)).getPos();}
public int getPiecePosx(int P){return (B.get(P)).getPosx();}
public int getPosy(int P){return (B.get(P)).getPosy();}
public char getPieceLposx(int P){return (B.get(P)).getLposx();}
public char getPieceLposy(int P){return (B.get(P)).getLposy();} 
//Saves position of King in case Castling attempt is not valid
public static int getPosK1(){return posK1;}
public static int getPosK2(){return posK2;}

//Constructs both PLAYER
public void createPlayers()
{
	 String pname = Calc.askName(true);
	 P1 = new Player(1,true,pname);
	 
	 pname = Calc.askName(false);
	 P2 = new Player(2,false,pname);
	 System.out.println("\n\n");
}
//Constructs the BOARD one PIECE at a time
public Piece putPiece(int P, int C){
	Piece put = null;
	switch (pieces[P])
	{
	  case 0:
	    put = new Nothing(P,C);
	    break;
	  case 1:
		put = new Pawn(P,C);
	    break;
	  case 2:
	    put = new Knight(P,C);
	    break;
	  case 3:
		put = new BishopW(P,C);
	    break;
	  case 4:
		put = new BishopB(P,C);
	    break;
	  case 5:
	    put = new Rook(P,C);
	    break;
	  case 6:
		put = new Queen(P,C);
	    break;
	  case 7:
	    put = new King(P,C);
	    break;  
	  default:
	    System.out.println("Il faut davantage travailler.");
	}	
	
   B.add(put);
   return put;
}
//Gets moves from PLAYER and try them until they are valid, then make the move
public Piece askNMoveCoord(Player J){
Piece captured = null;
boolean check= false;
do
{
if (check) 
{
	revertMoveP();
	Calc.printCheck();
	Calc.printBoard(B);
}
Bfuture = Calc.cloneAL(B);
Calc.askMove(J,Bfuture);
saveMoveP();
captured = moveTo(Calc.getPinit(),Calc.getPfinal(),J,Bfuture);
Calc.setThreatsOnBoard(Bfuture);
Calc.printThreateningOnBoard(Bfuture);
//Calc.printThreatenOnBoard(Bfuture);
check=Calc.isInCheck(Bfuture,J);
System.out.println("Check ????? : "+check);
}
while(check);
setCaptures(captured,J);
B=Calc.cloneAL(Bfuture);
return captured;
}
//Makes the move, supposing it is valid
public Piece moveTo(int Pinit,int Pfinal,Player P,ArrayList <Piece>Board) {
   Piece moving = getPiece(Pinit,Board);

//Verify Castling conditions
   if (((moving.getName()).charAt(0))=='K')
	  {
	    if (P.isWhite()) posK1 = Pfinal;
	    else if (!(P.isWhite())) posK2 = Pfinal;
	    if (moving.isImmobile()) 
	    {
	      if ((Pfinal-Pinit)==2) moveTo(Pfinal+1,Pfinal-1,P,Board);
	      else if ((Pfinal-Pinit)==-2) moveTo(Pfinal-2,Pfinal+1,P,Board);
	      
	      moving.isMobile();
	    }
	  }
   else if (((moving.getName()).charAt(0))=='R')
         {
	    if (moving.isImmobile()) moving.isMobile();
	  }

//Moving pieces
   Piece captured = getPiece(Pfinal,Board); 	
   setPiece(new Nothing(Pinit,0),Pinit,Board);
   setPiece(moving,Pfinal,Board);
   return captured;
}
//Adds a PIECE into the wcaptures/bcaptures ARRAYLIST, containing CAPTURES per COLOR
public Piece setCaptures(Piece captured,Player P){
if ((captured.getName())!="  "&& P.isWhite()) {
wcaptures.add(captured.getName());
return captured;
}else if ((captured.getName())!="  "&& !P.isWhite()) {
 bcaptures.add(captured.getName());
 return captured;
}else{
 return captured;  
}
}
//Saves Coordinates of KING, in case his move is not valid
public void saveMoveP(){
	posK1bu=posK1;
	posK2bu=posK2;
}
//Reverts previous Coordinates of KING, when his move was not valid
public void revertMoveP(){
	posK1=posK1bu;
	posK2=posK2bu;
	//variable immobile qu en est il ????
}
//Modify the BOARD composition after a move
public void setPiece(Piece p,int P,ArrayList<Piece> Board){
Board.set(P,p);
(getPiece(P,Board)).setPos(P);
(getPiece(P,Board)).setCoord();
(getPiece(P,Board)).setLCoord();
}

//Organize the turn of the PLAYER
public boolean playTurn(Player M,Player N) {
if(M.isWhite()) Calc.printBoardState(B,M,1);	
else if(!(M.isWhite())) Calc.printBoardState(B,M,2);
askNMoveCoord(M);
Calc.printBoard(B);
if (Calc.isInCheck(B,N))
{
Calc.oppInCheck(B,N);
}
return false;
}

}