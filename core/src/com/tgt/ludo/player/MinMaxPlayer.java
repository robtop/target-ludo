package com.tgt.ludo.player;

import java.util.List;

import com.tgt.ludo.player.action.Move;
import com.tgt.ludo.rules.RuleEngine;
import com.tgt.ludo.ui.LudoScreen;

public class MinMaxPlayer extends ComputerPlayer {

	public MinMaxPlayer(LudoScreen screen, RuleEngine ruleEngine) {
		super(screen, ruleEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Move selectMove(List<Move> moves) {
      Move bestMove = null;
      float prevBestWt =0;
		for (Move move : moves) {
			float newWt = analyzeMove(move);
           if(newWt>prevBestWt){
        	   prevBestWt = newWt;
        	   bestMove = move;
           }
		}
		return bestMove;
	}

	public float analyzeMove(Move move) {

		float weight = 0;
		// give a 1 - 10 range for each scenario
		 //first three are mutually exclusive
		if (ruleEngine.goToJail(move)) {
			weight = 1;
		} else if (ruleEngine.goToHomeSquare(move)) {
			weight = 10;
		} if (ruleEngine.reachHome(move)) {
			weight = 8;
		} 
		
		if (ruleEngine.makeAkill(move)) {
			weight = 7;
		}    
		if (ruleEngine.jumpJail(move)) {
			weight = 6;
		}
		if (ruleEngine.escapeKill(move)) {
			weight = 5;
		}
		if (ruleEngine.closeToKill(move)) {
			weight = 5;
		} 
		
		
		return weight / 10;
	}
}
