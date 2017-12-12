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
   
   public void dispose(){
	   diceInstance.model.dispose();
   }

public ModelInstance getDiceInstance() {
	
	//rotate to display in value in board
	if(!rolled){
		//funny position
		diceInstance.transform.rotate(new Vector3(),3);
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
   
   
}
