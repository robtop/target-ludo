package com.tgt.ludo;

import com.badlogic.gdx.ApplicationAdapter;
import com.tgt.ludo.board.Board;
import com.tgt.ludo.ui.LudoScreen;

public class LudoGame extends ApplicationAdapter {
	
    private LudoScreen ludoScreen;
    private LudoGameState ludoGameState;
    
	@Override
	public void create() {
		ludoGameState = new LudoGameState();
		ludoScreen = new LudoScreen();
		ludoScreen.create(ludoGameState);
	}

	@Override
	public void render() {
		ludoScreen.render();
	}
	

	@Override
	public void dispose() {
		ludoScreen.dispose();
		super.dispose();
	}
}
