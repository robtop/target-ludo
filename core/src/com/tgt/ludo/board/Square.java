package com.tgt.ludo.board;

import java.util.List;

public class Square {

	// maintain the index of the main outer track, home squares have
	// index 0 to (DIMENSION-1)
	private int index;
	private boolean home;
	private boolean jail;
	private boolean specialHome;
	private boolean startSquare;
	private Board.COLOR color;
	private List<Piece> pieces;

	public boolean isBlock() {
		int colorCount = 0;
		Piece oldPiece = null;
		if (pieces != null) {
			for (Piece piece : pieces) {
				if (oldPiece != null) {
					if (oldPiece.getColor().equals(piece.getColor())) {
						colorCount++;
					} else {
						break;
					}
				} else {
					colorCount = 1;
				}
			}
		}
		if (colorCount == pieces.size()) {
			return true;
		}
		return false;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isHome() {
		return home;
	}

	public void setHome(boolean home) {
		this.home = home;
	}

	public boolean isJail() {
		return jail;
	}

	public void setJail(boolean jail) {
		this.jail = jail;
	}

	public boolean isSpecialHome() {
		return specialHome;
	}

	public void setSpecialHome(boolean specialHome) {
		this.specialHome = specialHome;
	}

	public Board.COLOR getColor() {
		return color;
	}

	public void setColor(Board.COLOR color) {
		this.color = color;
	}

	public List<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
	}

	public boolean isStartSquare() {
		return startSquare;
	}

	public void setStartSquare(boolean startSquare) {
		this.startSquare = startSquare;
	}

}
