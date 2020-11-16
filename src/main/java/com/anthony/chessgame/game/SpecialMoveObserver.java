package com.anthony.chessgame.game;

import com.anthony.chessgame.piece.Piece;

public interface SpecialMoveObserver {
	/**
	 *
	 * @param P
	 * @param W
	 */
	void bigLeapSpawnSpotted(Piece P,boolean W);
	/**
	 *
	 * @param W
	 * @return
	 */
	Piece getFoePawn(boolean W);
}
