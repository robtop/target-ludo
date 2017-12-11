package com.tgt.ludo.player;

import java.util.List;

public class ComputerPlayer extends Player{

	@Override
	public Move play() {
		super.play();
		return null;
	}

	@Override
	protected  List<Integer> rollDice() {
		//random number generator
		return null;
	}

}
