package com.tgt.ludo.player;

import java.util.List;

public class RemotePlayer extends Player {

	public RemotePlayer() {
	}

	@Override
	public Move play() {
		//TODO: connect to server to get the move
		return null;
	}
  
	@Override
	public void setTurn(boolean turn) {
		super.setTurn(turn);
		//TODO: connect to server and tell remove player that its their turn
		
	}

	@Override
	public List<Integer> rollDice() {
		//TODO: get roll dice from server
		return null;
	}
}
