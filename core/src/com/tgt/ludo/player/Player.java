package com.tgt.ludo.player;

import java.util.List;

import com.tgt.ludo.board.Piece;

public abstract class Player {

	private boolean turn = false;
	private List<Piece> pieces;

	// main game loop
	public abstract void play();

	public boolean isTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public List<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
	}

}
