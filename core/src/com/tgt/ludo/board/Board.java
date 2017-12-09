package com.tgt.ludo.board;

public class Board {

	// length of one arm of the board
	public static final int DIMENSION = 8;
	// start location of a player wrt dimension
	public static final int START = 0;
	// index from start point of each color
	public static final int JAIL_INDEX = 14;
	// special home square
	public static final int HOME_INDEX = 14;

	public static enum COLOR {
		GREEN, YELLOW, RED, BLUE
	}

	public void setup() {

	}
}
