package com.tgt.ludo.problems;

/**
 * Vertex in a BFS tree
 * 
 * @author robin
 *
 */
public class Vertex {
	private int key;
	private int dist;
	
	public Vertex() {
		// TODO Auto-generated constructor stub
	}
	
	public Vertex(int key,int dist) {
		this.key = key;
		this.dist = dist;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}
	
	
}
