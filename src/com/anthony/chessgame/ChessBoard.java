package com.anthony.chessgame;
import java.util.ArrayList;
import java.util.Scanner;

import com.anthony.chessgame.Piece.colorPiece;
public class ChessBoard
{		
	private Scanner sc = new Scanner(System.in);
	private Player P1;
	private Player P2;
	private static ArrayList<String> wcaptures = new ArrayList<String>();
	private static ArrayList<String> bcaptures = new ArrayList<String>();
	private ArrayList <Piece> B = new ArrayList <Piece>();
	boolean checkmate;
	boolean checkmate2;
	final static int pieces[]={5,2,4,6,7,2,3,5,1,1,1,1,1,1,1,1,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
		1,1,1,1,1,1,1,1,5,2,3,6,7,2,4,5};

	public ChessBoard(){
		System.out.println("         Chess HvH(By Anthony)\n\n");	

		P1 = new Player(1,true,"a");
		System.out.println("");
		P2 = new Player(2,false,"b");
		System.out.println("\n\n");

		checkmate = false;
		for (int i=0;i<64;i++) 
		{
			if (i/16==0) putPiece(i,colorPiece.WHITE); 
			else if (i/16==3)	putPiece(i,colorPiece.BLACK);  
			else putPiece(i,colorPiece.NONE); 	  
		}	  
		printBoard();

		while (checkmate2)
		{
			checkmate = playTurn(P1);
			if (checkmate) break;
			checkmate2 = playTurn(P2);
		}
	}
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
			System.out.println("Il faut davantage travailler.");
		}	

		B.add(put);
		return put;
	}
	public void askMoveCoord(int[]Cmove){
		System.out.println("Donner une position de départ");

		while(((sc.nextLine()).length())!=2) {
			System.out.println("Position non valide, réessayez.");
		}
		String result = sc.nextLine();
		int L = result.charAt(0) - 'a';
		int N = result.charAt(1) - '1';
		Cmove[0] = L*8 + N;

		System.out.println("Donner une position d'arrivée");

		while(((sc.nextLine()).length())!=2) {
			System.out.println("Position non valide, réessayez.");
		}
		String result2 = sc.nextLine();
		int L2 = result2.charAt(0) - 'a';
		int N2 = result2.charAt(1) - '1';
		Cmove[1] = L2*8 + N2;
	}
	public Piece moveTo(int Pinit,int Pfinal)
	{
		Piece moving = getPiece(Pinit); 	
		Piece captured = getPiece(Pfinal); 	
		setPiece(new Nothing(Pinit,colorPiece.NONE),Pinit);
		setPiece(moving,Pfinal);
		if ((captured.getName())!="  ") 
		{
			if (captured.isWhite()) bcaptures.add(captured.getName());
			else wcaptures.add(captured.getName());
			return captured;
		}
		else
		{
			return new Nothing(Pfinal,colorPiece.NONE);  
		}

	}

	public void setPiece(Piece p, int P){B.set(P,p);}
	public Piece getPiece(int P){return B.get(P);}  
	public colorPiece getPieceC(int P){return (B.get(P)).getColor();} 	 
	public String getPieceN(int P){return (B.get(P)).getName();} 	
	public int getPiecePos(int P){return (B.get(P)).getPos();}
	public int getPiecePosx(int P){return (B.get(P)).getPosx();}
	public int getPosy(int P){return (B.get(P)).getPosy();}
	public char getPieceLposx(int P){return (B.get(P)).getLposx();}
	public char getPieceLposy(int P){return (B.get(P)).getLposy();}

	public boolean playTurn(Player Pl){
		int Cmove[]={0,0};
		askMoveCoord(Cmove);

		moveTo(Cmove[0],Cmove[1]);	
		return true;
	}

	public void displayCaptures(){
		System.out.print("\n");
		
		System.out.print("White : ");
		if (wcaptures.size() > 1)System.out.print(wcaptures.get(0));
		for (int i=1;i<wcaptures.size();i++){
			System.out.print(" , ");
			System.out.print(wcaptures.get(i));
		}
		
		System.out.print("Black : ");
		if (bcaptures.size() > 1)System.out.print(bcaptures.get(0));
		for (int i=1;i<bcaptures.size();i++){
			System.out.print(" , ");
			System.out.print(bcaptures.get(i));
		}
		
		System.out.print("\n");
	}
	
	public void printBoard(){
		System.out.println("   +----+----+----+----+----+----+----+----+");
		System.out.print(" 8 + "+getPieceN(56)+" | ");
		for (int i = 1;i<64;i++)
		{
			int Y = i % 8;
			int newpos = 64 - ((i / 8)+1)*8 + Y;
			char letter = (char) (((int)'8') - i/8);

			if (Y==0) 
			{	
				System.out.print("\n");
				System.out.println("   +----+----+----+----+----+----+----+----+");
				System.out.print(" "+letter+" | "+getPieceN(newpos)+" | ");
			}
			else System.out.print(getPieceN(newpos)+" | ");
			//System.out.println("i "+i);
			//System.out.println("pos "+newpos);
		}
		System.out.println("");
		System.out.println("   +----+----+----+----+----+----+----+----+");
		System.out.println("     A    B    C    D    E    F    G    H  ");
		displayCaptures();
	}
	public void Stats(){ 
	}
}



