package com.tgt.ludo.player;

import java.util.ArrayList;
import java.util.List;

import com.tgt.ludo.RuleEngine;
import com.tgt.ludo.board.Board.COLOR;
import com.tgt.ludo.board.Piece;

public abstract class Player {

	private boolean turn = false;
	protected List<Piece> pieces;
    protected RuleEngine ruleEngine;
    protected COLOR color;
    protected boolean diceRolled = false;
    
    //Extending Players should set this
    protected List<Integer> diceRolls = new ArrayList<Integer>();

	// main game loop
	public Move play(){
		if(diceRolled){
			rollDice();
		}
		return null;
	}

	public boolean isTurn() {
		return turn;
	}

	protected abstract  List<Integer> rollDice();
	
	public void setTurn(boolean turn) {
		this.turn = turn;
	}

	public List<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
	}

	public RuleEngine getRuleEngine() {
		return ruleEngine;
	}

	public void setRuleEngine(RuleEngine ruleEngine) {
		this.ruleEngine = ruleEngine;
	}

	public boolean isDiceRolled() {
		return diceRolled;
	}

	public void setDiceRolled(boolean diceRolled) {
		this.diceRolled = diceRolled;
	}

	public List<Integer> getDiceRolls() {
		return diceRolls;
	}

	public void setDiceRolls(List<Integer> diceRolls) {
		this.diceRolls = diceRolls;
	}

	public COLOR getColor() {
		return color;
	}

	public void setColor(COLOR color) {
		this.color = color;
	}
	
}
