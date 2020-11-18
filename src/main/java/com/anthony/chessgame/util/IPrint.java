package com.anthony.chessgame.util;

import java.util.ArrayList;

import com.anthony.chessgame.game.ChessGame;
import com.anthony.chessgame.game.MovingPiece;
import com.anthony.chessgame.game.Player;
import com.anthony.chessgame.piece.Piece;

public interface IPrint {
		//FAST ConsolePrinter
		void printTitle();
		void printIsCheck(boolean check);
		void printLine();
		
		/**
		 * Transforms COORDINATES into CHAR
		 * @param L
		 */
		void setDest(String L);
		/**
		 * Asks and Returns a name for the PLAYER of COLOR W
		 * @param W
		 * @return
		 */
		String askName(boolean W,ChessGame G);
		/**
		 * Asks move proposed by PLAYER, modifying parameters destx,desty,pinit,pfinal
		 * @param J
		 * @param Board
		 * @param movingPiece
		 * @return
		 */
		Piece askMove(Player J, Piece[] Board, MovingPiece movingPiece, ChessGame G);
		/**askMove
		 * 
		 * @return
		 */
		Piece.TypePiece askPromotion(ChessGame G);
		/**
		 * Prints THREATENING[every PIECE attacked by a given PIECE, one by one] of every PIECE of a given BOARD
		 * @param B
		 */
		void printThreateningOnBoard(Piece[] B);
		/**
		 * Prints THREATEN[every PIECE attacking a given PIECE, one by one] of every PIECE of a given BOARD
		 * @param B
		 */
		void printThreatenOnBoard(Piece[] B);
		/**
		 * Prints every PIECE of both PLAYER
		 * @param P
		 */
		void printPlayersPiecesOnBoard(Player... P);
		/**
		 * Prints and inform the PLAYER he has put his Foe in CHECK
		 * @param B
		 * @param J2
		 * @return
		 */
		void oppInCheck(Piece[] B,Player J2,boolean moveFound);
		/**
		 * Prints and inform the PLAYER he is in CHESS
		 */
		void printCheck();
		/**
		 * Prints whose turn it is
		 * @param B
		 * @param J
		 * @param N
		 */
		void printBoardState(Piece[] B,Player J,int N);
		/**
		 * Prints captured pieces
		 * @param wcaptures
		 * @param bcaptures
		 */
		void printCaptures(ArrayList<String> wcaptures,ArrayList<String> bcaptures);
		/**
		 * Prints the actual BOARD state
		 * @param B
		 */
		void printBoard(Piece[] B);
		/**
		 * Game over
		 */
		void printGameOver();
		/**
		 * Shows PLAYER STATS
		 */
		void printStats();
}
