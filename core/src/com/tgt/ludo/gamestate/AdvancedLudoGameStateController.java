package com.tgt.ludo.gamestate;

import com.badlogic.gdx.Screen;
import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Piece;
import com.tgt.ludo.board.Square;
import com.tgt.ludo.player.Player;
import com.tgt.ludo.player.action.Kill;

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

		// check game state after play

	}

	private void killCheck(){

		Kill kill = ruleEngine.getKills();
		if (kill.getKilledPiece() != null && kill.getKilledPiece().size() > 0) {
			Piece killedPiece = kill.getKilledPiece().get(0);
			killedPiece.setRest(true);
			//TODO change to animation
			getFreeRestSquare(killedPiece.getColor()).getPieces().add(killedPiece);
		}
	}
	
	private Square getFreeRestSquare(Board.COLOR color){
		for(Square square:board.getRestSquaresMap().get(color)){
			if(square.getPieces().isEmpty()){
				return square;
			}
		}
		return null;
	}
}
