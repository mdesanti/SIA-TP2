package ar.edu.itba.sia.domain.heuristics;

import ar.edu.itba.sia.domain.Board;
import ar.edu.itba.sia.domain.Util;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.Heuristic;

public class OrderHeuristic implements Heuristic {

	@Override
	public Integer apply(GPSState state) {
		if(!state.getBoard().isValid()) {
			return Integer.MAX_VALUE;
		}
		if(Util.isValidEvalLocation(state)) {
			Board board = state.getBoard();
			int max = state.getBoard().getHeight() * state.getBoard().getWidth();
			int depth = board.getDepth();
			int x = depth % board.getWidth();
	    	int y = depth / board.getHeight();
	    	return max - y*board.getWidth() + x;
		} else {
			return Integer.MAX_VALUE;
		}
	}
}
