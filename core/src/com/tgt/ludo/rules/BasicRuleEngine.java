package com.tgt.ludo.rules;

import java.util.ArrayList;
import java.util.List;

import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Piece;
import com.tgt.ludo.player.Move;
import com.tgt.ludo.player.Player;

/**
 * Engine to enfocre rule of ludo.
 * 
 * @author robin
 *
 */
public class BasicRuleEngine implements RuleEngine {

	private Board board;
	
	
	@Override
	public boolean validMove(Piece piece, int diceVal) {

		if (piece.isRest() && diceVal == 6) {
			return true;
		}
	   
		return true;
	}

	int prev = 0;
	public int getSingleDiceRoll() {
		// get range 1 to 6
		int value = (int) Math.floor((Math.random() * 6)) + 1;
	//	if (prev != 6)
		//	value = 6;
		prev = value;
		System.out.println("Dice Roll: " + value);
		return value;

	}

	@Override
	public List<Move> getValidMoves(Player player, int diceVal) {
		List<Move> moves = new ArrayList<Move>();
		for (Piece piece : player.getPieces()) {
			if (piece.isRest() && diceVal == 6) {
				Move move = new Move(piece);
				move.setSquares(0);
				move.setStart(true);
				moves.add(move);
			}
			
			if(pieceCanMove(piece, diceVal)){
				Move move = new Move(piece);
				move.setSquares(diceVal);
				moves.add(move);
			}
		}

		return moves;
	}
   
	private boolean pieceCanMove(Piece piece,int diceVal){
		
		return true;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	public BasicRuleEngine(Board board) {
		this.board = board;
	}
}
