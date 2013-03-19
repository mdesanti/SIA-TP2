package gps.impl;

import gps.api.Board;
import gps.api.GPSState;
import gps.api.Piece;
import gps.renderer.BoardRenderer;

import java.awt.*;
import java.util.List;

public class GPSStateImpl implements GPSState {

	private GPSState parent;
	private Board board;

	private GPSStateImpl() { }

    public boolean compare(GPSState state) {
		Board rotated = state.getBoard();
		Board myBoard = this.getBoard();

		return false;
	}

	public Board getBoard() {
		return board;
	}

	public int getChecksum() {
		return 0;
	}
	
	@Override
	public String toString() {
		return new BoardRenderer(getBoard()).renderString();
	}

	@Override
	public GPSState getParent() {
		return parent;
	}

	public static GPSState initialState(int height, int width, List<Piece> all) {
		GPSStateImpl state = new GPSStateImpl();
		state.board = BoardImpl.initialBoard(height, width, state, all);
		return state;
	}
	
	public static GPSState fromParent(GPSState parent, Point pieceLocation, Piece toAdd) {
		GPSStateImpl state = new GPSStateImpl();
		state.parent = parent;
		state.board = BoardImpl.fromParent(state, pieceLocation, toAdd);
		return state;
	}
 
}
