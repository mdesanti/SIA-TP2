package gps.impl;

import gps.api.Board;
import gps.api.GPSState;
import gps.api.Piece;

import java.awt.Point;
import java.util.Map;

import com.google.common.collect.Maps;

public class GPSStateImpl implements GPSState {

	private GPSState parent;
	private int y;
	private int x;
	private int height;
	private int width;
	private Map<Point, Piece> cache = Maps.newHashMap();

	public GPSStateImpl(Piece addedPiece, int y, int x, int height, int width,
			GPSState parent) {
		super();
		this.y = y;
		this.x = x;
		this.parent = parent;
		this.height = height;
		this.width = width;
		cache.put(new Point(x, y), addedPiece);
	}

	public boolean compare(GPSState state) {
		// if(state.getChecksum() != this.getChecksum()) {
		// return false;
		// }
		// rotate the piece four times
//		Board rotated = state.getBoard();
//		Board myBoard = this.getBoard();
//		if(rotated.getPieceCount() != myBoard.getPieceCount()) {
//			return false;
//		}
//		if (rotated.equals(myBoard)) {
//			return true;
//		}
		// for(int i = 0; i < 4; i++) {
		// if(rotated.equals(myBoard)) {
		// return true;
		// }
		// rotated = rotated.rotateBoard();
		// }
		return false;
	}

	public Board getBoard() {
		Board b = new BoardImpl(height, width);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Point point = new Point(j, i);
				Piece piece = getPieceIn(point);
				b.setPieceIn(i, j, piece);
			}
		}
		return b;
	}

	public Piece getPieceIn(Point point) {
		Piece p = cache.get(point);
		if (p == null) {
			if (parent == null) {
				return PieceImpl.empty();
			} else {
				p = parent.getPieceIn(point);
				cache.put(point, p);
			}
		}
		return p;
	}

	public int getChecksum() {
		// return board.getChecksum();
		return 0;
	}

}
