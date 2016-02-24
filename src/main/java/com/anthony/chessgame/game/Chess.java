package com.anthony.chessgame.game;

import com.anthony.chessgame.util.PrinterFactory;
import com.anthony.chessgame.util.PrinterFactory.PrinterType;

public class Chess {

	/**
	 * Entry point of the program
	 * @param args none required
	 */
	public static void main(String[] args){
		ChessGame G = new ChessGame(PrinterFactory.getPrinter(PrinterType.SWING));
		G.start();
	}

}
