package gps.impl;

import gps.api.Board;
import gps.api.GPSState;
import gps.api.Piece;

public class GPSStateImpl implements GPSState {
	
	private GPSState parent;
	private Piece addedPiece;
	private int y;
	private int x;
	private int height;
	private int width;
	
	public GPSStateImpl(Piece addedPiece, int y, int x, int height, int width, GPSState parent) {
		super();
		this.addedPiece = addedPiece;
		this.y = y;
		this.x = x;
		this.parent = parent;
		this.height = height;
		this.width = width;
	}

	public boolean compare(GPSState state) {
//		if(state.getChecksum() != this.getChecksum()) {
//			return false;
//		}
		//rotate the piece four times
		Board rotated = state.getBoard();
		Board myBoard = this.getBoard();
		if(rotated.equals(myBoard)) {
			return true;
		}
//		for(int i = 0; i < 4; i++) {
//			if(rotated.equals(myBoard)) {
//				return true;
//			}
//			rotated = rotated.rotateBoard();
//		}
		return false;
	}
	
	public Board getBoard() {
		if(addedPiece == null) {
			return new BoardImpl(height, width);
		} else {
			Board b = parent.getBoard();
			b.setPieceIn(y, x, addedPiece);
			return b;
		}
	}
	
	public int getChecksum() {
//		return board.getChecksum();
		return 0;
	}
	
}
