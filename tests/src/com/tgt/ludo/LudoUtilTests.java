/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.tgt.ludo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.tgt.ludo.board.Board;
import com.tgt.ludo.board.Piece;
import com.tgt.ludo.board.Square;
import com.tgt.ludo.player.action.Move;
import com.tgt.ludo.util.LudoUtil;

public class LudoUtilTests {

	Piece testPiece;
	Square sittingSquare ;
	Move move;
	
	@Before
	public void init(){
		 testPiece = new Piece();
		 sittingSquare = new Square();
	}
	
	
	
	
	@Test
	public void destforBlueAtOuterTrackEnd() {
		assertEquals(1, 1);
	
		testPiece.setColor(Board.COLOR.BLUE);
		sittingSquare.setIndex(67);
		testPiece.setSittingSuare(sittingSquare);

		move = new Move(testPiece, 10);
		int dest =LudoUtil.calulateDestIndex(move);
		System.out.println(dest);
		assert(dest==9);
	}
	
	@Test
	public void nextIndexOuterTrackEndforBlue() {
		assertEquals(1, 1);
	
		testPiece.setColor(Board.COLOR.BLUE);
		sittingSquare.setIndex(Board.TOTAL_NUM_SQUARES-1);
		testPiece.setSittingSuare(sittingSquare);
		testPiece.setMoveCount(Board.TOTAL_NUM_SQUARES-Board.startIndexes.get(3)-1);
		move = new Move(testPiece, 6);
		int next =LudoUtil.calulateNextIndex(move, 0);
		System.out.println(next);
		//not on home square
		assert(!move.getPiece().isHomeSq());
		assert(next==0);
	}
	
	@Test
	public void nextIndexOuterTrackEndforBlue1() {
		assertEquals(1, 1);
	
		testPiece.setColor(Board.COLOR.BLUE);
		sittingSquare.setIndex(0);
		testPiece.setSittingSuare(sittingSquare);
		testPiece.setMoveCount(Board.TOTAL_NUM_SQUARES-Board.startIndexes.get(3)+1);
		move = new Move(testPiece, 6);
		int next =LudoUtil.calulateNextIndex(move, 0);
		System.out.println(next);
		//not on home square
		assert(!move.getPiece().isHomeSq());
		assert(next==1);
	}
	
	@Test
	public void nextIndexOuterTrackforBlue() {
		assertEquals(1, 1);
	
		testPiece.setColor(Board.COLOR.BLUE);
		sittingSquare.setIndex(Board.TOTAL_NUM_SQUARES-1);
		testPiece.setMoveCount(Board.TOTAL_NUM_SQUARES-Board.startIndexes.get(3)-1);
		testPiece.setSittingSuare(sittingSquare);
		
		move = new Move(testPiece, 6);
		int next =LudoUtil.calulateNextIndex(move, 5);
	
		//not on home square
		assert(!move.getPiece().isHomeSq());
		assert(next==5);
	}
	
	
	@Test
	public void getIntoHomeforBlue() {
		assertEquals(1, 1);
	
		testPiece.setColor(Board.COLOR.BLUE);

		sittingSquare.setIndex(Board.startIndexes.get(3)-1);
		testPiece.setMoveCount(Board.TOTAL_NUM_SQUARES-1);
		testPiece.setSittingSuare(sittingSquare);
		
		move = new Move(testPiece, 5);
		int next =LudoUtil.calulateNextIndex(move, 0);
		System.out.println(next);
		//not on home square
		assert(move.getPiece().isHomeSq());
		assert(next==0);
	}


}
