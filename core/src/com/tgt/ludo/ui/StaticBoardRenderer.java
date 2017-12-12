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

public class StaticBoardRenderer {

	protected RenderContext renderContext;
	protected ModelBatch modelBatch;
	protected PerspectiveCamera cam;
	protected Board board;
	public static AssetManager assetsManager = new AssetManager();
	public static final int SQUARE_LENGTH = 3;
	protected Model squareModel;
	protected Model greenPawnModel;
	protected Model yellowPawnModel;
	protected Model redPawnModel;
	protected Model bluePawnModel;
	protected ModelInstance instance;
	public Environment environment;
	// keep ModelInstance here instead of in the Square to keep the backend
	// independent of the UI
	protected Map<Square, ModelInstance> squareInstMap;
	protected Map<Piece, ModelInstance> pieceInstMap;

	public StaticBoardRenderer(Board board, RenderContext renderContext, PerspectiveCamera cam, Environment environment) {
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

	protected void renderHomeSquares() {
		for (Square sq : board.getHomeSquaresMap().get(COLOR.GREEN)) {
			renderSquare(sq);
		}
	}

	protected void renderRestSquares() {
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
	
	public void render(float delta) {
		  
		renderContext.begin();
		modelBatch.begin(cam);
		renderOuterTrack();
		renderHomeSquares();
		renderRestSquares();
		modelBatch.end();
		renderContext.end();

	}
	
	private void renderOuterTrack() {
		for (Square sq : board.getSquares()) {
			renderSquare(sq);
		}
	}
	
	protected void renderSquare(Square sq) {
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

	/**
	 * Create the 3D models of the individual squares and translate them to
	 * their positions
	 */
	protected void createOuterTrack() {
		int xTranslation = 0;
		int yTranslation = 0;
		int xControl = 1;
		int yControl = 0;

		for (Square sq : board.getSquares()) {
			// pick different model based on square type or use shader to color
			instance = new ModelInstance(squareModel);

			if (sq.getIndex() == Board.DIMENSION) {
				xTranslation += xControl * SQUARE_LENGTH;
				xControl = 0;
				yControl = -1;
			}
			// left wing end
			if (sq.getIndex() == Board.DIMENSION * 2) {
				xControl = 1;
				yControl = 0;
			}
			// left wing top
			if (sq.getIndex() == Board.DIMENSION * 2 + 2) {
				xControl = 0;
				yControl = 1;
			}
			// top wing start
			if (sq.getIndex() == Board.DIMENSION * 3 + 1) {
				yTranslation += yControl * SQUARE_LENGTH;
				xControl = 1;
				yControl = 0;
			}
			// top wing end
			if (sq.getIndex() == Board.DIMENSION * 4 + 1) {
				xControl = 0;
				yControl = 1;
			}

			if (sq.getIndex() == Board.DIMENSION * 4 + 1 + 2) {
				xControl = -1;
				yControl = 0;
			}
			// right wing start
			if (sq.getIndex() == Board.DIMENSION * 5 + 1 + 1) {
				xTranslation += xControl * SQUARE_LENGTH;
				xControl = 0;
				yControl = 1;
			}
			// right wing top
			if (sq.getIndex() == Board.DIMENSION * 6 + 2) {
				xControl = -1;
				yControl = 0;
			}
			if (sq.getIndex() == Board.DIMENSION * 6 + 2 + 2) {
				xControl = 0;
				yControl = -1;
			}
			if (sq.getIndex() == Board.DIMENSION * 7 + 3) {
				yTranslation += yControl * SQUARE_LENGTH;
				xControl = -1;
				yControl = 0;
			}
			if (sq.getIndex() == Board.DIMENSION * 8 + 3) {
				xControl = 0;
				yControl = -1;
			}

			xTranslation += xControl * SQUARE_LENGTH;
			yTranslation += yControl * SQUARE_LENGTH;
			instance.transform.translate(new Vector3(xTranslation, 0, yTranslation));
			squareInstMap.put(sq, instance);

		}
	}

	protected ModelInstance createPieceInstance(Piece piece, COLOR color) {

		switch (color) {
		case GREEN:
			instance = new ModelInstance(greenPawnModel);
			break;

		case YELLOW:
			instance = new ModelInstance(yellowPawnModel);
			break;

		case RED:
			instance = new ModelInstance(redPawnModel);
			break;
		case BLUE:
			instance = new ModelInstance(bluePawnModel);
			break;
		}

		pieceInstMap.put(piece, instance);
		return instance;
	}

	protected void createHomeSquares() {

		createHomeSquares(COLOR.GREEN, new Vector3(2 * SQUARE_LENGTH, 1, 1 * SQUARE_LENGTH), 1, 0);
		createHomeSquares(COLOR.YELLOW, new Vector3(0, 0, 1 * SQUARE_LENGTH), 1, 0);
	}

	private void createHomeSquares(COLOR color, Vector3 translation, int xControl, int yControl) {

		for (Square sq : board.getHomeSquaresMap().get(color)) {
			instance = new ModelInstance(squareModel);
			squareInstMap.put(sq, instance);
			instance.transform.translate(translation);
			translation.x += xControl * SQUARE_LENGTH;
			translation.z += yControl * SQUARE_LENGTH;
		}

	}

	protected void createRestSquares() {

		createRestSquares(COLOR.GREEN, new Vector3(2 * SQUARE_LENGTH, 0, -5 * SQUARE_LENGTH));
		createRestSquares(COLOR.YELLOW, new Vector3(2 * Board.DIMENSION * SQUARE_LENGTH, 0, -5 * SQUARE_LENGTH));
		createRestSquares(COLOR.RED, new Vector3(2 * Board.DIMENSION * SQUARE_LENGTH, 0, 5 * SQUARE_LENGTH));
		createRestSquares(COLOR.BLUE, new Vector3(2 * SQUARE_LENGTH, 0, 5 * SQUARE_LENGTH));
	}

	private void createRestSquares(COLOR color, Vector3 translation) {
		int xControl = 1;
		int yControl = 0;

		for (Square sq : board.getRestSquaresMap().get(color)) {
			instance = new ModelInstance(squareModel);
			squareInstMap.put(sq, instance);
			instance.transform.translate(translation);

			int temp = xControl;
			xControl = yControl;
			yControl = temp;
			if (sq.getPieces() != null && !sq.getPieces().isEmpty()) {
				ModelInstance pieceInstance = createPieceInstance(sq.getPieces().get(0), color);
				Vector3 translationTemp = translation.cpy();
				translationTemp.y = translationTemp.y + 1;
				pieceInstance.transform.translate(translationTemp);
			}

			translation.x += xControl * SQUARE_LENGTH * 2;
			translation.z += yControl * SQUARE_LENGTH * 2;
		}

	}

	public void dispose() {
		for (Square sq : squareInstMap.keySet()) {
			squareInstMap.get(sq).model.dispose();
		}
		for (Piece pc : pieceInstMap.keySet()) {
			squareInstMap.get(pc).model.dispose();
		}
		assetsManager.clear();
		assetsManager.dispose();
	}
}