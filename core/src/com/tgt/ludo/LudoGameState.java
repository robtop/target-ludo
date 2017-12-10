package com.tgt.ludo;

import com.tgt.ludo.board.Board;
import com.tgt.ludo.player.Player;

public class LudoGameState {
    private Board board;
    private Player greenPlayer;
    private Player yellowPlayer;
    private Player redPlayer;
    private Player bluePlayer;
    
    public LudoGameState() {
    	board = new Board();
    	board.setup();
	}

	public void update(){
    	
    }


	public Board getBoard() {
		return board;
	}
    
}
