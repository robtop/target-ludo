package com.tgt.ludo.player;

import java.util.List;

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
		return null;
	}


	protected  List<Integer> rollDice() {
		//random number generator
		return null;
	}

}
