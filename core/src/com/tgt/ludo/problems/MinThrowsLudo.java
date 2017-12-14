package com.tgt.ludo.problems;

// Java program to find minimum number of dice 
// throws required to reach last cell from first 
// cell of a given snake and ladder board

import java.util.LinkedList;
import java.util.Queue;

public class MinThrowsLudo {
	// arm of the ludo board
	public static final int DIMENSION = 6;

	// rest squares are not added
	// final home is a square
	// home squares = (DIMENSION - 1)
	public static final int TOTAL_NUM_SQUARES = (DIMENSION * 4 * 2) + 4 + (DIMENSION - 1) + 1;

	public static void main(String[] args) {

		int moves[] = new int[TOTAL_NUM_SQUARES];

		for (int i = 0; i < TOTAL_NUM_SQUARES; i++)
			moves[i] = -1;

		// special home square
		//moves[7] = TOTAL_NUM_SQUARES - 1;

		// home squares
		moves[TOTAL_NUM_SQUARES - 2] = TOTAL_NUM_SQUARES - 1;
		moves[TOTAL_NUM_SQUARES - 3] = TOTAL_NUM_SQUARES - 1;
		moves[TOTAL_NUM_SQUARES - 4] = TOTAL_NUM_SQUARES - 1;
		moves[TOTAL_NUM_SQUARES - 5] = TOTAL_NUM_SQUARES - 1;
		moves[TOTAL_NUM_SQUARES - 6] = TOTAL_NUM_SQUARES - 1;
		moves[TOTAL_NUM_SQUARES - 7] = TOTAL_NUM_SQUARES - 1;

		// jail
		//moves[1] = 0;
//		moves[2] = 0;
//		moves[3] = 0;
//		moves[4] = 0;
//		moves[5] = 0;
//		moves[6] = 0; 
//		moves[18] = 0;

		//add 1 to get to start position
		int numOfThrows = getMinDiceThrows(moves, TOTAL_NUM_SQUARES)+1;
		System.out.println("Min Dice throws required is " + numOfThrows );
	}

	/**
	 * Gets the min dice throws from the start square 0 (not rest square) to the
	 * last six squares of hte ludo board.
	 * 
	 * @param move
	 * @param n
	 * @return
	 */
	static int getMinDiceThrows(int move[], int n) {
		int visited[] = new int[n];
		Queue<Vertex> q = new LinkedList<>();
		Vertex ver = new Vertex(0, 0);
		visited[0] = 1;
		q.add(ver);

		// Do a BFS starting from vertex at index 0
		while (!q.isEmpty()) {
			ver = q.remove();
			int k = ver.getKey();

			if (k == TOTAL_NUM_SQUARES - 1) {
				break;
			}
			for (int j = k + 1; j <= (k + 6) && j < n; ++j) {
				// If this cell is already visited, then ignore
				if (visited[j] == 0) {
					// Otherwise calculate its distance and
					// mark it as visited
					Vertex a = new Vertex();
					a.setDist(ver.getDist() + 1);
					visited[j] = 1;

					// become the adjacent of 'i'
					if (move[j] != -1)
						a.setKey(move[j]);
					else
						a.setKey(j);
					 if(move[j] == 0){
						 //if a jail square we need to add 1 since a six is required to get to start position
						 a.setDist(a.getDist()+1);
					 }
					    q.add(a);
				}
			}
		}

		// We reach here when 'qe' has last vertex
		// return the distance of vertex in 'qe'
		return ver.getDist();
	}

}
