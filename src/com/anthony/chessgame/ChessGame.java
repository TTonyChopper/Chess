package com.anthony.chessgame;
import java.util.ArrayList;

import com.anthony.chessgame.Piece.colorPiece;
//Main Class for the GAMES OF CHESS
public class ChessGame
{
	private Player P1;
	private Player P2;
	private ArrayList <Piece> B;
	private ArrayList <Piece> Bfuture;
	private boolean checkmate;
	private boolean checkmate2;
	private ArrayList<String> wcaptures;
	private ArrayList<String> bcaptures;
	private int posK1;
	private int posK1bu;
	private int posK2;
	private int posK2bu;
	
	private final static int pieces[]={
		5,2,4,6,7,3,2,5,1,1,1,1,1,1,1,1,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		1,1,1,1,1,1,1,1,5,2,3,6,7,4,2,5};

	//CONSTRUCTOR
	public ChessGame(){
		B = new ArrayList <Piece>();
		Bfuture = new ArrayList <Piece>();
		checkmate = false;
		checkmate2 = false;
		wcaptures = new ArrayList<String>();
		bcaptures = new ArrayList<String>();
		posK1= 4;
		posK1bu= 4;
		posK2= 60;
		posK2bu= 60;
	}
	
	//PLAY : 
	public void start(){
		Print.printTitle();

		createPlayers();
		
		for (int i=0;i<64;i++) 
		{
			if (i/16==0) putPiece(i,colorPiece.WHITE); 
			else if (i/16==3)	putPiece(i,colorPiece.BLACK);  
			else putPiece(i,colorPiece.NONE); 	  
		}	  
		B.add(new OutOfBoard());
		Print.printBoard(B);
		Utils.setThreatsOnBoard(B);

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
	public colorPiece getPieceC(int P){return (B.get(P)).getColor();} 	 
	public String getPieceN(int P){return (B.get(P)).getName();} 	
	public int getPiecePos(int P){return (B.get(P)).getPos();}
	public int getPiecePosx(int P){return (B.get(P)).getPosx();}
	public int getPosy(int P){return (B.get(P)).getPosy();}
	public char getPieceLposx(int P){return (B.get(P)).getLposx();}
	public char getPieceLposy(int P){return (B.get(P)).getLposy();} 
	//Saves position of King in case Castling attempt is not valid
	public int getPosK1(){return posK1;}
	public int getPosK2(){return posK2;}

	//Constructs both PLAYER
	public void createPlayers()
	{
		String pname = Print.askName(true);
		P1 = new Player(1,true,pname);

		pname = Print.askName(false);
		P2 = new Player(2,false,pname);
		Print.printLine();
		Print.printLine();
	}
	//Constructs the BOARD one PIECE at a time
	public Piece putPiece(int P, colorPiece C){
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
			break;
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
				Print.printCheck();
				Print.printBoard(B);
			}
			Bfuture = Utils.cloneAL(B);
			Print.askMove(J,Bfuture);
			saveMoveP();
			captured = moveTo(Print.getPinit(),Print.getPfinal(),J,Bfuture);
			Utils.setThreatsOnBoard(Bfuture);
			Print.printThreateningOnBoard(Bfuture);
			//Calc.printThreatenOnBoard(Bfuture);
			check=Utils.isInCheck(Bfuture,J,posK1,posK2);
			Print.printIsCheck(check);
		}
		while(check);
		setCaptures(captured,J);
		B=Utils.cloneAL(Bfuture);
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
			if (moving.hasSpecialMove()) 
			{
				if ((Pfinal-Pinit)==2) moveTo(Pfinal+1,Pfinal-1,P,Board);
				else if ((Pfinal-Pinit)==-2) moveTo(Pfinal-2,Pfinal+1,P,Board);

				moving.loseSpecialMove();
			}
		}
		else if (((moving.getName()).charAt(0))=='R')
		{
			if (moving.hasSpecialMove()) moving.loseSpecialMove();
		}

		//Moving pieces
		Piece captured = getPiece(Pfinal,Board); 	
		setPiece(new Nothing(Pinit,colorPiece.NONE),Pinit,Board);
		setPiece(moving,Pfinal,Board);
		return captured;
	}
	//Adds a PIECE into the wcaptures/bcaptures ARRAYLIST, containing CAPTURES per COLOR
	public Piece setCaptures(Piece captured,Player P){
		if (!(captured.getName()).equals("  ")&& P.isWhite()) {
			wcaptures.add(captured.getName());
			return captured;
		}else if (!(captured.getName()).equals("  ")&& !P.isWhite()) {
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
		if(M.isWhite()) Print.printBoardState(B,M,1);	
		else if(!(M.isWhite())) Print.printBoardState(B,M,2);
		askNMoveCoord(M);
		Print.printBoard(B);
		Print.printCaptures(wcaptures,bcaptures);
		if (Utils.isInCheck(B,N,posK1,posK2))
		{
			Print.oppInCheck(B,N);
		}
		return false;
	}

}