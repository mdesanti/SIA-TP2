package gps.api;

import gps.api.Board.Direction;

import java.awt.Point;
import java.util.Map;
import java.util.Set;

public interface Board {

	Board rotateBoard();
	
	Piece getPieceIn(int y, int x);
	
	void setPieceIn(int y, int x, Piece piece);
	
	int getHeight();
	
	int getWidth();
	
	int getChecksum();

    int getPieceCount();
    
    public boolean containsPiece(Piece piece);


    public enum Direction {
        UP(0,-1), DOWN(0,1), LEFT(-1,0), RIGHT(1,0);

        private int x;
        private int y;


        private Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }

    }


	Piece getPieceIn(Point point);

	Set<Piece> getPieces();

	Map<Direction, short[]> getAvailableColors();

	Point getPieceLocation();
}
