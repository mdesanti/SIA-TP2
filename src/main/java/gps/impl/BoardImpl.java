package gps.impl;

import gps.api.Board;
import gps.api.Piece;

public class BoardImpl implements Board {

	private Piece[][] board;
	private int checksum = 0;
	
	public BoardImpl(int height, int width) {
		board = new Piece[height][width];
		initBoard();
	}
	
	public BoardImpl(Piece[][] board) {
		this.board = board;
		generateCheckSum();
	}
	
	private void initBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = PieceImpl.empty();
			}
		}
	}

	public Board rotateBoard() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Piece getPieceIn(int y, int x) {
		return board[y][x];
	}
	
	private void generateCheckSum() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				Piece piece = board[i][j];
				checksum += piece.getDownColor();
				checksum += piece.getLeftColor();
				checksum += piece.getRightColor();
				checksum += piece.getUpColor();
			}
		}
	}
	
	public int getHeight() {
		return board.length;
	}
	
	public int getWidth() {
		return board[0].length;
	}
	
	public int getChecksum() {
		return checksum;
	}
	
	public void setPieceIn(int y, int x, Piece piece) {
		board[y][x] = piece;
	}
	
	@Override
	public Board clone() {
		return new BoardImpl(board.clone());
	}
	
}
