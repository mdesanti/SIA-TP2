package ar.edu.itba.sia.domain.costFunctions;

import ar.edu.itba.sia.domain.Piece;
import ar.edu.itba.sia.gps.api.CostFunction;
import ar.edu.itba.sia.gps.api.GPSState;

public class ColorBasedCostFunction implements CostFunction {
	
	private static int GREY_FACTOR = 1;
	private static int NOT_GREY_FACTOR = 3;

	@Override
	public Integer getCost(Piece piece, GPSState state) {
		int[] colors = new int[4];
		colors[0] = piece.getUpColor();
		colors[1] = piece.getDownColor();
		colors[2] = piece.getLeftColor();
		colors[3] = piece.getRightColor();
		int grey = 0;
		int notGrey = 0;
		
		for(Integer color: colors) {
			if(color == -1) {
				grey++;
			} else {
	    		notGrey++;
			}
		}
		
		return grey * GREY_FACTOR + notGrey * NOT_GREY_FACTOR;
		
	}

}
