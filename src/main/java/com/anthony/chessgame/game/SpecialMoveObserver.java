package com.anthony.chessgame.game;

import com.anthony.chessgame.piece.Piece;

public interface SpecialMoveObserver {
	public void bigLeapSpawnSpotted(Piece P,boolean W);
	public Piece getFoePawn(boolean W);
}
