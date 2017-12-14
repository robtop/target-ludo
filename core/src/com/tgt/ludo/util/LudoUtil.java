package com.tgt.ludo.util;

import com.tgt.ludo.board.Board;
import com.tgt.ludo.player.action.Move;

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
		int dest = sittingIndex + move.getSquares();
//		System.out.println("sittingIndex: " + sittingIndex);
//		if (move.getSquares() + sittingIndex == Board.TOTAL_NUM_SQUARES - 1) {
//			// move to home square
//		} else
			if (dest >= Board.TOTAL_NUM_SQUARES) {
			return (move.getSquares() + sittingIndex) % Board.TOTAL_NUM_SQUARES;
		}
		
		//System.out.println("calulateDestIndex: " + dest);
		return dest;
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

		if (moveTempIndex == 0 && !pieceMove.getPiece().isHomeSq()) {
			moveTempIndex = startIndex + 1;
		} else if ((pieceMove.getPiece().getSittingSuare().getIndex() +  pieceMove.getPiece().getMoveCount()+1)%68 == startIndex) {
			// move to home square
			pieceMove.getPiece().setHomeSq(true);
			moveTempIndex = 0;
		} else if (moveTempIndex+1 >= Board.TOTAL_NUM_SQUARES) {
			moveTempIndex = startIndex + 1;
		} else {
			moveTempIndex += 1;
		}
		//System.out.println("calulateNextIndex: " + moveTempIndex);
		
		pieceMove.getPiece().setMoveCount(pieceMove.getPiece().getMoveCount()+1);
	//	System.out.println(
		//		"total moves: " +pieceMove.getPiece().getMoveCount());
		return moveTempIndex;
	}

}
