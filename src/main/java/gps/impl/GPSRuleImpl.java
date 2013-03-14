package gps.impl;

import gps.api.Board;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.api.Piece;
import gps.exception.NotAppliableException;

public class GPSRuleImpl implements GPSRule{
	
	private Piece piece;
	private int y;
	private int x;
	

	public GPSRuleImpl(Piece piece, int y, int x) {
		this.piece = piece;
		this.y = y;
		this.x = x;
	}

	public Integer getCost() {
		return 0;
	}

	public String getName() {
		return "Put " + piece.toString() + " in " + y + " " + x;
	}

	public GPSState evalRule(GPSState state) throws NotAppliableException {
		Board board = state.getBoard();
		if(!board.getPieceIn(y, x).isEmtpy()) {
			throw new NotAppliableException();
		}
		Piece up, right, down, left;
		up = getUpPiece(board);
		right = getRightPiece(board);
		down = getDownPiece(board);
		left = getLeftPiece(board);
		
		if(up == null) {
			if(piece.getUpColor() != 0) {
				throw new NotAppliableException();
			}
		} else {
			if(up.getDownColor() != -1 && piece.getUpColor() != up.getDownColor()) {
				throw new NotAppliableException();
			}
		}
		if(right == null) {
			if(piece.getRightColor() != 0) {
				throw new NotAppliableException();
			}
		} else {
			if(right.getLeftColor() != -1 && piece.getRightColor() != right.getLeftColor()) {
				throw new NotAppliableException();
			}
		}
		if(left == null) {
			if(piece.getLeftColor() != 0) {
				throw new NotAppliableException();
			}
		} else {
			if(left.getRightColor() != -1 && piece.getLeftColor() != left.getRightColor()) {
				throw new NotAppliableException();
			}
		}
		if(down == null) {
			if(piece.getDownColor() != 0) {
				throw new NotAppliableException();
			}
		} else {
			if(down.getUpColor() != -1 && piece.getDownColor() != down.getUpColor()) {
				throw new NotAppliableException();
			}
		}
		
		Board cloned = board.clone();
		cloned.setPieceIn(y, x, piece);
		return new GPSStateImpl(cloned);
		
	}

	private Piece getUpPiece(Board board) {
		if(y-1 < 0) {
			return null;
		}
		return board.getPieceIn(y-1, x);
	}
	
	private Piece getLeftPiece(Board board) {
		if(x-1 < 0) {
			return null;
		}
		return board.getPieceIn(y, x-1);
	}
	
	private Piece getRightPiece(Board board) {
		if(x+1 >= board.getWidth()) {
			return null;
		}
		return board.getPieceIn(y, x+1);
	}
	
	private Piece getDownPiece(Board board) {
		if(y+1 >= board.getHeight()) {
			return null;
		}
		return board.getPieceIn(y+1, x);
	}

}
