package ar.edu.itba.sia.gps.impl;

import ar.edu.itba.sia.domain.BoardImpl;
import ar.edu.itba.sia.domain.Board;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.domain.Piece;
import ar.edu.itba.sia.domain.renderer.BoardRenderer;

import java.awt.*;
import java.util.List;

public class GPSStateImpl implements GPSState {

	private GPSState parent;
	private Board board;
    private int checksum = -1;

	private GPSStateImpl() { }

    public boolean compare(GPSState state) {
//		Board rotated = state.getBoard();
//		Board board = this.getBoard();
//
//        if (rotated.getDepth() == board.getDepth() &&
//                rotated.getChecksum() == board.getDepth()) {
//            return rotated.equals(board);
//        }

		return false;
	}

	public Board getBoard() {
		return board;
	}

	public int getChecksum() {
		checksum = 0;
        if (this.parent != null) {
            checksum += this.getParent().getChecksum();
            checksum += this.board.getPiece().generateChecksum();
        }
        return checksum;
	}
	
	@Override
	public String toString() {
		return new BoardRenderer(getBoard()).renderString();
	}

	@Override
	public GPSState getParent() {
		return parent;
	}

	public static GPSState initialState(int height, int width, List<Piece> all, int colorCount) {
		GPSStateImpl state = new GPSStateImpl();
		state.board = BoardImpl.initialBoard(height, width, state, all, colorCount);
		return state;
	}
	
	public static GPSState fromParent(GPSState parent, Point pieceLocation, Piece toAdd) {
		GPSStateImpl state = new GPSStateImpl();
		state.parent = parent;
		state.board = BoardImpl.fromParent(state, pieceLocation, toAdd);
		return state;
	}
 
}
