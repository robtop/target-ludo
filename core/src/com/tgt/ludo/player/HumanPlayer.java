package com.tgt.ludo.player;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.tgt.ludo.RuleEngine;
import com.tgt.ludo.board.Dice;
import com.tgt.ludo.board.Piece;
import com.tgt.ludo.ui.BoardRenderer;
import com.tgt.ludo.ui.LudoScreen;

public class HumanPlayer extends Player {
	
	// human player needs eyes to capture inputs from the screen
	//OrthographicCamera guiCam;
	PerspectiveCamera cam3D;
	protected Vector3 touchPoint;
	Ray pickRay;
	// need screen details to capture inputs and get location of pieces
	LudoScreen screen;
  	
	public HumanPlayer(LudoScreen screen,List<Dice> dice,RuleEngine ruleEngine) {
		super(ruleEngine);
		this.screen = screen;
		//this.guiCam = screen.getGuiCam();
		this.cam3D = screen.getCam();
		touchPoint = new Vector3();
	}

	@Override
	public Move play() {
		if(!diceRolled){
			List<Integer> diceValList = rollDice();
			if(!(diceValList==null)){
				diceRolled = true;
			}
			return null;
		}
		
		if (Gdx.input.justTouched()) {
			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0);
			//guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			Piece piece = getSelectedPiece();
			if(piece == null){
				return null;
			}
			//assuming single dice
			//check if valid move and move
			System.out.println("Touched: "+piece);
			List<Dice> diceList = screen.getBoardRenderer().getDiceList();
			//TODO dispose instance 
			diceList.clear();
			//TODO: 2 variation
			diceList.add(screen.getBoardRenderer().createDiceInstance());
			return new Move(piece,3);
		}
		return null;
	}

	private Piece getSelectedPiece() {
		// check if touched
		pickRay = cam3D.getPickRay(touchPoint.x, touchPoint.y, 0, 0, Gdx.app.getGraphics().getWidth(),
				Gdx.app.getGraphics().getHeight());
		Vector3 intersection = new Vector3();
		for (Piece piece : pieces) {
			Vector3 tran = new Vector3();
			screen.getBoardRenderer().getPieceInstMap().get(piece).transform.getTranslation(tran);
			if(Intersector.intersectRaySphere(pickRay, tran, BoardRenderer.SQUARE_LENGTH, intersection))
			{
				return piece;
			}
			
		}
		return null;
	}

	protected List<Integer> rollDice() {
		List<Dice> diceList = screen.getBoardRenderer().getDiceList();
		//only last dice eligible to be touched - others should be six - //TODO: check variation with two dice
		Dice dice = diceList.get(diceList.size()-1);
		if (Gdx.input.justTouched()) {
			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),0);
			pickRay = cam3D.getPickRay(touchPoint.x, touchPoint.y, 0, 0, Gdx.app.getGraphics().getWidth(),
					Gdx.app.getGraphics().getHeight());
			Vector3 intersection = new Vector3();
			Vector3 tran = new Vector3();
			dice.getDiceInstance().transform.getTranslation(tran);
			if(Intersector.intersectRaySphere(pickRay, tran, BoardRenderer.SQUARE_LENGTH, intersection))
			{
				return super.rollDice(dice,  screen.getBoardRenderer());
			}
		}
		return null;
	}
	

}
