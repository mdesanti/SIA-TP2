package gps.api;

public interface Piece {
	
	int getUpColor();

	int getDownColor();

	int getLeftColor();

	int getRightColor();

	/**
	 * Rotates the piece 90 degrees clockwise
	 * @return
	 */
	Piece rotate();
	
	boolean isEmtpy();

}
