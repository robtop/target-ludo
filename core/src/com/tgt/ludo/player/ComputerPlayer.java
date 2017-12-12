package com.tgt.ludo.player;

import java.util.ArrayList;
import java.util.List;

import com.tgt.ludo.board.Dice;
import com.tgt.ludo.rules.RuleEngine;
import com.tgt.ludo.ui.LudoScreen;

public class ComputerPlayer extends Player{

	public ComputerPlayer(LudoScreen screen,RuleEngine ruleEngine) {
		super(screen, ruleEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Move play() {
		super.play();
		System.out.println("Turn: "+this.color);
		List<Integer> list = rollDice();
		
		List<Move> moves = new ArrayList<Move>();
		for (Integer integer : list) {
			moves.addAll(ruleEngine.getValidMoves(this, integer));
		}
		if (moves.isEmpty()) {
			// skip turn
			return new Move(true);
		}
		
		return selectMove(moves);
	}


	private Move selectMove(List<Move> moves){
		//give first valid move
		return moves.get(0);
	}
	protected  List<Integer> rollDice() {
	
		List<Integer> rolls = new ArrayList<Integer>();
		int diceRole = ruleEngine.getSingleDiceRoll();
		rolls.add(diceRole);
		
		while(diceRole==6){
			diceRole = ruleEngine.getSingleDiceRoll();
			rolls.add(diceRole);
		}
		return rolls;
	}

}
