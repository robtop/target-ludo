package com.tgt.ludo.player;

import com.tgt.ludo.board.Piece;

public class Move {

	private Piece piece;
	private int squares;

	public Move(Piece piece, int squares) {
		this.piece = piece;
		this.squares = squares;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public int getSquares() {
		return squares;
	}

	public void setSquares(int squares) {
		this.squares = squares;
	}
	
}
