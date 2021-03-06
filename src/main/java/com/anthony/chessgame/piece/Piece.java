package com.anthony.chessgame.piece;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.util.Utils;
//MOTHER Class for every PIECE, CHECKMOVE & SETTHREATS are OVERRIDEN
public abstract class Piece{
	
	public final static int BOARD_SIZE = 8;
	//enum declaration for pieces	
	public enum TypePiece{Bb("B",3),Bw("B",3),K("K",999),Kn("N",3),N(" ",0),O("X",0),P("P",1),Q("Q",9),R("R",5);
	private String name;
	//nominal value of the piece
	private int power;
	TypePiece(String name, int power){
		this.name = name;
		this.power = power;
	}
	public String getN(){
		return name;
	}
	public int getPower(){
		return power;
	}
	}; 
	//enum declaration for colors
	public enum colorPiece{WHITE(true),BLACK(false),NONE(null);
	private Boolean W;
	private colorPiece(Boolean W){
		this.W = W;
	}
	public Boolean getW(){
		return W;
	}
	}; 

	//Type of this PIECE
	protected TypePiece type;
	//COLOR of this PIECE
	protected colorPiece color;
	//name with color
	protected String name;
	//Contains each PIECE attacking this PIECE
	protected ArrayList<Piece> threaten;
	//Contains each PIECE(friendly or not) attacked by this PIECE, can be OutOfBoard though 
	protected ArrayList<Piece> threatening;
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
		threaten = new ArrayList<>();
		threatening = new ArrayList<>();
		possibleMoves = new TreeSet<>();
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
	public Integer[] getPossibleMovesList(){return possibleMoves.toArray(new Integer[possibleMoves.size()]);};
	public int getPos() {return pos;}
	public int getPosx() {return posx;}
	public int getPosy() {return posy;}
	public char getLposx() {return lposx;}
	public char getLposy() {return lposy;}
	public String getName(){return name;}
	public Boolean isWhite(){return color.getW();}
	public TypePiece getType(){return type;}
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
	public abstract boolean setThreats(Piece[] B);
	/**
	 * 
	 * @return The number of potential moves the piece can make
	 */
	public int scanPotentialMoves(){
		return 0;
	}
	/**
	 * Calls checkMove of every PIECE of a given BOARD
	 * @param Px
	 * @param Py
	 * @param J
	 * @param B
	 * @return
	 */
	public abstract boolean checkMove(int Px, int Py,Player J,Piece[] B);
	/**
	 * 
	 * @param type
	 * @param color
	 */
	protected void definePiece(TypePiece type, colorPiece color) {
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
		posx = pos % BOARD_SIZE ;
		posy = pos / BOARD_SIZE ;
	}
	/**
	 * 
	 */
	public void setLCoord(){
		lposx = (char)(posx  + (int)('a'));
		lposy = (char)(posy  + (int)('1'));
	}
	/**
	 * 
	 */
	public void setLToCoord(){
		posx = ((int)lposx  - (int)('a'));
		posy = ((int)lposy  - (int)('1'));
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
	 * Prints each possible moves from this piece
	 */
	public void printPossibleMoves(){
		System.out.print("Possible moves for "+getName()+" : ");
		possibleMoves.stream().map(possibleMove -> possibleMove + " ").forEach(System.out::print);
		System.out.println("");
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + pos;
		result = prime * result + posx;
		result = prime * result + posy;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pos != other.pos)
			return false;
		if (posx != other.posx)
			return false;
		if (posy != other.posy)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Piece [name=" + name + ", pos=" + pos + ", posx=" + posx
				+ ", posy=" + posy + ", lposx=" + lposx + ", lposy=" + lposy
				+ "]";
	}
}