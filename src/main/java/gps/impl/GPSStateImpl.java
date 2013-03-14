package gps.impl;

import gps.api.Board;
import gps.api.GPSState;
import gps.api.Piece;

public class GPSStateImpl implements GPSState {
	
	private Board board;
	private int checksum = 0;
	
	public GPSStateImpl(Board board) {
		this.board = board;
	}

	public boolean compare(GPSState state) {
		if(state.getChecksum() != this.getChecksum()) {
			return false;
		}
	}
	
	public Board getBoard() {
		return board;
	}
	
	public int getChecksum() {
		return checksum;
	}

}
