package ar.edu.itba.sia.gps.impl;

import ar.edu.itba.sia.domain.Board;
import ar.edu.itba.sia.domain.BoardImpl;
import ar.edu.itba.sia.domain.Piece;
import ar.edu.itba.sia.gps.api.GPSState;

import java.awt.*;
import java.util.List;

public class GPSStateImpl implements GPSState {

	private GPSState parent;
	private Board board;
	private int checksum = -1;

	private GPSStateImpl() {
	}

	public static int checkSumCheck = 0;
	public static int checkSumHit = 0;
	public static int equalsHit = 0;
	public static int errorHit = 0;

	public boolean compare(GPSState state) {
        Board other = state.getBoard();


        checkSumCheck++;
        if (other.getDepth() == board.getDepth()) {

            if (board.likelyToBeEqual(other)) {
                checkSumHit++;
            }

            boolean eq = other.equals(board);

            if (!eq) {
                for (int i = 1; i <= 3; i++) {
                    try {
                        other = other.rotateBoard();
                        eq = other.equals(board);
                        if (eq) {
                            break;
                        }
                    } catch (Exception e) {

                    }
                }
            }

            if (eq) {
                equalsHit++;
            }

            if (eq && !board.likelyToBeEqual(other)) {
                errorHit++;
            }

            return eq;
        }



        return false;
    }

    public Board getBoard() {
		return board;
	}

	@Override
	public String toString() {
		return board.toString();
	}

	@Override
	public GPSState getParent() {
		return parent;
	}
	
	public static GPSState initialState(int height, int width, List<Piece> all,
			int colorCount) {
		GPSStateImpl state = new GPSStateImpl();
		state.board = BoardImpl.initialBoard(height, width, state, all,
				colorCount);
		return state;
	}

	public static GPSState fromParent(GPSState parent, Point pieceLocation,
			Piece toAdd) {
		GPSStateImpl state = new GPSStateImpl();
		state.parent = parent;
		state.board = BoardImpl.fromParent(state, pieceLocation, toAdd);
		return state;
	}

}
