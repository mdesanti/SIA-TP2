package gps.impl;

import gps.api.GPSState;
import gps.api.Piece;

public class GPSStateImpl implements GPSState {
	
	private Piece[][] board;
	
	public GPSStateImpl(int height, int width) {
		board = new Piece[height][width];
		initBoard();
	}
	
	public GPSStateImpl(Piece[][] board) {
		this.board = board;
	}

	private void initBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = PieceImpl.empty();
			}
		}
	}

	public boolean compare(GPSState state) {
		return false;
	}
	
	public Piece[][] getBoard() {
		return board;
	}

}
