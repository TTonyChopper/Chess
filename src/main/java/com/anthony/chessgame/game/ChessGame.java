package com.anthony.chessgame.game;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.anthony.chessgame.piece.Bishop;
import com.anthony.chessgame.piece.King;
import com.anthony.chessgame.piece.Knight;
import com.anthony.chessgame.piece.Nothing;
import com.anthony.chessgame.piece.OutOfBoard;
import com.anthony.chessgame.piece.Pawn;
import com.anthony.chessgame.piece.Piece;
import com.anthony.chessgame.piece.Piece.colorPiece;
import com.anthony.chessgame.piece.Piece.TypePiece;
import com.anthony.chessgame.piece.Queen;
import com.anthony.chessgame.piece.Rook;
import com.anthony.chessgame.util.IPrint;
import com.anthony.chessgame.util.Utils;
//Main Class for the GAMES OF CHESS
public class ChessGame implements SpecialMoveObserver
{
	final static int BOARD_WIDTH = 8;
	final static int BOARD_HEIGHT = 8;
	final static int BOARD_SIZE = BOARD_WIDTH * BOARD_HEIGHT;
	final static int PIECES_PER_PLAYER = 16;

	final static int STANDARD_FIRST_KING_INDEX = 4;
	final static int STANDARD_SECOND_KING_INDEX = 60;

	final static int[] PIECES ={
	        4,2,3,5,6,3,2,4,
            1,1,1,1,1,1,1,1,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,0,
            1,1,1,1,1,1,1,1,
            4,2,3,5,6,3,2,4};
	//Players
	private Player P1;
	private Player P2;
	//Boards : real and trial board
	private Piece[] B;
	private Piece[] Bfuture;
	//True when Mate, null on Pat.
	private Boolean checkmate;
	private Boolean checkmate2;
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
	private MovingPiece movingPiece;
	private IPrint printer;
	//Object transmitted between threads
	private Object holder;

	/**
	 * CONSTRUCTOR
	 */
	public ChessGame(){
		B = new Piece[BOARD_WIDTH*BOARD_HEIGHT+1];
		Bfuture = new Piece[BOARD_WIDTH*BOARD_HEIGHT+1];
		checkmate = false;
		checkmate2 = false;
		wcaptures = new ArrayList<>();
		bcaptures = new ArrayList<>();
		posK1 = STANDARD_FIRST_KING_INDEX;
		posK1bu = STANDARD_FIRST_KING_INDEX;
		posK2 = STANDARD_SECOND_KING_INDEX;
		posK2bu = STANDARD_SECOND_KING_INDEX;
		movingPiece = new MovingPiece();
	}
	/**
	 * CONSTRUCTOR
	 * @param p
	 */
	public ChessGame(IPrint p){
		B = new Piece[BOARD_WIDTH*BOARD_HEIGHT+1];
		Bfuture = new Piece[BOARD_WIDTH*BOARD_HEIGHT+1];
		checkmate = false;
		checkmate2 = false;
		wcaptures = new ArrayList<>();
		bcaptures = new ArrayList<>();
		posK1= STANDARD_FIRST_KING_INDEX;
		posK1bu= STANDARD_FIRST_KING_INDEX;
		posK2= STANDARD_SECOND_KING_INDEX;
		posK2bu= STANDARD_SECOND_KING_INDEX;
		movingPiece = new MovingPiece();
		printer = p;
	}
	/**
	 * start and play the game
	 */
	public void start(){
		if (printer == null) return;
		printer.printTitle();

		createPlayers();

		createBoard();

		printer.printBoard(B);
		Utils.setThreatsOnBoard(B);

		while ((checkmate2!=null) && (!checkmate2))
		{
			//printer.printThreateningOnBoard(B);
			//printer.printThreatenOnBoard(B);
			checkmate = playTurn(P1,P2);
			if ((checkmate==null) || (checkmate)) {
				break;
			}
			checkmate2 = playTurn(P2,P1);
		}
		
		//TODO end game details
		if ((checkmate==null) && (checkmate2==null)) {
//			printer.printPat(P1,P2);
		} else if (checkmate) {
//			printer.printMate(P1,P2);
		} else if (checkmate2) {
//			printer.printMate(P2,P1);
		}
		printer.printGameOver();
	}

	//GETTER for a PIECE parameter, for a given POSITION P
	public Piece getPiece(int P,Piece[] B){return B[P];}  
	public colorPiece getPieceC(int P){return B[P].getColor();} 	 
	public String getPieceN(int P){return B[P].getName();} 	
	public int getPiecePos(int P){return B[P].getPos();}
	public int getPiecePosx(int P){return B[P].getPosx();}
	public int getPosy(int P){return B[P].getPosy();}
	public char getPieceLposx(int P){return B[P].getLposx();}
	public char getPieceLposy(int P){return B[P].getLposy();} 
	//Saves position of King in case Castling attempt is not valid
	public int getPosK1(){return posK1;}
	public int getPosK2(){return posK2;}
	//Getter to check "En passant"
	public Piece getFoePawn(boolean W) {
		Piece pawn = (W) ? pawnB : pawnW;
		return pawn;
	}
	//Getter for enemy player
	public Player getFoe(Player P) {
		return (P1==P) ? P2:P1;
	}
	public Player getPlayer1() {
		return P1;
	}
	public Player getPlayer2() {
		return P2;
	}
	public Object getHolder() {
		return holder;
	}
	public void setHolder(Object o) {
		holder = o;
	}
	/**
	 * Constructs both PLAYER
	 */
	private void createPlayers()
	{
		String pname = printer.askName(true,this);
		P1 = new Player(1,true,pname);

		pname = printer.askName(false,this);
		P2 = new Player(2,false,pname);
		printer.printLine();
		printer.printLine();
	}
	/**
	 * Constructs board
	 */
	private void createBoard()
	{
		for (int i=0;i<BOARD_SIZE;i++)
		{
			if (i/PIECES_PER_PLAYER==0) putPiece(i,colorPiece.WHITE);
			else if (i/PIECES_PER_PLAYER==3) putPiece(i,colorPiece.BLACK);
			else putPiece(i,colorPiece.NONE); 	  
		}	  
		B[BOARD_SIZE] = new OutOfBoard();
	}
	/**
	 * Constructs the BOARD one PIECE at a time
	 * @param P
	 * @param C
	 * @return
	 */
	private Piece putPiece(int P, colorPiece C){
		Piece put = null;
		switch (PIECES[P])
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

		if (C.getW() != null) {
			//Testing colors of Player and new piece put
			if (P1.isWhite()==C.getW()) {
				P1.receivePiece(put);
			}
			if (P2.isWhite()==C.getW()) {
				P2.receivePiece(put);
			}
		} 
		
		setPiece(put,P,B);
		return put;
	}
	/**
	 * 
	 * @param P
	 */
	private void tryMoveBack(Player P,Piece captured){
		moveBackTo(P,Bfuture,movingPiece.dest,movingPiece.orig,captured);
	}
	/**
	 * 
	 * @param P
	 * @param pOrig
	 * @param pMoved
     * @param captured
	 */
	private void tryMoveBack(Player P,int pOrig,int pMoved,Piece captured){
		revertMoveKings();
		moveBackTo(P,Bfuture,pMoved,pOrig,captured);
		Utils.setThreatsOnBoard(Bfuture);
		//printer.printThreateningOnBoard(Bfuture);
		//printer.printThreatenOnBoard(Bfuture);
		//printer.printPlayerPiecesOnBoard(P1,P2);
	}
	/**
	 * Try current move
	 * @param P
	 * @return
	 */
	private Piece tryMove(Player P){
		return tryMove(P,movingPiece.orig,movingPiece.dest);
	}
	/**
	 * Try current move
	 * @param P
     * @param pOrig
     * @param pDest
	 * @return
	 */
	private Piece tryMove(Player P, int pOrig, int pDest){
		saveMoveKings();
		Piece captured = moveTo(P,Bfuture,pOrig,pDest);
		Utils.setThreatsOnBoard(Bfuture);
		//printer.printThreateningOnBoard(Bfuture);
		//printer.printThreatenOnBoard(Bfuture);
		//printer.printPlayerPiecesOnBoard(P1,P2);
		return captured;
	}

    /**
     *
     * @param moving
     * @param captured
     * @param P
     */
	private void finalizeMove(Piece moving,Piece captured,Player P) {
		setCaptures(captured,getFoe(P));
		if (captured.isWhite() != null) getFoe(P).losePiece(captured);
		B=null;
		B=Bfuture;
		//Lose special moves opportunity
		if(Utils.isKing(B,movingPiece.dest)||Utils.isRook(B,movingPiece.dest)) B[movingPiece.dest].loseSpecialMove();
		//reset other player passable pawn
		resetMovePawn(!P.isWhite());
		
		//Pawn promotion ?
		if(moving.getType()==TypePiece.P) {
			if ( ((P.isWhite()) && (moving.getPosy()==7)) || ((!P.isWhite()) && (moving.getPosy()==0)) ){
				TypePiece type = printer.askPromotion(this);
				int pos = moving.getPos();
				Piece newPiece;
				colorPiece C = (P.isWhite()) ? colorPiece.WHITE : colorPiece.BLACK;
				switch(type){
					case P :
					case K :
					case N : 	
					case O :
					default : return;
					case Bw : 
					case Bb : newPiece = new Bishop(pos,C);
						break;
					case Kn : newPiece = new Knight(pos,C);;
						break;
					case R :  newPiece = new Rook(pos,C);;
						break;
					case Q :  newPiece = new Queen(pos,C);;
						break;
				}
				P.losePiece(moving);
				P.receivePiece(newPiece);
				setPiece(newPiece,pos,B);
				Utils.setThreatsOnBoard(B);
			}
		}
	}
	/**
	 * Try moves until they are valid, then makes the move(same as askNMoveCoord, without getting moves from player)
	 * @param P
     * @param pOrig
     * @param pDest
	 * @return
	 */
	private boolean moveCoord(Player P,int pOrig,int pDest){
		//boolean losingMobility = false;
		Piece captured;
		boolean check= false;

		Bfuture = null;
		Bfuture = Utils.cloneAL(B);

		//Test the move 
		captured = tryMove(P,pOrig,pDest);
		//Verify if Player playing turn if is check
		check=Utils.isInCheck(Bfuture,P,posK1,posK2);

		tryMoveBack(P,pOrig,pDest,captured);
		if (check) {
			return false;
		} else {
			//Move is OK : YAY(hoping it is a good one)!
			return true;
		}
	}
	/**
	 * Gets moves from PLAYER and try them until they are valid, then makes the move
	 * @param P
	 * @return
	 */
	private Piece askNMoveCoord(Player P){
		Piece moving;
		Piece captured = null;
		//boolean losingMobility = false;
		boolean check= false;

		do
		{
			if (check) 
			{
				tryMoveBack(P,captured);
				printer.printCheck();
				printer.printBoard(B);
			}
			//Ask coordinates
			Bfuture = null;
			Bfuture = Utils.cloneAL(B);
			moving=printer.askMove(P,Bfuture,movingPiece, this);
			//Test the move 
			captured = tryMove(P);
			//Verify if Player playing turn if is check
			check=Utils.isInCheck(Bfuture,P,posK1,posK2);
			printer.printIsCheck(check);
		}while(check);		
		//Move is OK, Finalize it
		finalizeMove(moving,captured,P);
		return captured;
	}
	/**
	 * Makes back the move, supposing it is valid
	 * @param P
	 * @param Board
	 * @param pMoved
	 * @param pOrig
     * @param captured
	 * @return
	 */
	private void moveBackTo(Player P,Piece[] Board,int pMoved,int pOrig,Piece captured) {
		moveTo(P,Board,pMoved,pOrig);
		if (captured == null) {
			setPiece(new Nothing(pMoved,colorPiece.NONE),pMoved,Board);
		} else if (captured.getPos() == pMoved) {
			setPiece(captured,pMoved,Board);
		} else {
			setPiece(new Nothing(pMoved,colorPiece.NONE),pMoved,Board);
			setPiece(captured,captured.getPos(),Board);
		}				
	}
	/**
	 * Makes the move, supposing it is valid
	 * @param P
	 * @param Board
	 * @param Pinit
	 * @param Pfinal
	 * @return
	 */
	private Piece moveTo(Player P,Piece[] Board,int Pinit,int Pfinal) {
		Piece moving = getPiece(Pinit,Board);

		//Verify Castling conditions to move the Rook too
		if (moving.getType()==TypePiece.K)
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
		if (moving.getType()==TypePiece.P)
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
	private Piece setCaptures(Piece captured,Player P){
		if (((captured.getType()) != TypePiece.N) && P.isWhite()) {
			wcaptures.add(captured.getName());
			return captured;
		}else if (((captured.getType()) != TypePiece.N)&& !P.isWhite()) {
			bcaptures.add(captured.getName());
			return captured;
		}else{
			return captured;  
		}
	}
	/**
	 * Returns false if no potential move[without considering checks]is possible(Very rare case)
	 * @param P
	 * @return
	 */
	private Boolean isOneMobility(Player P){
		List<Piece> ps = P.getPieces();
		Iterator<Piece> itr = ps.iterator();
		int i=0;
		while(itr.hasNext()) {
			Piece p = itr.next();
			i = p.scanPotentialMoves();
			if (i>0) break;
		}
		return (i>0);
	}
	/**
	 * Saves Coordinates of KING, in case his move is not valid
	 */
	private void saveMoveKings(){
		posK1bu=posK1;
		posK2bu=posK2;
	}
	/**
	 * Reverts previous Coordinates of KING, when his move was not valid
	 */
	private void revertMoveKings(){
		posK1=posK1bu;
		posK2=posK2bu;
	}
	/**
	 * Resets Passing conditions check for a Pawn, when his move was not valid
	 */
	private void resetMovePawn(boolean W){
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
	private void setPiece(Piece p,int P,Piece[] Board){
		Board[P] = p;
		(getPiece(P,Board)).setPos(P);
		(getPiece(P,Board)).setCoord();
		(getPiece(P,Board)).setLCoord();
	}
	/**
	 * Tests every piece of the player and tries to find one legal move
	 * @param P
	 * @return
	 */
	private Boolean findOneMoveForPlayer(Player P) {
		List<Piece> pieces = P.getPieces();
		Iterator<Piece> itr = pieces.iterator();

		while ((itr.hasNext())) {
			Piece p = itr.next();
			int pOrig = p.getPos(); 
			Integer[] moves = p.getPossibleMovesList();
			for (Integer i : moves) {
				int pDest = i;
				boolean success = moveCoord(P,pOrig,pDest);
				if (success) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Organize the turn of the PLAYER
	 * @param M
	 * @param N
	 * @return
	 */
	private Boolean playTurn(Player M,Player N) {
		if(M.isWhite()) printer.printBoardState(B,M,1);	
		else if(!(M.isWhite())) printer.printBoardState(B,M,2);
		
		askNMoveCoord(M);
		
		printer.printBoard(B);
		printer.printCaptures(wcaptures,bcaptures);
		printer.printPlayersPiecesOnBoard(M,N);
		//M.printPossibleMoves();
		//N.printPossibleMoves();
		
		Boolean oneMoveFound = false;
		if (isOneMobility(N)) {
			oneMoveFound = findOneMoveForPlayer(N);
		}
		boolean check = false;
		
		if (Utils.isInCheck(B,N,posK1,posK2))
		{
			check = true;
			printer.oppInCheck(B,N,oneMoveFound);
		}
		
		Boolean result = false;
		if (!oneMoveFound) {
			if (check) result = true;
			else result = null;
		}
		return result;
	}
	@Override
    public void bigLeapSpawnSpotted(Piece P,boolean W) {
		if (W) {
			pawnW = P;
		} else {
			pawnB = P;
		}
	}
}