package gps.impl;

import gps.api.Board;
import gps.api.GPSState;
import gps.api.Piece;

public class GPSStateImpl implements GPSState {
	
	private Board board;
	
	public GPSStateImpl(Board board) {
		this.board = board;
	}

	public boolean compare(GPSState state) {
		if(state.getChecksum() != this.getChecksum()) {
			return false;
		}
		//rotate the piece four times
		Board rotated = state.getBoard();
		for(int i = 0; i < 4; i++) {
			if(rotated.equals(board)) {
				return true;
			}
			rotated = rotated.clone();
		}
		return false;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int getChecksum() {
		return board.getChecksum();
	}
	
}
