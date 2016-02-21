package com.anthony.chessgame.game;

import com.anthony.chessgame.util.ConsolePrinter;

public class Chess {

	/**
	 * Entry point of the program
	 * @param args none required
	 */
	public static void main(String[] args){
		ChessGame G = new ChessGame(new ConsolePrinter());
		G.start();
	}

}
