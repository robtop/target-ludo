package com.tgt.ludo.rules;

import java.util.List;

import com.tgt.ludo.board.Piece;
import com.tgt.ludo.player.Move;
import com.tgt.ludo.player.Player;

/**
 * Engine to enfocre rule of ludo.
 * 
 * @author robin
 *
 */
public interface RuleEngine {

	public boolean validMove(Piece piece,int diceVal);
	
	public List<Move> getValidMoves(Player player,int diceVal);
	
	public  int getSingleDiceRoll();
}
