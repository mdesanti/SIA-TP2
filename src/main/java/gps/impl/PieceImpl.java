package gps.impl;

import gps.api.Piece;

public class PieceImpl implements Piece {

	//four colors available on one piece
	private int up = -1, right = -1, left = -1, down = -1;
	private static Piece empty;
	
	public PieceImpl(int up, int right, int down, int left) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
	}
	
	private PieceImpl() {
	}
	
	public int getUpColor() {
		return up;
	}
	
	public int getDownColor() {
		return down;
	}
	
	public int getLeftColor() {
		return left;
	}
	
	public int getRightColor() {
		return right;
	}
	
	public PieceImpl rotate() {
		return new PieceImpl(left, up, right, down);
	}
	
	public static Piece empty() {
		if(empty == null) {
			empty = new PieceImpl();
		}
		return empty;
	}
}
