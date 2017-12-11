package com.tgt.ludo.ui;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Vector3;
import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Board.COLOR;
import com.tgt.ludo.board.Piece;
import com.tgt.ludo.board.Square;
import com.tgt.ludo.player.Move;

public class BoardRenderer extends StaticBoardRenderer{


	

	private boolean pieceMoved = true;
	private Move pieceMove;
	private int moveFinalIndex;
	private int moveTempIndex;
	private static final int MOVE_SPEED = 10;

	public BoardRenderer(Board board, RenderContext renderContext, PerspectiveCamera cam, Environment environment) {
		this.board = board;
		this.renderContext = renderContext;
		this.cam = cam;
		this.environment = environment;

		assetsManager.load("meshes/square.g3db", Model.class);
		//assetsManager.load("meshes/piece.g3db", Model.class);
		assetsManager.load("meshes/pawnGreen.g3dj", Model.class);
		assetsManager.load("meshes/pawnYellow.g3dj", Model.class);
		assetsManager.load("meshes/pawnRed.g3dj", Model.class);
		assetsManager.load("meshes/pawnBlue.g3dj", Model.class);
		assetsManager.finishLoading();
		squareModel = (Model) assetsManager.get("meshes/square.g3db");
		// pieceModel = (Model) assetsManager.get("meshes/piece.g3db");

		greenPawnModel = (Model) assetsManager.get("meshes/pawnGreen.g3dj");
		yellowPawnModel = (Model) assetsManager.get("meshes/pawnYellow.g3dj");
		redPawnModel = (Model) assetsManager.get("meshes/pawnRed.g3dj");
		bluePawnModel = (Model) assetsManager.get("meshes/pawnBlue.g3dj");

		squareInstMap = new HashMap<Square, ModelInstance>();
		pieceInstMap = new HashMap<Piece, ModelInstance>();
		createOuterTrack();
		createHomeSquares();
		createRestSquares();
		modelBatch = new ModelBatch();
	}

	public void renderSquares(float delta) {

		renderContext.begin();
		modelBatch.begin(cam);
		renderOuterTrack();
		renderHomeSquares();
		renderRestSquares();
		if (!pieceMoved) {
			renderMovingPiece(delta);
		}
		modelBatch.end();
		renderContext.end();

	}

	private void renderOuterTrack() {
		for (Square sq : board.getSquares()) {
			renderSquare(sq);
		}
	}

	private void renderSquare(Square sq) {
		modelBatch.render(squareInstMap.get(sq), environment);
		if (sq.getPieces() != null && !sq.getPieces().isEmpty()) {
			Vector3 translation = new Vector3();
			for (Piece pc : sq.getPieces()) {
				ModelInstance inst = pieceInstMap.get(pc);
				inst.transform.translate(translation);
				modelBatch.render(inst, environment);
				translation.z = translation.z + 1;
			}
		}
	}

	private void renderHomeSquares() {
		for (Square sq : board.getHomeSquaresMap().get(COLOR.GREEN)) {
			renderSquare(sq);
		}
	}

	private void renderRestSquares() {
		for (Square sq : board.getRestSquaresMap().get(COLOR.GREEN)) {
			renderSquare(sq);
		}
		for (Square sq : board.getRestSquaresMap().get(COLOR.YELLOW)) {
			renderSquare(sq);
		}
		for (Square sq : board.getRestSquaresMap().get(COLOR.RED)) {
			renderSquare(sq);
		}
		for (Square sq : board.getRestSquaresMap().get(COLOR.BLUE)) {
			renderSquare(sq);
		}
	}

	ModelInstance pieceInstance;

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
