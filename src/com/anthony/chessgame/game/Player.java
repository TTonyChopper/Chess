package com.anthony.chessgame.game;
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

	/**
	 * @return true if white, false otherwise
	 */
	public boolean isWhite() {return white;}
	/**
	 * 
	 * @return the name of the player
	 */
	public String getPname() {return pname;}
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

}