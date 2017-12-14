package com.tgt.ludo.gamestate;

import com.badlogic.gdx.Screen;
import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Piece;
import com.tgt.ludo.board.Square;
import com.tgt.ludo.player.Player;
import com.tgt.ludo.player.action.Kill;
import com.tgt.ludo.player.action.Move;
import com.tgt.ludo.ui.LudoScreen;

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
		jailCheck();
		homeCheck();
	}

	private void killCheck() {

		Kill kill = ruleEngine.getKills();
		if (kill.getKilledPiece() != null && kill.getKilledPiece().size() > 0) {
			Piece killedPiece = kill.getKilledPiece().get(0);
			killedPiece.setKilled(true);
			sendToRest(killedPiece);
			System.out.println("Another turn: "+player.getColor());
			getPlayer(kill.getKillerPiece().getColor()).setTurn(true);
		}
	}

	private void sendToRest(Piece piece){
		piece.getSittingSuare().getPieces().remove(piece);
		move = new Move(piece);
		movePieceOutsideTrack(move);
	}
	
	private void sendToHome(Piece piece){
		piece.getSittingSuare().getPieces().remove(piece);
		piece.setSittingSuare(board.getHomeMap().get(piece.getColor()));
		//give chance back to player
		getPlayer(piece.getColor()).setTurn(true);
	}
	
	private void jailCheck(){
		Piece jailedPiece = ruleEngine.getPieceOnJail();
		
		if(jailedPiece!=null){
			jailedPiece.setJailed(true);
			sendToRest(jailedPiece);
		}
	}
	
	private void homeCheck(){
		Piece homePiece = ruleEngine.getPieceOnHomeSquare();
		if(homePiece!=null){
			sendToHome(homePiece);
		}
		
	}
	
	

	private Player getPlayer(Board.COLOR color){
		for (Player player : getPlayers()) {
			if(player.getColor().equals(color)){
				return player;
			}
		}
		return null;
	}
	
	protected void movePieceOutsideTrack(Move move) {
		movingAnimation = true;
		((LudoScreen) screen).getBoardRenderer().setPieceMovingOutSideTrack( move);
		move.getPiece().getSittingSuare().getPieces().remove(move.getPiece());
		sittingSquareIndex = move.getPiece().getSittingSuare().getIndex();
		move.getPiece().setShake(false);
		shakeDice(false);
	}
}
