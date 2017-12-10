package com.tgt.ludo.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	//outer track squares
	private List<Square> squares;

	private Map<COLOR, List<Square>> homeSquares;
	
	private Map<COLOR, List<Square>> restSquares;

	public void setup() {
		createSquares();
		createHomeSquares();
		createRestSquares();
	}

	private void createSquares() {
		int totalOuterSqrs = DIMENSION * 4 * 2 + 4;
		int numHomeSqrsPerColor = DIMENSION - 1;
		squares = new ArrayList<Square>();
		for (int i = 0; i < totalOuterSqrs; i++) {
			Square sq = new Square();
			sq.setIndex(i);

			if (i % JAIL_INDEX == 0) {
				sq.setJail(true);
			}

			if (i == HOME_INDEX) {
				sq.setHome(true);
			}
			squares.add(sq);
		}

		// squares.get(0).setColor(COLOR.GREEN);
		// squares.get(DIMENSION * 2 + 2).setColor(COLOR.YELLOW);
		// squares.get(DIMENSION * 4 + 2).setColor(COLOR.RED);
		// squares.get(DIMENSION * 6 + 2).setColor(COLOR.BLUE);
	}

	private void createHomeSquares(){
		homeSquares = new HashMap<Board.COLOR, List<Square>>();
		homeSquares.put(COLOR.GREEN, createHomeSquareList(COLOR.GREEN));
		homeSquares.put(COLOR.YELLOW, createHomeSquareList(COLOR.YELLOW));
		homeSquares.put(COLOR.RED, createHomeSquareList(COLOR.RED));
		homeSquares.put(COLOR.BLUE, createHomeSquareList(COLOR.BLUE));
	}
	
	private void createRestSquares(){
		restSquares = new HashMap<Board.COLOR, List<Square>>();
		restSquares.put(COLOR.GREEN, createRestSquareList(COLOR.GREEN));
		restSquares.put(COLOR.YELLOW, createRestSquareList(COLOR.YELLOW));
		restSquares.put(COLOR.RED, createRestSquareList(COLOR.RED));
		restSquares.put(COLOR.BLUE, createRestSquareList(COLOR.BLUE));
	}

	private List<Square> createHomeSquareList(Board.COLOR color) {
		List<Square> list = new ArrayList<Square>();
		for (int i = 0; i <  DIMENSION-1; i++) {
			Square sq = new Square();
			sq.setHome(true);
			sq.setColor(color);
			list.add(sq);
		}
		return list;
	}
	
	private List<Square> createRestSquareList(Board.COLOR color) {
		List<Square> list = new ArrayList<Square>();
		for (int i = 0; i < 4; i++) {
			Square sq = new Square();
			sq.setHome(true);
			sq.setColor(color);
			list.add(sq);
		}
		return list;
	}

	public List<Square> getSquares() {
		return squares;
	}

	public void setSquares(List<Square> squares) {
		this.squares = squares;
	}

	public Map<COLOR, List<Square>> getHomeSquares() {
		return homeSquares;
	}

	public void setHomeSquares(Map<COLOR, List<Square>> homeSquares) {
		this.homeSquares = homeSquares;
	}

	public Map<COLOR, List<Square>> getRestSquares() {
		return restSquares;
	}

	public void setRestSquares(Map<COLOR, List<Square>> restSquares) {
		this.restSquares = restSquares;
	}

}
