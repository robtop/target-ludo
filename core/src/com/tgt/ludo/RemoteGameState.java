package com.tgt.ludo;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Dice;
import com.tgt.ludo.board.Board.COLOR;
import com.tgt.ludo.board.Square;
import com.tgt.ludo.player.HumanPlayer;
import com.tgt.ludo.player.Move;
import com.tgt.ludo.player.Player;
import com.tgt.ludo.ui.LudoScreen;

public class RemoteGameState extends LudoGameState{
	
	public RemoteGameState(Screen screen) {
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
