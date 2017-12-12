package com.tgt.ludo.player;

import java.util.ArrayList;
import java.util.List;

import com.tgt.ludo.board.Board.COLOR;
import com.tgt.ludo.rules.RuleEngine;
import com.tgt.ludo.ui.BoardRenderer;
import com.tgt.ludo.ui.LudoScreen;
import com.tgt.ludo.board.Dice;
import com.tgt.ludo.board.Piece;

public abstract class Player {

	private boolean turn = false;
	protected List<Piece> pieces;
    protected RuleEngine ruleEngine;
    protected COLOR color;
    protected boolean diceRolled = false;
    
    public Player(LudoScreen screen, RuleEngine ruleEngine) {
    	this.ruleEngine = ruleEngine;
	}
    //Extending Players should set this
    protected List<Integer> diceRolls = new ArrayList<Integer>();

	// main game loop
	public Move play(){
		if(diceRolled){
			//rollDice();
		}
		return null;
	}

	public boolean isTurn() {
		return turn;
	}

	/**
	 * Roll a single dice
	 * 
	 * @param dice - The dice to be rolled
	 * @param boardRenderer - to get the diceList and create new die if we get a six
	 * @return - list of dice values or null if we need another throw 
	 */
	protected  List<Integer> rollDice(Dice dice,BoardRenderer boardRenderer){
		List<Dice> diceList = boardRenderer.getDiceList();
		int value = ruleEngine.getSingleDiceRoll();
		dice.setDiceValue(value);
		dice.setRolled(true);
		if(value == 6){
			diceList.add(boardRenderer.createDiceInstance());
		} else {
			List<Integer> list = new ArrayList<Integer>();
			for(Dice diceTemp:diceList){
				list.add(dice.getDiceValue());
			}
			return list;
		}
		dice.setRolled(true);
		return null;
	}
	
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
