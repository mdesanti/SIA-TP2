package ar.edu.itba.sia.domain.heuristics;

import ar.edu.itba.sia.domain.Board;
import ar.edu.itba.sia.domain.Util;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.Heuristic;

public class CenterHeuristic implements Heuristic {

	@Override
	public Integer apply(GPSState state) {
		Board board = state.getBoard();

		int result = -1;


		if (result == -1
				&& !Util.canPutPieceOnBoard(board.getPiece(), board,
						board.getPieceLocation().x, board.getPieceLocation().y)) {
			result = Integer.MAX_VALUE;
		}
		if (result == -1 && !board.isValid()) {
			result = Integer.MAX_VALUE;
		}

		if (result == -1) {
			int complete = 0;
			int actual = 0;
			int n = board.getWidth();
			int constant = n / 2;
			for (int i = 0; i < board.getHeight(); i++) {
				for (int j = 0; j < board.getWidth(); j++) {
					int distance = Math.abs(constant - i)
							+ Math.abs(constant - j);
					if (!board.getPieceIn(i, j).isEmpty()) {
						actual += distance;
					}
					complete += distance;
				}
			}

			result = complete - actual;
		}

		return result;

	}

}
