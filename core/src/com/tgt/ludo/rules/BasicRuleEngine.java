package com.tgt.ludo.rules;

import java.util.ArrayList;
import java.util.List;

import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Piece;
import com.tgt.ludo.board.Square;
import com.tgt.ludo.player.Player;
import com.tgt.ludo.player.action.Kill;
import com.tgt.ludo.player.action.Move;

/**
 * Engine to enfocre rule of ludo.
 * 
 * @author robin
 *
 */
public class BasicRuleEngine implements RuleEngine {

	private Board board;

	@Override
	public boolean validMove(Piece piece, int diceVal) {

		if (piece.isRest()) {
			if (diceVal == 6)
				return true;
		} else {
			return true;
		}

		return false;
	}

	int prev = 0;

	public int getSingleDiceRoll() {
		// get range 1 to 6
		int value = (int) Math.floor((Math.random() * 6)) + 1;
		// if (prev != 6)
		// value = 6;
		prev = value;
		// System.out.println("Dice Roll: " + value);
		return value;

	}

	@Override
	public List<Move> getValidMoves(Player player, int diceVal) {
		List<Move> moves = new ArrayList<Move>();
		for (Piece piece : player.getPieces()) {
			if (piece.isRest() && diceVal == 6) {
				Move move = new Move(piece);
				move.setSquares(0);
				move.setStart(true);
				moves.add(move);
			}

			if (validMove(piece, diceVal)) {
				Move move = new Move(piece);
				move.setSquares(diceVal);
				moves.add(move);
			}
		}

		return moves;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public BasicRuleEngine(Board board) {
		this.board = board;
	}

	@Override
	public Kill getKills() {
		List<Piece> killePieces = new ArrayList<Piece>();
		Kill kill = new Kill();
		for (Square square : board.getSquares()) {
			if (square.getPieces().size() > 1) {
				// checking two is enough, otherwise it would be a block
				if (!square.getPieces().get(0).getColor().equals(square.getPieces().get(1).getColor())) {
					// older piece gets killed
					// if a variation the add more than ones
					killePieces.add(square.getPieces().get(0));
					kill.setKilledPiece(killePieces);
					kill.setKillerPiece(square.getPieces().get(1));
					System.out.println("Killed: " + killePieces.get(0).getColor());
				}
			}
		}

		return kill;
	}

	@Override
	public Piece getPieceOnHomeSquare() {
		Piece homePiece = board.getSquares().get(board.HOME_INDEX).getPieces().get(0);

		return homePiece;
	}

	@Override
	public Piece getPieceOnJail() {
		for (Integer i : board.jailIndexes) {
			if (!board.getSquares().get(i).getPieces().isEmpty()) {
				System.out.println("Jailed: ");
				return board.getSquares().get(i).getPieces().get(0);
			}
		}
		return null;
	}
}
