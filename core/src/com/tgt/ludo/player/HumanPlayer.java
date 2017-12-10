package com.tgt.ludo.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
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

	public HumanPlayer(LudoScreen screen) {
		this.screen = screen;
		//this.guiCam = screen.getGuiCam();
		this.cam3D = screen.getCam();
		touchPoint = new Vector3();
	}

	@Override
	public Move play() {
		
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
}
