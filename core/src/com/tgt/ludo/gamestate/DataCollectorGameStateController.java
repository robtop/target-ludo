package com.tgt.ludo.gamestate;

import com.badlogic.gdx.Screen;

/**
 * Used to support multiple game runs to collect stats 
 * @author robin
 *
 */
public class DataCollectorGameStateController extends LudoGameStateController{
	
	public DataCollectorGameStateController(Screen screen) {
		super(screen);
		// TODO Auto-generated constructor stub
	}

	public void update() {
		super.update();
		updateServer();
	}

	private void updateServer() {

	}

	
}
