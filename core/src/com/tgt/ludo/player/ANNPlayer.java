package com.tgt.ludo.player;

import java.util.List;

import com.tgt.ludo.player.action.Move;
import com.tgt.ludo.rules.RuleEngine;
import com.tgt.ludo.ui.LudoScreen;

public class ANNPlayer extends ComputerPlayer{
   	
	public ANNPlayer(LudoScreen screen,RuleEngine ruleEngine) {
		super(screen, ruleEngine);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Move selectMove(List<Move> moves) {
	  
			for (Move move : moves) {
				int[] arr = createInput(move);
				 System.out.println(arr[0] +","+arr[1] +","+arr[2] +","+arr[3] +","+arr[4] +","+arr[5] +","+arr[6]);
	           }
			
			return super.selectMove(moves);
		}

	
	public int[] createInput(Move move) {

	   int[] input = new int[7];
		
	   //first three are mutually exclusive
		if (ruleEngine.goToJail(move)) {
			input[0]=1;
		} else if (ruleEngine.goToHomeSquare(move)) {
			input[0]=1;
		} if (ruleEngine.reachHome(move)) {
			input[0]=1;
		} 
		
		if (ruleEngine.makeAkill(move)) {
			input[0]=1;
		}    
		if (ruleEngine.jumpJail(move)) {
			input[0]=1;
		}
		if (ruleEngine.escapeKill(move)) {
			input[0]=1;
		}
		if (ruleEngine.closeToKill(move)) {
			input[0]=1;
		} 
	
		return input;
	}
}
