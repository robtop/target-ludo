package com.tgt.ludo.gamestate;

import com.tgt.ludo.board.Board;
import com.tgt.ludo.player.Move;

public class LudoUtil {

	public static int calulateDestIndex(Move move) {

		Board.COLOR color = move.getPiece().getColor();
		int startIndex = 0;
		switch (color) {
		case GREEN:
			startIndex = Board.startIndexes.get(0);
			break;
		case YELLOW:
			startIndex = Board.startIndexes.get(1);
			break;
		case RED:
			startIndex = Board.startIndexes.get(2);
			break;
		case BLUE:
			startIndex = Board.startIndexes.get(3);
			break;
		}

		int sittingIndex = move.getPiece().getSittingSuare().getIndex();

		if (move.getSquares() + sittingIndex == Board.TOTAL_NUM_SQUARES - 1) {
			// move to home square
		} else if (move.getSquares() + sittingIndex >= Board.TOTAL_NUM_SQUARES) {
			return (move.getSquares() + sittingIndex) % Board.TOTAL_NUM_SQUARES;
		}
		return sittingIndex + move.getSquares();
	}

	public static int calulateNextIndex(Move pieceMove, int moveCount, int moveTempIndex) {
		Board.COLOR color = pieceMove.getPiece().getColor();
		int startIndex = 0;
		switch (color) {
		case GREEN:
			startIndex = Board.startIndexes.get(0);
			break;
		case YELLOW:
			startIndex = Board.startIndexes.get(1);
			break;
		case RED:
			startIndex = Board.startIndexes.get(2);
			break;
		case BLUE:
			startIndex = Board.startIndexes.get(3);
			break;
		}

		if (moveTempIndex == 0) {
			moveTempIndex += startIndex;
		} else if (moveCount + pieceMove.getPiece().getSittingSuare().getIndex() >= Board.TOTAL_NUM_SQUARES) {
			// move to home square
		} else if (moveTempIndex == Board.TOTAL_NUM_SQUARES) {
			moveTempIndex = 0;
		} else {
			moveTempIndex += 1;
		}
		return moveTempIndex;
	}

}
