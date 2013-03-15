package gps.impl;

import gps.api.Board;
import gps.api.Piece;
import gps.persist.GameXML;

import java.awt.*;
import java.util.Map;

public class BoardImpl implements Board {

	private Piece[][] board;
	private int checksum = 0;
	private int height;
	private int width;

	private BoardImpl() {
	}

	private int pieceCount;

	public BoardImpl(int height, int width) {
		this.height = height;
		this.width = width;
		board = new Piece[height][width];
		generateCheckSum();
	}

	public BoardImpl(Piece[][] board) {
		this.board = board;
		height = board.length;
		width = board[0].length;
		generateCheckSum();
	}

	public Board rotateBoard() {
		Board rotated = new BoardImpl(height, width);
		int ii = 0;
		int jj = 0;
		for (int i = 0; i < width; i++) {
			for (int j = height - 1; j >= 0; j--) {
				rotated.setPieceIn(ii, jj, this.getPieceIn(j, i).rotate());
				jj++;
			}
			ii++;
			jj = 0;
		}
		return rotated;
	}

	public Piece getPieceIn(int y, int x) {
		if (board[y][x] == null) {
			return PieceImpl.empty();
		}
		return board[y][x];
	}

	private void generateCheckSum() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Piece piece = board[i][j];
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
	}

	public int getHeight() {
		return board.length;
	}

	public int getWidth() {
		return board[0].length;
	}

	public int getPieceCount() {
		return pieceCount;
	}

	public int getChecksum() {
		return checksum;
	}

	public void setPieceIn(int y, int x, Piece piece) {
		board[y][x] = piece;
		checksum = 0;
		if (!piece.isEmtpy()) {
			pieceCount++;
		}
		generateCheckSum();
	}

	@Override
	public Board clone() {
		Piece[][] clone = new Piece[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				clone[i][j] = board[i][j];
			}
		}
		BoardImpl b = new BoardImpl(clone);
		b.pieceCount = this.pieceCount;
		return b;
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
		BoardImpl b = new BoardImpl(width, height);
		b.width = width;
		b.height = height;
		b.board = new Piece[height][width];

		for (Point point : map.keySet()) {
			b.board[point.y][point.x] = map.get(point).toPiece();
		}
		return b;
	}
}
