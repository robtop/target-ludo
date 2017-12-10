package com.tgt.ludo;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Screen;
import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Board.COLOR;
import com.tgt.ludo.player.HumanPlayer;
import com.tgt.ludo.player.Player;
import com.tgt.ludo.ui.LudoScreen;

public class LudoGameState {
    private Board board;
    private Player greenPlayer;
    private Player yellowPlayer;
    private Player redPlayer;
    private Player bluePlayer;
    
    //needed by human players to get inputs
    private Screen screen;
    private List<Player> players;
    
    public LudoGameState(Screen screen) {
    	board = new Board();
    	board.setup();
    	players = new ArrayList<Player>();
    	greenPlayer = new HumanPlayer(((LudoScreen)screen));
    	greenPlayer.setTurn(true);
    	greenPlayer.setPieces(board.getPiecesMap().get(COLOR.GREEN));
    	players.add(greenPlayer);
	}

	public void update(){
    	for(Player player:players){
    		if(player.isTurn()){
    			player.play();
    			//ignore others 
    			break;
    		}
    	}
    }

   
    
	public Board getBoard() {
		return board;
	}
    
}
