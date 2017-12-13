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

	@Override
	protected void checkGameState() {
		killCheck();
	}

	private void killCheck() {

		Kill kill = ruleEngine.getKills();
		if (kill.getKilledPiece() != null && kill.getKilledPiece().size() > 0) {
			Piece killedPiece = kill.getKilledPiece().get(0);
			sendToRest(killedPiece);
			System.out.println("Another turn: "+player.getColor());
			getPlayer(kill.getKillerPiece().getColor()).setTurn(true);
		}
	}

	private void sendToRest(Piece piece){
		piece.setRest(true);
		piece.getSittingSuare().getPieces().remove(piece);
		// TODO change to animation
		getFreeRestSquare(piece.getColor()).getPieces().add(piece);
	}
	
	private void sendToHome(Piece piece){
		piece.getSittingSuare().getPieces().remove(piece);
		piece.setSittingSuare(homeMap.get(piece.getColor()));
		
	}
	
	private void jailCheck(){
		Piece jailedPiece = ruleEngine.getPieceOnJail();
		if(jailedPiece!=null){
			sendToRest(jailedPiece);
		}
	}
	
	private void homeCheck(){
		Piece homePiece = ruleEngine.getPieceOnHomeSquare();
		if(homePiece!=null){
			sendToRest(homePiece);
		}
	}
	
	private Square getFreeRestSquare(Board.COLOR color) {
		for (Square square : board.getRestSquaresMap().get(color)) {
			if (square.getPieces().isEmpty()) {
				return square;
			}
		}
		return null;
	}

	private Player getPlayer(Board.COLOR color){
		for (Player player : getPlayers()) {
			if(player.getColor().equals(color)){
				return player;
			}
		}
		return null;
	}
}
