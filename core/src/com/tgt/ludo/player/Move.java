package com.tgt.ludo.player;

import com.tgt.ludo.board.Piece;

public class Move {

	private Piece piece;
	private int squares;

	public Move(Piece piece, int squares) {
		this.piece = piece;
		this.squares = squares;
	}
}
