package gps.impl;

import gps.api.Piece;

public class PieceImpl implements Piece {

	private static int idGenerator = 0;
	//four colors available on one piece
	private int up = -1, right = -1, left = -1, down = -1;
	private static Piece empty;
	private int id;
	
	public PieceImpl(int up, int right, int down, int left) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.id = generateID();
	}
	
	private PieceImpl(int id, int up, int right, int down, int left) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.id = id;
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
		return new PieceImpl(id, left, up, right, down);
		
	}
	
	public static Piece empty() {
		if(empty == null) {
			empty = new PieceImpl();
		}
		return empty;
	}
	
	public int getId() {
		return id;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PieceImpl other = (PieceImpl) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public boolean isEmtpy() {
		return up == -1 && down == -1 && left == -1 && right == -1;
	}
	
	public static int generateID() {
		return idGenerator++;
	}
	
	public int generateChecksum() {
		return up + down + left + right;
	}
	
}
