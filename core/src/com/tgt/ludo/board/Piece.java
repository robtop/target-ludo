package com.tgt.ludo.board;

public class Piece {

	// keep a copy for convenience :(
	// private ModelInstance pieceInstance;
	private Board.COLOR color;

	// at rest
	private boolean rest;
	// at homesuares
	private boolean homeSq;
	private Square sittingSuare;
	private boolean shake;
	private boolean killed;
	
	//track how much it moved
	private int moveCount = 0;

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

	public boolean isHomeSq() {
		return homeSq;
	}

	public void setHomeSq(boolean homeSq) {
		this.homeSq = homeSq;
	}

	public Square getSittingSuare() {
		return sittingSuare;
	}

	public void setSittingSuare(Square sittingSuare) {
		this.sittingSuare = sittingSuare;
	}

	public boolean isShake() {
		return shake;
	}

	public void setShake(boolean shake) {
		this.shake = shake;
	}

	public boolean isKilled() {
		return killed;
	}

	public void setKilled(boolean killed) {
		this.killed = killed;
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

}
