package com.anthony.chessgame.game;
import java.util.ArrayList;

import com.anthony.chessgame.piece.Bishop;
import com.anthony.chessgame.piece.King;
import com.anthony.chessgame.piece.Knight;
import com.anthony.chessgame.piece.Nothing;
import com.anthony.chessgame.piece.OutOfBoard;
import com.anthony.chessgame.piece.Pawn;
import com.anthony.chessgame.piece.Piece;
import com.anthony.chessgame.piece.Piece.typePiece;
import com.anthony.chessgame.piece.Queen;
import com.anthony.chessgame.piece.Rook;
import com.anthony.chessgame.piece.Piece.colorPiece;
import com.anthony.chessgame.util.IPrint;
import com.anthony.chessgame.util.Utils;
//Main Class for the GAMES OF CHESS
public class ChessGame implements SpecialMoveObserver
{
	//Players
	private Player P1;
	private Player P2;
	//Boards : real and trial board
	private ArrayList <Piece> B;
	private ArrayList <Piece> Bfuture;
	private boolean checkmate;
	private boolean checkmate2;
	//Captured pieces
	private ArrayList<String> wcaptures;
	private ArrayList<String> bcaptures;
	//King positions
	private int posK1;
	private int posK1bu;
	private int posK2;
	private int posK2bu;
	//Not null if a pawn of the corresponding player did a 2 case move last turn 
	private Piece pawnW;
	private Piece pawnB;
	//POSITIONS of a PIECE which is being played
	private int[] mW;
	
	private IPrint printer;

	private final static int pieces[]={
		4,2,3,5,6,3,2,4,1,1,1,1,1,1,1,1,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		1,1,1,1,1,1,1,1,4,2,3,5,6,3,2,4};

	/**
	 * CONSTRUCTOR
	 * @param p
	 */
	public ChessGame(IPrint p){
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
		mW= new int[]{8,9};
		printer = p;
	}

	/**
	 * start and play the game
	 */
	public void start(){
		printer.printTitle();

		createPlayers();

		createBoard();

		printer.printBoard(B);
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
	//Getter to check "En passant"
	public Piece getFoePawn(boolean W) {
		Piece pawn = (W) ? pawnB : pawnW;
		return pawn;
	}
	
	/**
	 * Constructs both PLAYER
	 */
	public void createPlayers()
	{
		String pname = printer.askName(true);
		P1 = new Player(1,true,pname);

		pname = printer.askName(false);
		P2 = new Player(2,false,pname);
		printer.printLine();
		printer.printLine();
	}
	/**
	 * Constructs board
	 */
	public void createBoard()
	{
		for (int i=0;i<64;i++) 
		{
			if (i/16==0) putPiece(i,colorPiece.WHITE); 
			else if (i/16==3)	putPiece(i,colorPiece.BLACK);  
			else putPiece(i,colorPiece.NONE); 	  
		}	  
		B.add(new OutOfBoard());
	}
	/**
	 * Constructs the BOARD one PIECE at a time
	 * @param P
	 * @param C
	 * @return
	 */
	public Piece putPiece(int P, colorPiece C){
		Piece put = null;
		switch (pieces[P])
		{
		case 0:
			put = new Nothing(P,C);
			break;
		case 1:
			put = new Pawn(P,C);
			((Pawn)put).setSpecialMoveListener(this);
			break;
		case 2:
			put = new Knight(P,C);
			break;
		case 3:
			put = new Bishop(P,C);
			break;
		case 4:
			put = new Rook(P,C);
			break;
		case 5:
			put = new Queen(P,C);
			break;
		case 6:
			put = new King(P,C);
			break;  
		default:
			break;
		}	

		B.add(put);
		return put;
	}
	/**
	 * Try current move
	 * @param P
	 * @return
	 */
	public Piece tryMove(Player P){
		saveMoveKings();
		Piece captured = moveTo(P,Bfuture,mW[0],mW[1]);
		Utils.setThreatsOnBoard(Bfuture);
		//printer.printThreateningOnBoard(Bfuture);
		//printer.printThreatenOnBoard(Bfuture);
		return captured;
	}
	/**
	 * Gets moves from PLAYER and try them until they are valid, then makes the move
	 * @param P
	 * @return
	 */
	public Piece askNMoveCoord(Player P){
		Piece captured = null;
		//		boolean losingMobility = false;
		boolean check= false;

		do
		{
			if (check) 
			{
				revertMoveKings();
				printer.printCheck();
				printer.printBoard(B);
			}
			//Ask coordinates
			Bfuture = null;
			Bfuture = Utils.cloneAL(B);
			printer.askMove(P,Bfuture,mW);
			//Test the move 
			captured = tryMove(P);
			//Verify if Player playing turn if is check
			check=Utils.isInCheck(Bfuture,P,posK1,posK2);
			printer.printIsCheck(check);
		}while(check);		
		//Move is OK, Finalize it
		setCaptures(captured,P);
		B=null;
		B=Bfuture;
		//Lose special moves opportunity
		if(Utils.isKing(B,mW[1])||Utils.isRook(B,mW[1])) B.get(mW[1]).loseSpecialMove();
		//reset other player passable pawn
		resetMovePawn(!P.isWhite());
		return captured;
	}

	/**
	 * Makes the move, supposing it is valid
	 * @param P
	 * @param Board
	 * @param Pinit
	 * @param Pfinal
	 * @return
	 */
	public Piece moveTo(Player P,ArrayList <Piece>Board,int Pinit,int Pfinal) {
		Piece moving = getPiece(Pinit,Board);

		//Verify Castling conditions to move the Rook too
		if (moving.getType()==typePiece.K)
		{
			if (P.isWhite()) posK1 = Pfinal;
			else if (!(P.isWhite())) posK2 = Pfinal;
			if (moving.hasSpecialMove()) 
			{
				if (((Pfinal-Pinit)==2)) moveTo(P,Board,Pfinal+1,Pfinal-1);
				else if ( ((Pfinal-Pinit)==-2)) moveTo(P,Board,Pfinal-2,Pfinal+1);
			}
		}
		
		//Moving pieces
		Piece captured = getPiece(Pfinal,Board);
		setPiece(new Nothing(Pinit,colorPiece.NONE),Pinit,Board);
		setPiece(moving,Pfinal,Board);
		
		//Verify Passing conditions
		if (moving.getType()==typePiece.P)
		{
			int Px = moving.getPosx();
			int Py = moving.getPosy();
			Py = (P.isWhite()) ? Py-1 : Py+1;
			Piece passedTest = (P.isWhite()) ? pawnB : pawnW;
			if(Utils.getPiece(Board, Px,Py)==passedTest) {
				captured = passedTest;
				int pos = captured.getPos();
				setPiece(new Nothing(pos,colorPiece.NONE),pos,Board);
			}
		}

		
		return captured;
	}
	/**
	 * Adds a PIECE into the wcaptures/bcaptures ARRAYLIST, containing CAPTURES per COLOR
	 * @param captured
	 * @param P
	 * @return
	 */
	public Piece setCaptures(Piece captured,Player P){
		if (((captured.getType()) != typePiece.N) && P.isWhite()) {
			wcaptures.add(captured.getName());
			return captured;
		}else if (((captured.getType()) != typePiece.N)&& !P.isWhite()) {
			bcaptures.add(captured.getName());
			return captured;
		}else{
			return captured;  
		}
	}
	/**
	 * Saves Coordinates of KING, in case his move is not valid
	 */
	public void saveMoveKings(){
		posK1bu=posK1;
		posK2bu=posK2;
	}
	/**
	 * Reverts previous Coordinates of KING, when his move was not valid
	 */
	public void revertMoveKings(){
		posK1=posK1bu;
		posK2=posK2bu;
	}
	/**
	 * Resets Passing conditions check for a Pawn, when his move was not valid
	 */
	public void resetMovePawn(boolean W){
		if (W) {
			if (pawnW!= null) pawnW = null;
		} else {
			if (pawnB!= null) pawnB = null;
		}
	}

	/**
	 * Modify the BOARD composition after a move
	 * @param p
	 * @param P
	 * @param Board
	 */
	public void setPiece(Piece p,int P,ArrayList<Piece> Board){
		Board.set(P,p);
		(getPiece(P,Board)).setPos(P);
		(getPiece(P,Board)).setCoord();
		(getPiece(P,Board)).setLCoord();
	}

	/**
	 * Organize the turn of the PLAYER
	 * @param M
	 * @param N
	 * @return
	 */
	public boolean playTurn(Player M,Player N) {
		if(M.isWhite()) printer.printBoardState(B,M,1);	
		else if(!(M.isWhite())) printer.printBoardState(B,M,2);
		
		askNMoveCoord(M);
		
		printer.printBoard(B);
		printer.printCaptures(wcaptures,bcaptures);
		
		if (Utils.isInCheck(B,N,posK1,posK2))
		{
			//TODO Gotta check if this is a Mate
			printer.oppInCheck(B,N);
		}
		return false;
	}
	@Override
	/**
	 * 
	 */
	public void bigLeapSpawnSpotted(Piece P,boolean W) {
		if (W) {
			pawnW = P;
		} else {
			pawnB = P;
		}
	}
}