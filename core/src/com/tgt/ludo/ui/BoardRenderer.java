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
import com.tgt.ludo.board.Board.COLOR;
import com.tgt.ludo.player.Move;

public class BoardRenderer extends StaticBoardRenderer {

	private boolean pieceMoved = true;
	private Move pieceMove;
	private int moveFinalIndex;
	private int moveTempIndex;
	private static final int MOVE_SPEED = 10;
	private ModelInstance pieceInstance;
	private List<Dice> diceList;
	protected Model diceModel;

	public BoardRenderer(Board board, RenderContext renderContext, PerspectiveCamera cam, Environment environment) {
		super(board, renderContext, cam, environment);
		assetsManager.load("meshes/dice.g3db", Model.class);
		assetsManager.finishLoading();
		diceModel = (Model) assetsManager.get("meshes/dice.g3db");
		diceList = new ArrayList<Dice>();
		diceList.add(createDiceInstance());
	}

	public void render(float delta) {
		super.render(delta);
		
			renderContext.begin();
			modelBatch.begin(cam);
			if (!pieceMoved) {
			renderMovingPiece(delta);
			}
			renderDice();
			modelBatch.end();
			renderContext.end();
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

	public void renderDice() {
		int count=0;
		for (Dice dice : diceList) {
		
			if(!dice.isRolled()){
			 	dice.getDiceInstance().transform.setToRotation(new Vector3(1, 1,1),45);
			} else {
				// rotate according to number
				dice.getDiceInstance().transform.setToRotation(new Vector3(0, 0,0),0);
			}
			dice.getDiceInstance().transform.setTranslation(0, 0,count*SQUARE_LENGTH*1.5f);
			modelBatch.render(dice.getDiceInstance(), environment);
			count++;
		}
	}

	public void setPieceMove(Move move) {
		pieceMove = move;
		pieceMoved = false;
		moveFinalIndex = move.getPiece().getSittingSuare().getIndex() + move.getSquares();
		moveTempIndex = move.getPiece().getSittingSuare().getIndex() + 1;
	}

	public Dice createDiceInstance() {

		instance = new ModelInstance(diceModel);
		Dice dice = new Dice();
		dice.setDiceInstance(instance);
		return dice;
	}

	public boolean isPieceMoved() {
		return pieceMoved;
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

}
