package com.anthony.chessgame.game;

import java.util.ArrayList;
import java.util.List;

import com.anthony.chessgame.piece.Piece;

//Class  PLAYER
public class Player{

	//NUMBER of the PLAYER
	private int number;
	//number of games won
	private int win;
	//number of games lost
	private int loss;
	//name of the PLAYER
	private String pname;
	//COLOR of the PIECE the PLAYER plays
	private boolean white;
	//playing pieces
	List<Piece> pieces = new ArrayList<>();
	private Piece movingPiece;

	/**
	 * CONSTRUCTOR : Player 
	 * @param i
	 * @param W
	 * @param N
	 */
	public Player(int i,boolean W,String N) {
		pname = N;
		number = i;
		win=0;
		loss=0;
		white = W;	  
	}

	public void receivePiece(Piece P) {
		if (!pieces.contains(P)) pieces.add(P);
	}
	public void losePiece(Piece P) {
		pieces.remove(P);
	}
	public List<Piece> getPieces() {
		return pieces;
	}
	public int scanPossibleMoves() {
		int result = 0;
		for (Piece p : pieces) {
			result += p.scanPotentialMoves();
		}
		return result;
	}
	public int scanCumulatedPower() {
		int result = 0;
		for (Piece p : pieces) {
			result += p.getType().getPower();
		}
		result -= Piece.TypePiece.K.getPower();
		return result;
	}
	public void printPossibleMoves() {
		for (Piece p : pieces) {
			p.printPossibleMoves();
		}
	}
	public int getNumber() {
		return number;
	}
	/**
	 * @return true if white, false otherwise
	 */
	public boolean isWhite() {return white;}
	/**
	 * 
	 * @return the name of the player
	 */
	public String getPname() {return pname;}
	public Piece getMovingPiece() {
		return movingPiece;
	}
	public void setMovingPiece(Piece movingPiece) {
		this.movingPiece = movingPiece;
	}
	/**
	 * Adds one WIN on this PLAYER and one LOSS on the other PLAYER
	 * @param opp The opposite player
	 */
	public void Victory(Player opp){
		win++;	
		opp.Defeat();
	}
	/**
	 * Adds one LOSS to this PLAYER
	 */
	public void Defeat(){loss++;}
	public int getVictories(){return win;}
	public int getDefeats(){return loss;}
}