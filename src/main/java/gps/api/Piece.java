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
	
	boolean isEmpty();
	
	public int generateChecksum();
	
	public boolean equalsNoId(Object obj);

}
