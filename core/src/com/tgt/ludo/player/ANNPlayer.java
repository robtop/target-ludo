package com.tgt.ludo.player;

import java.util.List;

import com.tgt.ludo.ai.AIutil;
import com.tgt.ludo.player.action.Move;
import com.tgt.ludo.rules.RuleEngine;
import com.tgt.ludo.ui.LudoScreen;
import com.tgt.ludo.util.LudoUtil;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.DenseInstance;
import weka.core.Instances;

public class ANNPlayer extends ComputerPlayer {

	MultilayerPerceptron mlp = null;
	double[] instanceValue = null;
	Instances trainingset;

	public ANNPlayer(LudoScreen screen, RuleEngine ruleEngine) {
		super(screen, ruleEngine);
		mlp = AIutil.readFromFile("/Users/z062260/");
		trainingset = LudoUtil.retrieveHistoricalData("arff");
	}

	@Override
	protected Move selectMove(List<Move> moves) {
		Move bestMove = new Move(true);
		float prevBestWt = 0;
		for (Move move : moves) {
			//
			float newWt = 0;
			try {
				DenseInstance d = (DenseInstance) trainingset.instance(0);
				double[] input = AIutil.createNNinput(ruleEngine, move);
				for (int i = 0; i < 7; i++) {
					d.setValue(i, input[i]);
					System.out.print(input[i]+",");
				}
				// prediction
				newWt = (float) mlp.distributionForInstance(d)[0];
				// min max
				float val = AIutil.analyzeMove(ruleEngine, move);
				System.out.println(val);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (newWt > prevBestWt) {
				prevBestWt = newWt;
				bestMove = move;
			}
		}

		return bestMove;
	}

}
