package gps.impl;

import com.google.common.collect.Maps;
import gps.api.Board;
import gps.api.GPSState;
import gps.api.Piece;
import gps.renderer.BoardRenderer;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class GPSStateImpl implements GPSState {

	private GPSState parent;
	private Board board;

	private GPSStateImpl() { }
    

    public boolean compare(GPSState state) {
//		 if(state.getChecksum() != this.getChecksum()) {
//		 return false;
//		 }
		Board rotated = state.getBoard();
		Board myBoard = this.getBoard();
		
		
		if (rotated.equals(myBoard)) {
			return true;
		}
		return false;
	}

	public boolean containsPiece(Piece p) {
		return board.containsPiece(p);
	}

	public Board getBoard() {
		return board;
	}

	public int getChecksum() {
		return 0;
	}

	public Piece getPieceIn(Point point) {
		return board.getPieceIn(point);
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
