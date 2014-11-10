package com.anthony.chessgame.util;
import java.util.ArrayList;
import java.util.Scanner;

import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.piece.Piece;

//This class tries to countain every important methods, those using System.out.print for example or Scanner
//Has to be modified to become a graphic game
//This class is not instanciated : everything is STATIC
public class Print {	

	//SCANNER for interactions with PLAYER
	private static Scanner sc = new Scanner(System.in);
	//Char version of coordinates
	private static int destx;
	private static int desty;

	//FAST Print
	public static void printTitle(){System.out.println("      Chess HumanVsHuman(By Anthony)\n\n");}	
	public static void printIsCheck(boolean check){System.out.println("Check ????? : "+check);}
	public static void printLine(){System.out.println("\n");}
	
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
	//Asks move proposed by PLAYER, modifying parameters destx,desty,pinit,pfinal
	public static void askMove(Player J,ArrayList<Piece> Board,int[] mW){
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
				mW[0] = 8*y+x; 
				mW[1] = 0;
				pieceisok=Utils.comparePieceC(Board,mW[0],J.isWhite());
				if (!pieceisok) System.out.println("Invalid piece.");
			}
			while(!pieceisok);

			//Partie 2 : Destination
			System.out.println("Where to move it ?");
			if ((L=sc.nextLine()).length()==2)
			{
				setDest(L);	
				mW[1] = Utils.getPos(destx, desty);
				moveisok = Utils.getPiece(Board,mW[0]).checkMove(destx,desty,J.isWhite(),J,Board);
				if (!moveisok) System.out.println("Coup non valide.");
			}
			else System.out.println("Erreur.");
		}
	}
	
	//Prints THREATENING[every PIECE attacked by a given PIECE, one by one] of every PIECE of a given BOARD
	public static void printThreateningOnBoard(ArrayList<Piece> B){
		for (int t=0;t<64;t++)
		{
			if (!Utils.isVoid(B,t)){
				String name =(B.get(t)).getName();
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
			if (!Utils.isVoid(B,t)){
				String name =(B.get(t)).getName();
				System.out.println(name);
				System.out.println("x : "+(B.get(t)).getPosx()+" y : "+(B.get(t)).getPosy());
				(B.get(t)).printThreatenNames();
			}
		}
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
	//Prints captured pieces
	public static void printCaptures(ArrayList<String> wcaptures,ArrayList<String> bcaptures){
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

	//Prints the actual BOARD state
	public static void printBoard(ArrayList<Piece> B){
		System.out.println("");	
		System.out.println("      a    b    c    d    e    f    g    h   ");	
		System.out.println("   +----+----+----+----+----+----+----+----+");
		System.out.print(" 8 | "+Utils.getPieceN(B,56)+" | ");
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
	//Shows PLAYER STATS
	public static void printStats(){ 
	}	

}
