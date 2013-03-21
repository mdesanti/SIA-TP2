package ar.edu.itba.sia.domain.heuristics;

import java.util.List;

import com.google.common.collect.Lists;

import ar.edu.itba.sia.domain.Board;
import ar.edu.itba.sia.domain.Piece;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.exception.IncorrectPiecesException;

public class BorderHeuristic implements Heuristic {

	@Override
	public Integer apply(GPSState state) {
		// CRIS: antes de cambiar esto preguntame!!!!
		Board board = state.getBoard();
		int times = board.getWidth() - (int) (board.getWidth() / 2);
		for (int i = 0; i < times; i++) {
			checkSquare(board, i);
		}
	}

	private void checkSquare(Board board, int i) {
		int start = i;
		int end = board.getWidth() - (i + 1);

		List<Piece> up = getHorizontalBetween(board, start, start, end);
		checkUp(board, i, i, end, up);
		List<Piece> down = getHorizontalBetween(board, end, start, end);
		checkDown(board, end, i, end, up);
		List<Piece> left = getVerticalBetween(board, start, start, end);
		List<Piece> right = getVerticalBetween(board, end, start, end);
	}

	private int checkUp(Board board, int row, int startCol, int endCol, List<Piece> pieces) {
		int cumSum = 0;
		for (int i = 0; i < pieces.size(); i++) {
			int col = startCol + i;
			Piece up = board.getPieceIn(row - 1, col);
			Piece piece = pieces.get(i);
			if (up.getDownColor() != -1 && piece.getUpColor() != up.getDownColor()) {
				return -1;
			} else if (up.getDownColor() == -1 && piece.getUpColor() == 0 && isOut) {
				return cumSum++;
			} else if (up.getDownColor() == -1 && piece.getUpColor() == 0) {
			} else {
				throw new IncorrectPiecesException();
			}
		}
		return cumSum;
	}
	
	private boolean isOutOfBoard(Board b, int y, int x) {
		if(y < 0 || y >= board)
	}

	private int checkDown(Board board, int row, int startCol, int endCol, List<Piece> pieces) {
		int cumSum = 0;
		for (int i = 0; i < pieces.size(); i++) {
			int col = startCol + i;
			Piece up = board.getPieceIn(row + 1, col);
			Piece piece = pieces.get(i);
			if (up.getDownColor() != -1 && piece.getUpColor() != up.getDownColor()) {
				return cumSum++;
			} else if (up.getDownColor() == -1 && piece.getUpColor() == 0) {
				return cumSum++;
			}
		}
		return cumSum;
	}

	private List<Piece> getHorizontalBetween(Board board, int row, int leftCol, int rightCol) {
		List<Piece> list = Lists.newArrayList();
		for (int i = 0; i < (leftCol - rightCol) + 1; i++) {
			list.add(board.getPieceIn(row, rightCol + i));
		}
		return list;
	}

	private List<Piece> getVerticalBetween(Board board, int col, int upRow, int lowRow) {
		List<Piece> list = Lists.newArrayList();
		for (int i = 0; i < (lowRow - upRow) + 1; i++) {
			list.add(board.getPieceIn(upRow + i, col));
		}
		return list;
	}

}
