package gps.api;

public interface Board {

	Board rotateBoard();
	
	Piece getPieceIn(int y, int x);
	
	void setPieceIn(int y, int x, Piece piece);
	
	int getHeight();
	
	int getWidth();
	
	int getChecksum();

    int getPieceCount();

	Board clone();


}
