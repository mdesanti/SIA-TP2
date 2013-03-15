package gps.impl;

import gps.api.Board;
import gps.api.GPSState;
import gps.api.Piece;
import gps.persist.GameXML;

import java.awt.Point;
import java.util.Map;

import com.google.common.collect.Maps;

public class BoardImpl implements Board {

	private Map<Point, Piece> board = Maps.newHashMap();
	private Integer checksum = null;
	private int height;
	private int width;
	private GPSState state;

	private BoardImpl() {
	}

	private int pieceCount;

	public BoardImpl(int height, int width, GPSState state) {
		this.height = height;
		this.width = width;
		this.state = state;
	}

	public Board rotateBoard() {
		// Board rotated = new BoardImpl(height, width);
		// int ii = 0;
		// int jj = 0;
		// for (int i = 0; i < width; i++) {
		// for (int j = height - 1; j >= 0; j--) {
		// rotated.setPieceIn(ii, jj, this.getPieceIn(j, i).rotate());
		// jj++;
		// }
		// ii++;
		// jj = 0;
		// }
		// return rotated;
		return null;
	}

	public Piece getPieceIn(int y, int x) {
		Point p = new Point(x, y);
		if (board.get(p) == null) {
			if (state == null) {
				board.put(p, PieceImpl.empty());
			} else {
				Piece piece = state.getPieceIn(p);
				board.put(p, piece);
			}
		}
		return board.get(p);
	}

	private int generateCheckSum() {
		Integer checksum = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Piece piece = this.getPieceIn(i, j);
				if (piece == null) {
					checksum += -4;
				} else {
					checksum += piece.getDownColor();
					checksum += piece.getLeftColor();
					checksum += piece.getRightColor();
					checksum += piece.getUpColor();
				}
			}
		}
		return checksum;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getPieceCount() {
		return pieceCount;
	}

	public int getChecksum() {
		if(checksum == null) {
			checksum = generateCheckSum();
		}
		return checksum;
	}

	public void setPieceIn(int y, int x, Piece piece) {
		checksum = null;
		board.put(new Point(x, y), piece);
		if (!piece.isEmtpy()) {
			pieceCount++;
		}
	}

	@Override
	public boolean equals(Object obj) {
		Board board2 = (Board) obj;
		if (this.getChecksum() != board2.getChecksum()) {
			return false;
		}
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (!this.getPieceIn(i, j).equals(board2.getPieceIn(i, j))) {
					return false;
				}
			}
		}
		return true;
	}

	public static Board withPieces(int width, int height,
			Map<Point, GameXML.GameNode> map) {
		BoardImpl b = new BoardImpl(width, height, null);
		b.width = width;
		b.height = height;

		for (Point point : map.keySet()) {
			b.board.put(new Point(point.y, point.x), map.get(point).toPiece());
		}
		return b;
	}

	public boolean containsPiece(Piece piece) {
		if(!board.containsValue(piece)) {
			return state.containsPiece(piece);
		} else {
			return true;
		}
	}
}
