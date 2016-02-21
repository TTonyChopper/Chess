package com.anthony.chessgame.piece;
import java.util.ArrayList;
import java.util.TreeSet;

import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.util.Utils;
//MOTHER Class for every PIECE, CHECKMOVE & SETTHREATS are OVERRIDEN
public abstract class Piece{

	//enum declaration for pieces	
	public static enum typePiece{Bb("B"),Bw("B"),K("K"),Kn("N"),N(" "),O("X"),P("P"),Q("Q"),R("R");
	private String name;
	private typePiece(String name){
		this.name = name;
	}
	public String getN(){
		return name;
	}
	}; 
	//enum declaration for colors
	public static enum colorPiece{WHITE(true),BLACK(false),NONE(null);
	private Boolean W;
	private colorPiece(Boolean W){
		this.W = W;
	}
	public Boolean getW(){
		return W;
	}
	}; 



	//Type of this PIECE
	protected typePiece type;
	//COLOR of this PIECE
	protected colorPiece color;
	//name with color
	protected String name;
	//Contains each PIECE attacking this PIECE
	protected ArrayList<Piece> threaten = new ArrayList<Piece>();
	//Contains each PIECE(friendly or not) attacked by this PIECE, can be OutOfBoard though 
	protected ArrayList<Piece> threatening = new ArrayList<Piece>();
	//Contains possible Moves
	TreeSet<Integer> possibleMoves;
	//Position of the PIECE on the 64 cases BOARD, from 0(a1) to 63(h8) 
	protected int pos;
	//Coordinates of the PIECE
	protected int posx;
	protected int posy;
	//Char corresponding to the coordinates ('a' for x=0,'1' for y=0)
	protected char lposx;
	protected char lposy;

	/**
	 * CONSTRUCTOR : create a PIECE at POSITION P 
	 * @param P
	 */
	public Piece(int P) {
		threaten = new ArrayList<Piece>(); 
		threatening = new ArrayList<Piece>();
		possibleMoves = new TreeSet<Integer>();
		pos = P;
		setCoord();
		setLCoord();
	}
	
	/**
	 * GETTERS
	 * for the King castling condition, overridden by King and Rook
	 * @return
	 */
	public boolean hasSpecialMove(){return false;};
	public boolean loseSpecialMove(){return false;};
	public int getPos() {return pos;}
	public int getPosx() {return posx;}
	public int getPosy() {return posy;}
	public char getLposx() {return lposx;}
	public char getLposy() {return lposy;}
	public String getName(){return name;}
	public Boolean isWhite(){return color.getW();}
	public typePiece getType(){return type;}
	public colorPiece getColor(){return color;}
	public ArrayList<Piece> getThreaten(){return threaten;}
	public boolean isThreatenKing(int P,Player J){
		if (J.isWhite()) return Utils.isKingW(threaten,P);	
		else if (!(J.isWhite())) return Utils.isKingB(threaten,P);
		else return false;
	}

	/**
	 * 
	 * @param B
	 * @return
	 */
	public abstract boolean setThreats(ArrayList <Piece> B);
	/**
	 * Calls checkMove of every PIECE of a given BOARD
	 * @param Px
	 * @param Py
	 * @param W
	 * @param J
	 * @param B
	 * @return
	 */
	public abstract boolean checkMove(int Px, int Py,boolean W,Player J,ArrayList<Piece> B);
	
	
	/**
	 * 
	 * @param type
	 * @param color
	 */
	public void definePiece(typePiece type, colorPiece color) {
		this.type = type;
		this.color = color;
		String C = (color.getW() == null) ? " " : ((color.getW()) ? "w" : "b"); 
		name = type.getN() + C;
	}
	/**
	 * 
	 * @param P
	 * @return
	 */
	public int setPos(int P) {return pos=P;}
	/**
	 * 
	 */
	public void setCoord(){
		posx = pos % 8 ;
		posy = pos / 8 ;
	}
	/**
	 * 
	 */
	public void setLCoord(){
		lposx = (char)(posx - 1 + (int)('a'));
		lposy = (char)(posy - 1 + (int)('1'));
	}
	/**
	 * 
	 */
	public void setLToCoord(){
		posx = ((int)lposx + 1 - (int)('a'));
		posy = ((int)lposy + 1 - (int)('1'));
	}



	/**
	 * Prints each THREATEN PIECE for this one 
	 */
	public void printThreateningNames(){
		for(int i=0;i<threatening.size();i++)
		{
			System.out.println("n "+(i+1)+" : "+(threatening.get(i)).getName());  
		}
	}
	/**
	 * Prints each PIECE Attacking this one
	 */
	public void printThreatenNames(){
		for(int i=0;i<threaten.size();i++)
		{
			System.out.println("n "+(i+1)+" : "+(threaten.get(i)).getName());  
		}
	}
	/**
	 * Resets THREATENING for this PIECE : is always called before updating it by erasing previous data
	 */
	public void clearThreatening(){threatening.clear();}
	/**
	 * Resets Possible Moves for this PIECE : is always called before updating it by erasing previous data	
	 */
	public void clearPossibleMoves(){possibleMoves.clear();}
	/**
	 * Resets THREATEN for this PIECE : is always called before updating it by erasing previous data
	 */
	public void clearThreaten(){threaten.clear();}
	/**
	 * Means this PIECE is Attacking the PIECE P
	 * P is added to THREATENING
	 * this PIECE is added to THREATEN of P
	 * @param P
	 */
	public void addThreatening(Piece P){threatening.add(P);(P.getThreaten()).add(this);}
}