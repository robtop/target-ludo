package com.tgt.ludo.player;

import java.util.List;

import com.tgt.ludo.RuleEngine;
import com.tgt.ludo.board.Piece;

public abstract class Player {

	private boolean turn = false;
	protected List<Piece> pieces;
    protected RuleEngine ruleEngine;
    
	// main game loop
	public abstract Move play();

	public boolean isTurn() {
		return turn;
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
	

}
