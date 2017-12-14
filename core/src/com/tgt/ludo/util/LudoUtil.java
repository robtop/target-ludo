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

	public static int calulateNextIndex(Move pieceMove, int moveCount) {
		
		//shouldnt come here
		if(pieceMove.getPiece().isHomeSq())
		{
			return -99;
		}
		 int newMoveTempIndex=0;
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
		int currentIndex =  (pieceMove.getPiece().getSittingSuare().getIndex() + moveCount) % 68;
		
		newMoveTempIndex = currentIndex;
		if (currentIndex == 0 && !pieceMove.getPiece().isHomeSq()) {
			newMoveTempIndex = startIndex + 1;
		} else if ((pieceMove.getPiece().getMoveCount()+1)%Board.TOTAL_NUM_SQUARES == 0) {
			// move to home square
			pieceMove.getPiece().setHomeSq(true);
			newMoveTempIndex = 0;
		} else if (newMoveTempIndex+1 >= Board.TOTAL_NUM_SQUARES) {
			newMoveTempIndex = 0;
		} else {
			newMoveTempIndex += 1;
		}
		//System.out.println("calulateNextIndex: " + moveTempIndex);
		
		pieceMove.getPiece().setMoveCount(pieceMove.getPiece().getMoveCount()+1);
	//	System.out.println(
		//		"total moves: " +pieceMove.getPiece().getMoveCount());
		return newMoveTempIndex;
	}

}
