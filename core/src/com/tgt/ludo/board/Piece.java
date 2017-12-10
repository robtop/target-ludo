package com.tgt.ludo.board;

public class Piece {
	
	private Board.COLOR color;
	private boolean rest;
	private boolean home;
	private Square sittingSuare;

	public Board.COLOR getColor() {
		return color;
	}

	public void setColor(Board.COLOR color) {
		this.color = color;
	}

	public boolean isRest() {
		return rest;
	}

	public void setRest(boolean rest) {
		this.rest = rest;
	}

	public boolean isHome() {
		return home;
	}

	public void setHome(boolean home) {
		this.home = home;
	}

	public Square getSittingSuare() {
		return sittingSuare;
	}

	public void setSittingSuare(Square sittingSuare) {
		this.sittingSuare = sittingSuare;
	}
	
	
}
