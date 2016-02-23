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
		 * @return
		 */
		public Piece askMove(Player J,Piece[] Board,int[] mW);		
		/**
		 * Prints THREATENING[every PIECE attacked by a given PIECE, one by one] of every PIECE of a given BOARD
		 * @param B
		 */
		public void printThreateningOnBoard(Piece[] B);
		/**
		 * Prints THREATEN[every PIECE attacking a given PIECE, one by one] of every PIECE of a given BOARD
		 * @param B
		 */
		public void printThreatenOnBoard(Piece[] B);
		/**
		 * Prints every PIECE of both PLAYER
		 * @param P1
		 * @param P2
		 */
		public void printPlayersPiecesOnBoard(Player... P);
		/**
		 * Prints and inform the PLAYER he has put his Foe in CHECK
		 * @param B
		 * @param J2
		 * @return
		 */
		public void oppInCheck(Piece[] B,Player J2,boolean moveFound);
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
		public void printBoardState(Piece[] B,Player J,int N);
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
		public void printBoard(Piece[] B);
		/**
		 * Game over
		 */
		public void printGameOver();
		/**
		 * Shows PLAYER STATS
		 */
		public void printStats();
}
