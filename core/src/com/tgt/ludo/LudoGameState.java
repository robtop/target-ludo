package com.tgt.ludo;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Board.COLOR;
import com.tgt.ludo.player.HumanPlayer;
import com.tgt.ludo.player.Move;
import com.tgt.ludo.player.Player;
import com.tgt.ludo.ui.LudoScreen;

public class LudoGameState {
	private Board board;
	private Player greenPlayer;
	private Player yellowPlayer;
	private Player redPlayer;
	private Player bluePlayer;

	// needed by human players to get inputs
	private Screen screen;
	private List<Player> players;

	public LudoGameState(Screen screen) {
		board = new Board();
		board.setup();
		players = new ArrayList<Player>();
		greenPlayer = new HumanPlayer(((LudoScreen) screen));
		greenPlayer.setTurn(true);
		greenPlayer.setPieces(board.getPiecesMap().get(COLOR.GREEN));
		players.add(greenPlayer);
	}

	public void update() {
	
		for (int i=0;i<players.size();i++) {
			Player player = players.get(i);
			
			if (player.isTurn()) {
				Move move = player.play();
				if (move != null) {
					//do the actual move
					//check game state
					player.setTurn(false);
					if(i+1<players.size()){
						players.get(i+1).setTurn(true);
					} else {
						players.get(0).setTurn(true);	
					}
				} else {
					// ignore others
					break;
				}
			}
		}
	}

	public Board getBoard() {
		return board;
	}

}