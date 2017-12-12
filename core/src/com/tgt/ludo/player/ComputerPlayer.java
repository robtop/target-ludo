package com.tgt.ludo.player;

import java.util.List;

import com.tgt.ludo.RuleEngine;

public class ComputerPlayer extends Player{

	public ComputerPlayer(RuleEngine ruleEngine) {
		super(ruleEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Move play() {
		super.play();
		return null;
	}


	protected  List<Integer> rollDice() {
		//random number generator
		return null;
	}

}
