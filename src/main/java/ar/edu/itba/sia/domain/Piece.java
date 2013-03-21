package ar.edu.itba.sia.domain;

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
	Piece rotate(int times);
	
	boolean isEmpty();

	int generateChecksum();

    boolean hasSameColors(Piece piece);

    boolean hasSameIdWith(Piece piece);
}
