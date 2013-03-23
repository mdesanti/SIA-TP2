package ar.edu.itba.sia.domain.costFunctions;

import ar.edu.itba.sia.domain.Piece;
import ar.edu.itba.sia.gps.api.CostFunction;

public class RotationCostFunction implements CostFunction {

	@Override
	public Integer getCost(Piece piece) {
		return piece.getRotationLevel();
	}

}
