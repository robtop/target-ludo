package com.tgt.ludo.rules;

import com.tgt.ludo.board.Piece;

/**
 * Engine to enfocre rule of ludo.
 * 
 * @author robin
 *
 */
public interface RuleEngine {

	public boolean validMove(Piece piece,int diceVal);
	
	public  int getSingleDiceRoll();
}
