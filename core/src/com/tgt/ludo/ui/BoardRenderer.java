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
import com.tgt.ludo.player.Player;

public class BoardRenderer extends StaticBoardRenderer {

	private boolean pieceMoved = true;
	private Move pieceMove;
	private int moveFinalIndex;
	private int moveTempIndex;
	private int moveCount;
	private boolean restMovedToStart;
	private static final int MOVE_SPEED = 10;
	private ModelInstance pieceInstance;
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
		if (!pieceMoved) {
			if (pieceMove.getPiece().isRest()) {
				renderMovingRestPiece(delta);
			} else {
				renderMovingPiece(delta);
			}
		}
		renderDice(delta);
		modelBatch.end();
		renderContext.end();
	}

	public void renderMovingPiece(float delta) {
		
		if (moveCount == pieceMove.getSquares()-1) {
			Vector3 trans = new Vector3();
			squareInstMap.get(board.getSquares().get(moveFinalIndex)).transform.getTranslation(trans);
			// set the destination squares translation to the piece
			pieceInstance.transform.setTranslation(trans);
			pieceMoved = true;
			return;
		}
		//check if it reached its home square or home etc
		moveTempIndex = calulateNextIndex();
		
		Vector3 currentTranslation = new Vector3();
		Vector3 finalTranslation = new Vector3();
		pieceInstance = pieceInstMap.get(pieceMove.getPiece());
		pieceInstance.transform.getTranslation(currentTranslation);
		squareInstMap.get(board.getSquares().get(moveTempIndex)).transform.getTranslation(finalTranslation);

		Vector3 diff = finalTranslation.sub(currentTranslation);
		modelBatch.render(pieceInstance, environment);
		if (diff.len() < .1f) {
			moveTempIndex++;
			moveCount++;
		} else {

			pieceInstance.transform.translate(diff.scl(delta * MOVE_SPEED));
		}

	}

	private int  calulateNextIndex(){
		 Board.COLOR color = pieceMove.getPiece().getColor();
		 int startIndex = 0;
		 switch(color){
		 case GREEN:
			 startIndex = Board.startIndexes.get(0);
			 break;
		 case YELLOW:
			 startIndex = Board.startIndexes.get(1);
			 break;
		 case RED:
			 startIndex = Board.startIndexes.get(2);
			 break;
		 case BLUE:
			 startIndex = Board.startIndexes.get(3);
			 break;
		 }
		 
		 if(moveCount+pieceMove.getPiece().getSittingSuare().getIndex() <= board.TOTAL_NUM_SQUARES){
			 //move to home square
		 } else
		 if(moveTempIndex == board.TOTAL_NUM_SQUARES){
			 moveTempIndex = 0;
		 }
		 return moveTempIndex;
	}
	public void renderMovingRestPiece(float delta) {
		if (restMovedToStart) {
			Vector3 trans = new Vector3();
			squareInstMap.get(board.getSquares().get(moveFinalIndex)).transform.getTranslation(trans);
			// set the destination squares translation to the piece
			pieceInstance.transform.setTranslation(trans);
			pieceMoved = true;
			restMovedToStart = false;
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

	public void renderDice(float delta) {
		int count = 0;
		for (Dice dice : diceList) {

			// if (!dice.isRolled()) {
			// dice.getDiceInstance().transform.setToRotation(new Vector3(1, 1,
			// 1), 45);
			// } else {
			// // rotate according to number
			// dice.getDiceInstance().transform.setToRotation(new Vector3(0, 0,
			// 0), 0);
			// }
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
	protected void renderPiece(Piece pc,int index, float delta) {
		super.renderPiece(pc,index, delta);

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

	public void setPieceMovingInTrack(Player player, Move move) {
		moveCount=0;
		pieceMove = move;
		pieceMoved = false;
		if (move.getPiece().isRest()) {
			moveFinalIndex = player.getStartIndex();
		}else if (move.getPiece().isKilled()) {
		//	moveFinalIndex = getFreeSquare(board.getHomeSquaresMap().get(player.getColor());
		}  else {
			moveFinalIndex = move.getPiece().getSittingSuare().getIndex() + move.getSquares();
			moveTempIndex = move.getPiece().getSittingSuare().getIndex() + 1;
		}
	}
	
	public void setPieceMovingOutSideTrack(Player player, Move move) {
		moveCount=0;
		pieceMove = move;
		pieceMoved = false;
		if (move.getPiece().isRest()) {
			moveFinalIndex = player.getStartIndex();
		}else if (move.getPiece().isKilled()) {
		//	moveFinalIndex = getFreeSquare(board.getHomeSquaresMap().get(player.getColor());
		}  else {
			moveFinalIndex = move.getPiece().getSittingSuare().getIndex() + move.getSquares()-1;
			moveTempIndex = move.getPiece().getSittingSuare().getIndex() + 1;
		}
	}

//	private Square getFreeSquare(List<Square> square){
//		
//	}
	
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

	public Player getSelectedPlayer() {
		return selectedPlayer;
	}

	public void setSelectedPlayer(Player selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}

}
