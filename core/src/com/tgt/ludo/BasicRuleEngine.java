package com.tgt.ludo;

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
	
	public  int getSingleDiceRoll(){
		int value = (int) Math.floor((Math.random()*7));
		value =6;
		System.out.println("Dice Roll: "+value);
		return value;
	
	}
	
}
