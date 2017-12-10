package com.tgt.ludo;

import com.badlogic.gdx.Gdx;
import com.tgt.ludo.board.Piece;

/**
 * Engine to enfocre rule of ludo.
 * 
 * @author robin
 *
 */
public class BasicRuleEngine implements RuleEngine {

	@Override
	public boolean validMove(Piece piece,int diceVal){
		return true;
	}
}
