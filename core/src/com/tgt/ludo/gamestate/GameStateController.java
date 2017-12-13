package com.tgt.ludo.gamestate;

import java.util.List;

import com.tgt.ludo.board.Board;
import com.tgt.ludo.player.Player;

public interface GameStateController {

	Board getBoard();

	List<Player> getPlayers();

	void update();

}
