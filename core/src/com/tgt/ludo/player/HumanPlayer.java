package com.tgt.ludo.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class HumanPlayer extends Player{
	
	//human player needs eyes to capture inputs from the screen
	OrthographicCamera guiCam;
	protected Vector3 touchPoint;
	
	public HumanPlayer(OrthographicCamera guiCam) {
		this.guiCam = guiCam;
		touchPoint = new Vector3();
	}
	@Override
	public void play() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		}
	}

}
