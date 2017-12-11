package com.tgt.ludo.ui;

import java.util.Map;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Vector3;
import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Piece;
import com.tgt.ludo.board.Square;
import com.tgt.ludo.player.Move;

public class BoardRenderer extends StaticBoardRenderer {

	public BoardRenderer(Board board, RenderContext renderContext, PerspectiveCamera cam, Environment environment) {
		super(board, renderContext, cam, environment);

	}

	private boolean pieceMoved = true;
	private Move pieceMove;
	private int moveFinalIndex;
	private int moveTempIndex;
	private static final int MOVE_SPEED = 10;
	private ModelInstance pieceInstance;

	public void render(float delta) {
		super.render(delta);
		if (!pieceMoved) {
			renderContext.begin();
			modelBatch.begin(cam);

			renderMovingPiece(delta);

			modelBatch.end();
			renderContext.end();
		}

	}

	public void renderMovingPiece(float delta) {
		if (moveTempIndex == moveFinalIndex + 1) {
			Vector3 trans = new Vector3();
			squareInstMap.get(board.getSquares().get(moveFinalIndex)).transform.getTranslation(trans);
			// set the destination squares translation to the piece
			pieceInstance.transform.setTranslation(trans);
			pieceMoved = true;
		}

		Vector3 currentTranslation = new Vector3();
		Vector3 finalTranslation = new Vector3();
		pieceInstance = pieceInstMap.get(pieceMove.getPiece());
		pieceInstance.transform.getTranslation(currentTranslation);
		squareInstMap.get(board.getSquares().get(moveTempIndex)).transform.getTranslation(finalTranslation);

		Vector3 diff = finalTranslation.sub(currentTranslation);
		modelBatch.render(pieceInstance, environment);
		if (diff.len() < .1f) {
			moveTempIndex++;
		} else {

			pieceInstance.transform.translate(diff.scl(delta * MOVE_SPEED));
		}

	}

	public void setPieceMove(Move move) {
		pieceMove = move;
		pieceMoved = false;
		moveFinalIndex = move.getPiece().getSittingSuare().getIndex() + move.getSquares();
		moveTempIndex = move.getPiece().getSittingSuare().getIndex() + 1;
	}

	public boolean isPieceMoved() {
		return pieceMoved;
	}

	public Map<Piece, ModelInstance> getPieceInstMap() {
		return pieceInstMap;
	}

}
