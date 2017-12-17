package com.tgt.ludo.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Vector3;
import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Dice;
import com.tgt.ludo.board.Piece;
import com.tgt.ludo.board.Square;
import com.tgt.ludo.player.Player;
import com.tgt.ludo.player.action.Move;
import com.tgt.ludo.util.LudoUtil;

public class BoardRenderer extends StaticBoardRenderer {

	protected boolean animationComplete = true;
	protected Move pieceMove;
	protected int moveFinalIndex;
	private int moveTempIndex;
	private int moveCount;

	// flags for animation
	protected boolean restMovedToStart;
	private boolean movedToRest;
	private boolean homeSqMovedToHome;

	private Square moveToRestSq;
	private boolean killMovedToRest;

	protected static final int MOVE_SPEED = 10;
	protected ModelInstance pieceInstance;
	private List<Dice> diceList;
	protected Model diceModel;
	protected Player selectedPlayer;

	public BoardRenderer(Board board, RenderContext renderContext, PerspectiveCamera cam, Environment environment) {
		super(board, renderContext, cam, environment);
		assetsManager.load("meshes/dice.g3db", Model.class);
		assetsManager.finishLoading();
		diceModel = (Model) assetsManager.get("meshes/dice.g3db");
		diceList = new ArrayList<Dice>();
		Dice newDice = createDiceInstance();
		newDice.setShake(true);
		diceList.add(newDice);
	}

	public void render(float delta) {
		super.render(delta);

		renderContext.begin();
		modelBatch.begin(cam);
		if (!animationComplete) {
			if (pieceMove.getPiece().isRest()) {
				renderMovingRestPiece(delta);
			} else if (pieceMove.getPiece().isJailed() || pieceMove.getPiece().isKilled()) {
				renderMovingToRestPiece(delta);
			} else {
				renderMovingPiece(delta);
			}
		}

		renderDice(delta);
		modelBatch.end();
		renderContext.end();
	}

	public void renderMovingPiece(float delta) {

		if (moveCount == pieceMove.getSquares() - 1) {
			Vector3 trans = new Vector3();
			squareInstMap.get(board.getSquares().get(moveFinalIndex)).transform.getTranslation(trans);
			// set the destination squares translation to the piece
			pieceInstance.transform.setTranslation(trans);
			Square finalSquare = board.getSquares().get(LudoUtil.calulateDestIndex(pieceMove));
			finalSquare.getPieces().add(pieceMove.getPiece());
			pieceMove.getPiece().getSittingSuare().getPieces().remove(pieceMove.getPiece());
			pieceMove.getPiece().setSittingSuare(finalSquare);
			animationComplete = true;
			return;
		}
		// check if it reached its home square or home etc

		Vector3 currentTranslation = new Vector3();
		Vector3 finalTranslation = new Vector3();
		pieceInstance = pieceInstMap.get(pieceMove.getPiece());
		pieceInstance.transform.getTranslation(currentTranslation);

		if (pieceMove.getPiece().isHomeSq()) {
			squareInstMap
					.get(board.getHomeSquaresMap().get(pieceMove.getPiece().getColor()).get(moveTempIndex)).transform
							.getTranslation(finalTranslation);
		} else {
			squareInstMap.get(board.getSquares().get(moveTempIndex)).transform.getTranslation(finalTranslation);
		}

		Vector3 diff = finalTranslation.sub(currentTranslation);
		modelBatch.render(pieceInstance, environment);
		if (diff.len() < .1f) {
			moveTempIndex = LudoUtil.calulateNextIndex(pieceMove, moveCount);
			moveCount++;
		} else {

			pieceInstance.transform.translate(diff.scl(delta * MOVE_SPEED));
		}

	}

	public void renderMovingRestPiece(float delta) {
		if (restMovedToStart) {
			Vector3 trans = new Vector3();
			squareInstMap.get(board.getSquares().get(moveFinalIndex)).transform.getTranslation(trans);
			// set the destination squares translation to the piece
			pieceInstance.transform.setTranslation(trans);
			Square finalSquare = board.getSquares().get(LudoUtil.getStartIndex(pieceMove.getPiece().getColor()));
			pieceMove.getPiece().setSittingSuare(finalSquare);
			finalSquare.getPieces().add(pieceMove.getPiece());

			pieceMove.getPiece().setRest(false);
			animationComplete = true;
			restMovedToStart = false;
			pieceMove = null;
			return;
		}

		Vector3 currentTranslation = new Vector3();
		Vector3 finalTranslation = new Vector3();
		pieceInstance = pieceInstMap.get(pieceMove.getPiece());
		pieceInstance.transform.getTranslation(currentTranslation);
		squareInstMap.get(board.getSquares().get(moveFinalIndex)).transform.getTranslation(finalTranslation);

		Vector3 diff = finalTranslation.sub(currentTranslation);
		modelBatch.render(pieceInstance, environment);
		if (diff.len() < .1f) {
			restMovedToStart = true;
		} else {

			pieceInstance.transform.translate(diff.scl(delta * MOVE_SPEED));
		}

	}

	public void renderMovingToRestPiece(float delta) {
		if (movedToRest) {
			Vector3 trans = new Vector3();
			squareInstMap.get(moveToRestSq).transform.getTranslation(trans);
			pieceMove.getPiece().getSittingSuare().getPieces().remove(pieceMove.getPiece());
			pieceMove.getPiece().reset();
			pieceMove.getPiece().setSittingSuare(moveToRestSq);
			moveToRestSq.getPieces().add(pieceMove.getPiece());
			pieceMove.getPiece().setRest(true);
			pieceMove.setPiece(null);
			// set the destination squares translation to the piece
			pieceInstance.transform.setTranslation(trans);
			
			pieceMove = null;
			animationComplete = true;
			movedToRest = false;
			return;
		}

		Vector3 currentTranslation = new Vector3();
		Vector3 finalTranslation = new Vector3();
		pieceInstance = pieceInstMap.get(pieceMove.getPiece());
		pieceInstance.transform.getTranslation(currentTranslation);
		squareInstMap.get(moveToRestSq).transform.getTranslation(finalTranslation);

		Vector3 diff = finalTranslation.sub(currentTranslation);
		modelBatch.render(pieceInstance, environment);
		if (diff.len() < .1f) {
			movedToRest = true;
		} else {

			pieceInstance.transform.translate(diff.scl(delta * MOVE_SPEED));
		}

	}

	public void renderDice(float delta) {
		int count = 0;
		for (Dice dice : diceList) {

			Vector3 translation = new Vector3();
			ModelInstance inst = dice.getDiceInstance();
			inst.transform.getTranslation(translation);

			dice.getDiceInstance().transform.setTranslation(
					(selectedPlayer.getLocX() * Board.DIMENSION * 2 * SQUARE_LENGTH * 1.4f), translation.y,
					(selectedPlayer.getLocY() * Board.DIMENSION * SQUARE_LENGTH * 2 + count * SQUARE_LENGTH * 1.5f)
							- (Board.DIMENSION * SQUARE_LENGTH));
			modelBatch.render(dice.getDiceInstance(), environment);
			count++;

			if (dice.isShake()) {
				inst.transform.getTranslation(translation);
				if (translation.y < 1) {
					inst.transform.translate(0, delta, 0);
				} else {
					translation.y = 0;
					inst.transform.setTranslation(translation);
				}
			}
		}

	}

	@Override
	protected void renderPiece(Piece pc, int index, float delta) {
		super.renderPiece(pc, index, delta);

		if (pc.isShake()) {
			ModelInstance inst = pieceInstMap.get(pc);
			Vector3 translation = new Vector3();
			inst.transform.getTranslation(translation);
			// if more then one piece, give some space
			translation.z = translation.z + index;
			if (translation.y < 1) {
				inst.transform.translate(0, delta, 0);
			} else {
				translation.y = 0;
				inst.transform.setTranslation(translation);
			}
		}

	}

	/**
	 * 
	 * @param player
	 * @param move
	 */
	public void setPieceMovingInTrack(Player player, Move move) {
		moveCount = 0;
		pieceMove = move;
		animationComplete = false;
		if (move == null || move.getPiece() == null) {
			return;
		}
		if (move.getPiece().isRest()) {
			moveFinalIndex = player.getStartIndex();
		} else if (move.getPiece().isKilled()) {
			// moveFinalIndex =
			// getFreeSquare(board.getHomeSquaresMap().get(player.getColor());
		} else {
			moveFinalIndex = 0;
			moveFinalIndex = LudoUtil.calulateDestIndex(move);
			moveTempIndex = move.getPiece().getSittingSuare().getIndex();
			moveCount = 0;
			moveTempIndex = LudoUtil.calulateNextIndex(move, moveCount);
		}
	}

	public void setPieceMovingOutSideTrack(Move move) {
		pieceMove = move;
		animationComplete = false;

		if (move.getPiece().isKilled() || move.getPiece().isJailed()) {
			moveToRestSq = LudoUtil.getFreeRestSquare(move.getPiece().getColor(), board);
		} else {

		}
	}

	public Dice createDiceInstance() {

		ModelInstance instance = new ModelInstance(diceModel);
		Dice dice = new Dice();
		dice.setDiceInstance(instance);
		return dice;
	}

	public boolean isAnimationComplete() {
		return animationComplete;
	}

	public Map<Piece, ModelInstance> getPieceInstMap() {
		return pieceInstMap;
	}

	public List<Dice> getDiceList() {
		return diceList;
	}

	public void setDiceList(List<Dice> diceList) {
		this.diceList = diceList;
	}

	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	public void setSelectedPlayer(Player selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}

}
