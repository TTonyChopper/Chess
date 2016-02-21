package com.anthony.chessgame.util;

import java.util.ArrayList;

import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.piece.Piece;

public interface IPrint {
		//FAST ConsolePrinter
		public void printTitle();	
		public void printIsCheck(boolean check);
		public void printLine();
		
		/**
		 * Transforms COORDINATES into CHAR
		 * @param L
		 */
		public void setDest(String L);
		/**
		 * Asks and Returns a name for the PLAYER of COLOR W
		 * @param W
		 * @return
		 */
		public String askName(boolean W);
		/**
		 * Asks move proposed by PLAYER, modifying parameters destx,desty,pinit,pfinal
		 * @param J
		 * @param Board
		 * @param mW
		 */
		public void askMove(Player J,ArrayList<Piece> Board,int[] mW);		
		/**
		 * Prints THREATENING[every PIECE attacked by a given PIECE, one by one] of every PIECE of a given BOARD
		 * @param B
		 */
		public void printThreateningOnBoard(ArrayList<Piece> B);
		/**
		 * Prints THREATEN[every PIECE attacking a given PIECE, one by one] of every PIECE of a given BOARD
		 * @param B
		 */
		public void printThreatenOnBoard(ArrayList<Piece> B);
		/**
		 * Prints and inform the PLAYER he has put his Foe in CHESS
		 * @param B
		 * @param J2
		 * @return
		 */
		public boolean oppInCheck(ArrayList<Piece> B,Player J2);
		/**
		 * Prints and inform the PLAYER he is in CHESS
		 */
		public void printCheck();
		/**
		 * Prints whose turn it is
		 * @param B
		 * @param J
		 * @param N
		 */
		public void printBoardState(ArrayList<Piece> B,Player J,int N);
		/**
		 * Prints captured pieces
		 * @param wcaptures
		 * @param bcaptures
		 */
		public void printCaptures(ArrayList<String> wcaptures,ArrayList<String> bcaptures);
		/**
		 * Prints the actual BOARD state
		 * @param B
		 */
		public void printBoard(ArrayList<Piece> B);
		/**
		 * Shows PLAYER STATS
		 */
		public void printStats();
}
