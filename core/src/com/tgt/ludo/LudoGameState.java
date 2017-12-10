package com.tgt.ludo;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Board.COLOR;
import com.tgt.ludo.board.Square;
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
		this.screen = screen;
		players = new ArrayList<Player>();
		greenPlayer = new HumanPlayer(((LudoScreen) screen));
		greenPlayer.setTurn(true);
		greenPlayer.setPieces(board.getPiecesMap().get(COLOR.GREEN));
		players.add(greenPlayer);
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
					// do the actual move
					moving = true;
					((LudoScreen) screen).getBoardRenderer().setPieceMove(move);
					move.getPiece().getSittingSuare().getPieces().remove(move.getPiece());
					sittingSquareIndex = move.getPiece().getSittingSuare().getIndex();
					// check game state
					player.setTurn(false);
					if (i + 1 < players.size()) {
						players.get(i + 1).setTurn(true);
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
