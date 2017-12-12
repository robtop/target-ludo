package com.tgt.ludo.gamestate;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Board.COLOR;
import com.tgt.ludo.board.Dice;
import com.tgt.ludo.board.Square;
import com.tgt.ludo.player.HumanPlayer;
import com.tgt.ludo.player.Move;
import com.tgt.ludo.player.Player;
import com.tgt.ludo.rules.BasicRuleEngine;
import com.tgt.ludo.rules.RuleEngine;
import com.tgt.ludo.ui.LudoScreen;

public class LudoGameState {
	private Board board;
	private Player greenPlayer;
	private Player yellowPlayer;
	private Player redPlayer;
	private Player bluePlayer;

	// needed by human players to get inputs
	private LudoScreen screen;
	private List<Player> players;
	RuleEngine ruleEngine = new BasicRuleEngine();

	public LudoGameState(Screen screen) {
		board = new Board();
		board.setup();
		this.screen = (LudoScreen) screen;
		players = new ArrayList<Player>();

		greenPlayer = new HumanPlayer(((LudoScreen) screen), ruleEngine);
		greenPlayer.setColor(COLOR.GREEN);
		greenPlayer.setTurn(true);
		greenPlayer.setPieces(board.getPiecesMap().get(greenPlayer.getColor()));
		players.add(greenPlayer);

		yellowPlayer = new HumanPlayer(((LudoScreen) screen), ruleEngine);
		yellowPlayer.setColor(COLOR.YELLOW);
		yellowPlayer.setTurn(false);
		yellowPlayer.setPieces(board.getPiecesMap().get(yellowPlayer.getColor()));
		players.add(yellowPlayer);

		redPlayer = new HumanPlayer(((LudoScreen) screen), ruleEngine);
		redPlayer.setColor(COLOR.RED);
		redPlayer.setTurn(false);
		redPlayer.setPieces(board.getPiecesMap().get(redPlayer.getColor()));
		players.add(redPlayer);

		bluePlayer = new HumanPlayer(((LudoScreen) screen), ruleEngine);
		bluePlayer.setColor(COLOR.BLUE);
		bluePlayer.setTurn(false);
		bluePlayer.setPieces(board.getPiecesMap().get(bluePlayer.getColor()));
		players.add(bluePlayer);
	}

	private boolean moving = false;
	Move move;
	int sittingSquareIndex = 0;

	public void update() {
		// wait for animation to complete
		if (moving) {
			if (((LudoScreen) screen).getBoardRenderer().isPieceMoved()) {
				moving = false;
				Square finalSquare = board.getSquares().get(sittingSquareIndex + move.getSquares());
				finalSquare.getPieces().add(move.getPiece());
				move.getPiece().setSittingSuare(finalSquare);
				return;
			}

			return;
		}

		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);

			if (player.isTurn()) {
				move = player.play();

				if (move != null) {

					if (!move.isIncomplete()) {
						player.setTurn(false);
						giveTurnToNext(i);

						if (move.isSkipTurn()) {

							return;
						}
					}
					// do the actual move
					moving = true;
					((LudoScreen) screen).getBoardRenderer().setPieceMove(move);
					move.getPiece().getSittingSuare().getPieces().remove(move.getPiece());
					sittingSquareIndex = move.getPiece().getSittingSuare().getIndex();
					move.getPiece().setShake(false);
					
					// check game state for win etc
				}
				break;
			}
		}

	}

	private void giveTurnToNext(int i) {
		Player selectedPlayer;
		if (i + 1 < players.size()) {
			selectedPlayer = players.get(i + 1);

		} else {
			selectedPlayer = players.get(0);
		}
		selectedPlayer.setTurn(true);
		List<Dice> diceList = ((LudoScreen) screen).getBoardRenderer().getDiceList();
		diceList.clear();
		Dice newDice = ((LudoScreen) screen).getBoardRenderer().createDiceInstance();
		newDice.setShake(true);
		shakeDice(false);
		diceList.add(newDice);
		((LudoScreen) screen).getBoardRenderer().setSelectedPlayer(selectedPlayer);
	}

	private void shakeDice(boolean shake){
		List<Dice> diceList = screen.getBoardRenderer().getDiceList();
		 for(Dice dice:diceList){
			 dice.setShake(shake);
         }
	}
	
	public Board getBoard() {
		return board;
	}

	public Player getGreenPlayer() {
		return greenPlayer;
	}

}
