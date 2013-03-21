package ar.edu.itba.sia.domain.heuristics;

import ar.edu.itba.sia.domain.Board;
import ar.edu.itba.sia.domain.Util;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.Heuristic;
import com.google.common.collect.Maps;

import java.awt.*;
import java.util.Map;

public class BorderHeuristic implements Heuristic {


    private Map<GPSState, Integer> cache = Maps.newHashMap();

    private boolean isValidEvalLocation(GPSState state, Board board) {
        Point pieceLocation = state.getBoard().getPieceLocation();
        int x = state.getBoard().getDepth() % board.getHeight();
        int y = state.getBoard().getDepth() / board.getWidth();

        if (pieceLocation == null) {
            return x == 0 && y == 0;
        }

        if (pieceLocation.x + 1 == board.getWidth()
                && pieceLocation.y + 1 == board.getHeight()) {
            return false;
        }

        if (pieceLocation.x + 1 == board.getWidth()) {
            return pieceLocation.y + 1 == y && x == 0;
        } else {
            return pieceLocation.x + 1 == x && pieceLocation.y == y;
        }
    }

	@Override
	public Integer apply(GPSState state) {
		Board board = state.getBoard();

        if (cache.containsKey(state)) {
            return cache.get(state);
        }

        int result = -1;
//
//        if (result == -1 && !isValidEvalLocation(state, board)) {
//            return Integer.MAX_VALUE;
//        }
		
		if(result == -1 && !Util.canPutPieceOnBoard(board.getPiece(), board, board.getPieceLocation().x, board.getPieceLocation().y)) {
			return Integer.MAX_VALUE;
		}
		if(result == -1 && !board.isValid()) {
			return Integer.MAX_VALUE;
		}


        if (result == -1) {
		    int complete = 0;
            int actual = 0;
            int n = board.getWidth();
            int constant = n / 2;
            for (int i = 0; i < board.getHeight(); i++) {
                for (int j = 0; j < board.getWidth(); j++) {
                    int distance = Math.abs(constant - i) + Math.abs(constant - j);
                    if (!board.getPieceIn(i, j).isEmpty()) {
                        actual += distance;
                    }
                    complete += distance;
                }
            }

            result = complete - actual;
        }


        cache.put(state, result);

        return result;

	}

}
