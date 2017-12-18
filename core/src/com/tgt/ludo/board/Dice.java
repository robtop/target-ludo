package com.tgt.ludo.board;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

/**
 * Use this only by the Human player - the other types of playes can just
 * use an Integer since they dont need to see the actual dice mesh (modelInstance) 
 * @author z062260
 *
 */
public class Dice {
   private ModelInstance diceInstance;
   int diceValue = 1;
   //is this rolled or waiting to be rolled by the player
   boolean rolled=false;
   boolean shake = false;
  
public ModelInstance getDiceInstance() {
	
	//rotate to display in value in board
	if(rolled && !shake){
		//funny position
		//diceInstance.transform.rotate(new Vector3(1,1,1),90);
		//diceInstance.transform.setToRotation(new Vector3(1,1,1),90);
	}
	return diceInstance;
}

public void setDiceInstance(ModelInstance diceInstance) {
	this.diceInstance = diceInstance;
}

public int getDiceValue() {
	return diceValue;
}

public boolean isRolled() {
	return rolled;
}

public void setDiceValue(int diceValue) {
	this.diceValue = diceValue;
}

public void setRolled(boolean rolled) {
	this.rolled = rolled;
}

public boolean isShake() {
	return shake;
}

public void setShake(boolean shake) {
	this.shake = shake;
}
   @Override
	protected void finalize() throws Throwable {
		super.finalize();
		diceInstance.model.dispose();
	}
   
}
