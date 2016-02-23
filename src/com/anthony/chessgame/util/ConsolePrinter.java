package com.anthony.chessgame.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.piece.Piece;

//This class tries to contain every important methods, those using System.out.print for example or Scanner
//Has to be modified to become a graphic game
//This class is not instantiated : everything is STATIC
public class ConsolePrinter implements IPrint{	

	//SCANNER for interactions with PLAYER
	private static Scanner sc = new Scanner(System.in);
	//Char version of coordinates
	private static int destx;
	private static int desty;

	/**
	 * 
	 */
	public void printTitle(){System.out.println("      Chess HumanVsHuman(By Anthony)\n\n");}	
	/**
	 * 
	 */
	public void printIsCheck(boolean check){System.out.println("Check ????? : "+check);}
	/**
	 * 
	 */
	public void printLine(){System.out.println("\n");}
	/**
	 * Transforms COORDINATES into CHAR
	 */
	public void setDest(String L){
		destx = (int)((int)(L.charAt(0))-(int)'a');
		desty = (int)((int)(L.charAt(1))-(int)'1');
	}
	/**
	 * Asks and Returns a name for the PLAYER of COLOR W
	 */
	public String askName(boolean W){
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
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private Boolean isValid(int x,int y){
		return (x>=0)&&(x<Piece.BOARD_SIZE)&&(y>=0)&&(y<Piece.BOARD_SIZE);
	}
	/**
	 * Asks move proposed by PLAYER, modifying parameters destx,desty,pinit,pfinal
	 */
	public Piece askMove(Player J,Piece[] Board,int[] mW){
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
				L=L.toLowerCase();
				int x = (int)L.charAt(0) - (int) 'a';
				int y = (int)L.charAt(1) - (int) '1';
				if (!isValid(x,y)) {
					pieceisok = false;
				} else {
					mW[0] = Piece.BOARD_SIZE*y+x; 
					mW[1] = 0;
					pieceisok=Utils.comparePieceC(Board,mW[0],J.isWhite());
				}
				if (!pieceisok) System.out.println("Invalid piece.");
			}
			while(!pieceisok);

			//Partie 2 : Destination
			System.out.println("Where to move it ?");
			if ((L=sc.nextLine()).length()==2)
			{
				setDest(L.toLowerCase());	
				mW[1] = Utils.getPos(destx, desty);
				moveisok = Utils.getPiece(Board,mW[0]).checkMove(destx,desty,J.isWhite(),J,Board);
				if (!moveisok) System.out.println("Coup non valide.");
			}
			else System.out.println("Erreur.");
		}
		return Utils.getPiece(Board,mW[0]);
	}
	/**
	 * Prints THREATENING[every PIECE attacked by a given PIECE, one by one] of every PIECE of a given BOARD
	 */
	public void printThreateningOnBoard(Piece[] B){
		for (int t=0;t<64;t++)
		{
			if (!Utils.isVoid(B,t)){
				String name =B[t].getName();
				System.out.println(name);
				System.out.println("x : "+B[t].getPosx()+" y : "+B[t].getPosy());
				B[t].printThreateningNames();
			}
		}
	}	
	/**
	 * Prints THREATEN[every PIECE attacking a given PIECE, one by one] of every PIECE of a given BOARD
	 */
	public void printThreatenOnBoard(Piece[] B){
		for (int t=0;t<64;t++)
		{
			if (!Utils.isVoid(B,t)){
				String name =B[t].getName();
				System.out.println(name);
				System.out.println("x : "+B[t].getPosx()+" y : "+B[t].getPosy());
				B[t].printThreatenNames();
			}
		}
	}	
	/**
	 * Prints THREATEN[every PIECE attacking a given PIECE, one by one] of every PIECE of a given BOARD
	 */
	public void printPlayersPiecesOnBoard(Player... P){
		for (Player p : P) {
			System.out.print("Player "+p.getNumber()+" pieces : ");
			List<Piece> pieces = p.getPieces();
			Collections.sort(pieces,new Comparator<Piece>() {
			      public int compare(Piece p1, Piece p2) {
			          return new Integer(p1.getType().getPower()).compareTo(p2.getType().getPower());
			        }
			      });
			for (Piece pc : pieces) {
				System.out.print(pc.getName()+" ");
			}
			System.out.println("");
		}
	}	
	/**
	 * Prints and inform the PLAYER he has put his Foe in CHECK
	 */
	public void oppInCheck(Piece[] B,Player J2,boolean moveFound){
		System.out.println("You have put your opponent in check !");
		if(!moveFound) System.out.println("He seems to be Check Mate!");
	}
	/**
	 * Prints and inform the PLAYER he is in CHESS
	 */
	public void printCheck(){
		System.out.println("You are in Check, look closely !");
	}
	/**
	 * Prints whose turn it is
	 */
	public void printBoardState(Piece[] B,Player J,int N){
		System.out.println("Player "+N+"("+J.getPname()+") : Your turn to play!");
	}
	/**
	 * Prints captured pieces
	 */
	public void printCaptures(ArrayList<String> wcaptures,ArrayList<String> bcaptures){
		System.out.print("\n");

		System.out.print("White : ");
		if (wcaptures.size() >= 1) System.out.print(wcaptures.get(0));
		for (int i=1;i<wcaptures.size();i++){
			System.out.print(" , ");
			System.out.print(wcaptures.get(i));
		}
		
		System.out.print("\n");

		System.out.print("Black : ");
		if (bcaptures.size() >= 1)System.out.print(bcaptures.get(0));
		for (int i=1;i<bcaptures.size();i++){
			System.out.print(" , ");
			System.out.print(bcaptures.get(i));
		}

		System.out.print("\n");
	}
	/**
	 * Prints the actual BOARD state
	 */
	public void printBoard(Piece[] B){
		System.out.println("");	
		System.out.println("      a    b    c    d    e    f    g    h   ");	
		System.out.println("   +----+----+----+----+----+----+----+----+");
		System.out.print(" 8 | "+Utils.getPieceN(B,56)+" | ");
		for (int i = 1;i<64;i++)
		{
			int Y = i % Piece.BOARD_SIZE;
			int newpos = 64 - ((i / Piece.BOARD_SIZE)+1)*Piece.BOARD_SIZE + Y;
			char digit = (char) (((int)'8') - i/Piece.BOARD_SIZE);
			char digit2 = (char) (((int)'8') - i/Piece.BOARD_SIZE+1);

			if (Y==0) 
			{	
				System.out.print(digit2+"\n");
				System.out.println("   +----+----+----+----+----+----+----+----+");
				System.out.print(" "+digit+" | "+Utils.getPieceN(B,newpos)+" | ");
			}
			else System.out.print(Utils.getPieceN(B,newpos)+" | ");
			//System.out.println("i "+i);
			//System.out.println("pos "+newpos);
		}
		System.out.println("1");
		System.out.println("   +----+----+----+----+----+----+----+----+");
		System.out.println("     a    b    c    d    e    f    g    h  ");
	}
	/**
	 * Game Over
	 */
	public void printGameOver(){
		System.out.println("GAME OVER!");
	}
	/**
	 * Shows PLAYER STATS
	 */
	public void printStats(){ 
	}	
}
