package ar.edu.itba.sia.domain;

import ar.edu.itba.sia.gps.impl.GPSProblemImpl;

public class PieceImpl implements Piece {

	//four colors available on one piece
	private int up = -1, right = -1, left = -1, down = -1;
	private static Piece empty;
	private int id;
	
	public PieceImpl(int up, int right, int down, int left) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.id = GPSProblemImpl.nextId();
	}
	
	private PieceImpl(int id, int up, int right, int down, int left) {
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.id = id;
	}
	
	
	private PieceImpl() {
		this.id = GPSProblemImpl.nextId();
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
	
	public PieceImpl rotate(int n) {
        n %= 4;
        switch(n) {
            case 1:
		        return new PieceImpl(id, left, up, right, down);
            case 2:
                return new PieceImpl(id, down, left, up, right);
            case 3:
                return new PieceImpl(id, right, down, left, up);
            default:
                return this;
        }
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PieceImpl piece = (PieceImpl) o;

        if (down != piece.down) return false;
        if (id != piece.id) return false;
        if (left != piece.left) return false;
        if (right != piece.right) return false;
        if (up != piece.up) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = up;
        result = 31 * result + right;
        result = 31 * result + left;
        result = 31 * result + down;
        result = 31 * result + id;
        return result;
    }

    public boolean isEmpty() {
		return up == -1 && down == -1 && left == -1 && right == -1;
	}
	
	public int generateChecksum() {
		return up + down + left + right;
	}

    @Override
    public boolean hasSameColors(Piece base) {
        PieceImpl piece = (PieceImpl) base;
        if (down != piece.down) return false;
        if (left != piece.left) return false;
        if (right != piece.right) return false;
        if (up != piece.up) return false;
        return true;
    }

    @Override
    public boolean hasSameIdWith(Piece piece) {
        return this.getId() == piece.getId();
    }


    @Override
	public String toString() {
		return "PieceImpl [up=" + up + ", right=" + right + ", left=" + left
				+ ", down=" + down + ", id=" + id + "]";
	}
	
	
	
}
