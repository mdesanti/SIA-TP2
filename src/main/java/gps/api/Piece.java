package gps.api;

public interface Piece {
	
	int getUpColor();

	int getDownColor();

	int getLeftColor();

	int getRightColor();
	
	int getId();

	/**
	 * Rotates the piece 90 degrees clockwise
	 * @return
	 */
	Piece rotate();
	
	boolean isEmtpy();
	
	public int generateChecksum();

}
