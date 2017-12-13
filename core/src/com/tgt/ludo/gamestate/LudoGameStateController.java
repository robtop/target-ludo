package com.tgt.ludo.gamestate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.Screen;
import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Board.COLOR;
import com.tgt.ludo.board.Dice;
import com.tgt.ludo.board.Piece;
import com.tgt.ludo.board.Square;
import com.tgt.ludo.player.ComputerPlayer;
import com.tgt.ludo.player.HumanPlayer;
import com.tgt.ludo.player.Move;
import com.tgt.ludo.player.Player;
import com.tgt.ludo.rules.BasicRuleEngine;
import com.tgt.ludo.rules.RuleEngine;
import com.tgt.ludo.ui.LudoScreen;

/***
 * Main class controlling a single game session
 * 
 * @author robin
 *
 */
public class LudoGameStateController {
	private Board board;
	private Player greenPlayer;
	private Player yellowPlayer;
	private Player redPlayer;
	private Player bluePlayer;

	// needed by human players to get inputs
	private LudoScreen screen;
	private List<Player> players;

	private UUID gameID;
	private GAME_STATE gameState;
	private Player winner;
	private RuleEngine ruleEngine;
	private Player player;

	public static enum GAME_STATE {
		WAITING, PROGRESS, COMPLETE
	}

	public LudoGameStateController(Screen screen) {

		gameID = UUID.randomUUID();
		gameState = GAME_STATE.WAITING;
		board = new Board();
		board.setup();
		ruleEngine = new BasicRuleEngine(board);
		this.screen = (LudoScreen) screen;
		// createPlayers();
		createRobotPlayers();
		gameState = GAME_STATE.PROGRESS;
	}

	private boolean movingAnimation = false;
	Move move;
	int sittingSquareIndex = 0;

	/***
	 * Main game loop to update the backend and allow play
	 */
	public void update() {
		// wait for piece motion animation to complete
		if (movingAnimation) {
			animationCheck();
			return;
		}

		// check for any events after animation/piece moves
		checkGameState();

		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);

			if (player.isTurn()) {
				this.player = player;
				play(player, i);
				break;
			}
		}
	}

	private void checkGameState() {
		for (Piece kill : ruleEngine.getKills()) {
			movingAnimation = true;
		}
	}

	private void play(Player player, int playerIndex) {
		move = player.play();
		if (move != null) {

			if (move.isSkipTurn()) {
				giveTurnToNext(player, playerIndex);
				return;
			}
			if (move.isIncomplete()) {
				player.setSelectDice(false);
				player.setDiceRolled(true);
			} else {
				giveTurnToNext(player, playerIndex);
				setPiecesShake(player, false);

			}

			// do the actual move in the board backend
			movePiece(player);

			// check game state for win etc after the animation completes
		}
	}

	private void setPiecesShake(Player player, boolean shake) {
		for (Piece piece : player.getPieces()) {
			piece.setShake(shake);
		}
	}

	private void movePiece(Player player) {
		movingAnimation = true;
		((LudoScreen) screen).getBoardRenderer().setPieceMovingInTrack(player, move);
		move.getPiece().getSittingSuare().getPieces().remove(move.getPiece());
		sittingSquareIndex = move.getPiece().getSittingSuare().getIndex();
		move.getPiece().setShake(false);
		shakeDice(false);
	}

	private void animationCheck() {
		if (((LudoScreen) screen).getBoardRenderer().isPieceMoved()) {
			movingAnimation = false;
			Square finalSquare = null;
			if (move.getPiece().isRest()) {
				finalSquare = board.getSquares().get(player.getStartIndex() + move.getSquares());
				move.getPiece().setRest(false);
			}
			// else if (move.getPiece().getSittingSuare().isSpecialHome()) {
			// finalSquare = board.getSquares().get(sittingSquareIndex +
			// move.getSquares());
			// } else if (move.getPiece().getSittingSuare().isJail()) {
			// finalSquare = board.getSquares().get(sittingSquareIndex +
			// move.getSquares());
			// } else if (move.getPiece().isKilled()) {
			// finalSquare = board.getSquares().get(sittingSquareIndex +
			// move.getSquares());
			// } 
			else
			{
				finalSquare = board.getSquares().get(LudoUtil.calulateDestIndex(move));
			}
			if (finalSquare == null) {
				System.out.println("null?");
			}
			finalSquare.getPieces().add(move.getPiece());
			move.getPiece().setSittingSuare(finalSquare);
			return;
		}

	}



	private void giveTurnToNext(Player currentPlayer, int i) {
		currentPlayer.setTurn(false);
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

		diceList.add(newDice);
		((LudoScreen) screen).getBoardRenderer().setSelectedPlayer(selectedPlayer);
	}

	private void createPlayers() {
		players = new ArrayList<Player>();

		greenPlayer = new HumanPlayer(((LudoScreen) screen), ruleEngine);
		greenPlayer.setColor(COLOR.GREEN);
		greenPlayer.setTurn(true);
		greenPlayer.setPieces(board.getPiecesMap().get(greenPlayer.getColor()));
		players.add(greenPlayer);

		yellowPlayer = new HumanPlayer(((LudoScreen) screen), ruleEngine);
		yellowPlayer.setColor(COLOR.YELLOW);
		yellowPlayer.setTurn(false);
		yellowPlayer.setStartIndex(Board.DIMENSION * 2 + 1);
		yellowPlayer.setPieces(board.getPiecesMap().get(yellowPlayer.getColor()));
		players.add(yellowPlayer);

		redPlayer = new HumanPlayer(((LudoScreen) screen), ruleEngine);
		redPlayer.setColor(COLOR.RED);
		redPlayer.setTurn(false);
		redPlayer.setStartIndex(Board.DIMENSION * 4 + 2);
		redPlayer.setPieces(board.getPiecesMap().get(redPlayer.getColor()));
		players.add(redPlayer);

		bluePlayer = new HumanPlayer(((LudoScreen) screen), ruleEngine);
		bluePlayer.setColor(COLOR.BLUE);
		bluePlayer.setTurn(false);
		bluePlayer.setStartIndex(Board.DIMENSION * 6 + 3);
		bluePlayer.setPieces(board.getPiecesMap().get(bluePlayer.getColor()));
		players.add(bluePlayer);
	}

	private void createRobotPlayers() {
		players = new ArrayList<Player>();

//		greenPlayer = new ComputerPlayer(((LudoScreen) screen), ruleEngine);
//		greenPlayer.setColor(COLOR.GREEN);
//		greenPlayer.setPieces(board.getPiecesMap().get(greenPlayer.getColor()));
//		players.add(greenPlayer);

//		yellowPlayer = new ComputerPlayer(((LudoScreen) screen), ruleEngine);
//		yellowPlayer.setColor(COLOR.YELLOW);
//		yellowPlayer.setStartIndex(Board.DIMENSION * 2 + 1);
//		yellowPlayer.setPieces(board.getPiecesMap().get(yellowPlayer.getColor()));
//		players.add(yellowPlayer);
//
//		redPlayer = new ComputerPlayer(((LudoScreen) screen), ruleEngine);
//		redPlayer.setColor(COLOR.RED);
//		redPlayer.setStartIndex(Board.DIMENSION * 4 + 2);
//		redPlayer.setPieces(board.getPiecesMap().get(redPlayer.getColor()));
//		players.add(redPlayer);
//
		bluePlayer = new ComputerPlayer(((LudoScreen) screen), ruleEngine);
		bluePlayer.setColor(COLOR.BLUE);
		bluePlayer.setStartIndex(Board.DIMENSION * 6 + 3);
		bluePlayer.setPieces(board.getPiecesMap().get(bluePlayer.getColor()));
		players.add(bluePlayer);
	}

	private void shakeDice(boolean shake) {
		List<Dice> diceList = screen.getBoardRenderer().getDiceList();
		for (Dice dice : diceList) {
			dice.setShake(shake);
		}
	}

	public Board getBoard() {
		return board;
	}

	public Player getGreenPlayer() {
		return greenPlayer;
	}

	public GAME_STATE getGameState() {
		return gameState;
	}

	public List<Player> getPlayers() {
		return players;
	}

}
