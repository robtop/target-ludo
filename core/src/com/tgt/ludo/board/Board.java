package com.tgt.ludo.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tgt.ludo.player.Move;

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

	// outer track squares
	private List<Square> squares;

	private Map<COLOR, List<Square>> homeSquaresMap;

	private Map<COLOR, List<Square>> restSquaresMap;

	private Map<COLOR, List<Piece>> piecesMap;
	private int players = 4;
	
	public void setup(int players) {
		this.players = players;
		setup();
	}

	public void setup() {
		createSquares();
		createHomeSquares();
		createRestSquares();
		createPieces();
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

	private void createHomeSquares() {
		homeSquaresMap = new HashMap<Board.COLOR, List<Square>>();
		homeSquaresMap.put(COLOR.GREEN, createHomeSquareList(COLOR.GREEN));

		homeSquaresMap.put(COLOR.YELLOW, createHomeSquareList(COLOR.YELLOW));

		homeSquaresMap.put(COLOR.RED, createHomeSquareList(COLOR.RED));

		homeSquaresMap.put(COLOR.BLUE, createHomeSquareList(COLOR.BLUE));

	}

	private void createRestSquares() {
		restSquaresMap = new HashMap<Board.COLOR, List<Square>>();
		restSquaresMap.put(COLOR.GREEN, createRestSquareList(COLOR.GREEN));
		restSquaresMap.put(COLOR.YELLOW, createRestSquareList(COLOR.YELLOW));
		restSquaresMap.put(COLOR.RED, createRestSquareList(COLOR.RED));
		restSquaresMap.put(COLOR.BLUE, createRestSquareList(COLOR.BLUE));
	}

	private List<Square> createHomeSquareList(Board.COLOR color) {
		List<Square> list = new ArrayList<Square>();
		for (int i = 0; i < DIMENSION - 1; i++) {
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
			sq.setRestSquare(true);
			sq.setHome(true);
			sq.setColor(color);
			list.add(sq);
		}
		return list;
	}

	private void createPieces() {
		piecesMap = new HashMap<Board.COLOR, List<Piece>>();
		piecesMap.put(COLOR.GREEN, createPiecesList(COLOR.GREEN));
		if (players > 2) {
			piecesMap.put(COLOR.YELLOW, createPiecesList(COLOR.YELLOW));
		}
		piecesMap.put(COLOR.RED, createPiecesList(COLOR.RED));
		if (players > 3) {
			piecesMap.put(COLOR.BLUE, createPiecesList(COLOR.BLUE));
		}
	}

	private List<Piece> createPiecesList(Board.COLOR color) {
		List<Piece> list = new ArrayList<Piece>();
		for (int i = 0; i < 4; i++) {
			Piece piece = new Piece();
			piece.setColor(color);
			list.add(piece);
			placePieceInRestSq(piece, color);
		}
		return list;
	}

	private void placePieceInRestSq(Piece piece, COLOR color) {
		// find empty square and place
		for (Square sq : restSquaresMap.get(color)) {
			if (sq.getPieces() == null || sq.getPieces().isEmpty()) {
				List<Piece> list = new ArrayList<Piece>();
				list.add(piece);
				piece.setSittingSuare(sq);
				sq.setPieces(list);
				return;
			}
		}
	}

	public void movePiece(Move move) {
		Piece piece = move.getPiece();
		
		if(piece.getSittingSuare().isRestSquare()){
			
			//get stat of this color
			Square startSquare = squares.get(0);
			if(startSquare.getPieces() == null){
				startSquare.setPieces(new ArrayList<Piece>());
			}
			startSquare.getPieces().add(piece);
			return;
		}
		
		int index = piece.getSittingSuare().getIndex();
		piece.getSittingSuare().getPieces().remove(piece);
		squares.get(index).getPieces().add(piece);
	}

	public List<Square> getSquares() {
		return squares;
	}

	public void setSquares(List<Square> squares) {
		this.squares = squares;
	}

	public Map<COLOR, List<Square>> getHomeSquaresMap() {
		return homeSquaresMap;
	}

	public Map<COLOR, List<Square>> getRestSquaresMap() {
		return restSquaresMap;
	}

	public Map<COLOR, List<Piece>> getPiecesMap() {
		return piecesMap;
	}

}