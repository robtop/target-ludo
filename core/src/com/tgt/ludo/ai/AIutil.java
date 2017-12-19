package com.tgt.ludo.ai;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import com.tgt.ludo.player.action.Move;
import com.tgt.ludo.rules.RuleEngine;

import weka.classifiers.functions.MultilayerPerceptron;

public class AIutil {

	/**
	 * Basic logic used by a min max type of player
	 * 
	 * @param ruleEngine
	 * @param move
	 * @return
	 */
	public static float analyzeMove(RuleEngine ruleEngine,Move move) {

		float weight = 5;
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
	
	public static double[] createNNinput(RuleEngine ruleEngine,Move move) {

		   double[] input = new double[7];
			
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
	
	  public static MultilayerPerceptron readFromFile(String dumpLocation) {
	    	
	    	MultilayerPerceptron mlp = new MultilayerPerceptron();
	    	
	    	//binary network is saved to following file
	    	File file = new File(dumpLocation+"trained_network.txt");
	    	
	    	FileInputStream fileInputStream = null;

	    	//binary content will be stored in the binaryFile variable
	    	byte[] binaryFile = new byte[(int) file.length()];
	    	
	    	try{
	    		
	    		fileInputStream = new FileInputStream(file);
	    		fileInputStream.read(binaryFile);
	    		fileInputStream.close();
	    		
	    	}
	    	catch(Exception ex){
	    		System.out.println(ex);
	    	}
	    	
	    	try{
	    		
	        	mlp = (MultilayerPerceptron) deserialize(binaryFile);
	        	
	    	}
	    	catch(Exception ex){
	    		System.out.println(ex);
	    	}
	    	
	    	return mlp;
	    	
	    }
	  
	  public static Object deserialize(byte[] bytes) throws Exception {
	    	
	    	ByteArrayInputStream b = new ByteArrayInputStream(bytes);
	        
	        ObjectInputStream o = new ObjectInputStream(b);
	        
	        return o.readObject();

	    }

}
