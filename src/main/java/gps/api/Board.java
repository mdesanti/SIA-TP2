package gps.api;

import java.awt.*;
import java.util.Map;

public interface Board {

	Board rotateBoard();
	
	Piece getPieceIn(int y, int x);
	
	void setPieceIn(int y, int x, Piece piece);
	
	int getHeight();
	
	int getWidth();
	
	int getChecksum();

    int getPieceCount();
    
    public boolean containsPiece(Piece piece);

    int getDepth();

    int getColorCount();


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

	Map<Direction, short[]> getAvailableColors();

	Point getPieceLocation();
}
