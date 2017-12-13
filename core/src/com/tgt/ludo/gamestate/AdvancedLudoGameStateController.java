package com.tgt.ludo.gamestate;

import com.badlogic.gdx.Screen;
import com.tgt.ludo.player.Player;

/***
 * Main class controlling a single game session
 * 
 * @author robin
 *
 */
public class AdvancedLudoGameStateController extends LudoGameStateController {

	public static enum GAME_STATE {
		WAITING, PROGRESS, COMPLETE
	}

	public AdvancedLudoGameStateController(Screen screen) {
		super(screen);
	}

	protected void play(Player player, int playerIndex) {
		super.play(player, playerIndex);

	}

}
