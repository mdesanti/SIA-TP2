package gps.api;

import gps.impl.BoardImpl;

import java.awt.Point;
import java.util.Map;

/**
 * GPSState interface.
 */
public interface GPSState {
	
	/**
	 * Compares self to another state to determine
	 * whether they are the same or not.
	 * @param state The state to compare to.
	 * @return true if self is the same as the state given,
	 * false if they are different.
	 */
	boolean compare(GPSState state);
	
	Board getBoard();
	
	int getChecksum();
	
	Piece getPieceIn(Point p);
	
	boolean containsPiece(Piece p);

	GPSState getParent();
}
