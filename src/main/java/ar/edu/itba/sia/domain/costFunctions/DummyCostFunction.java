package ar.edu.itba.sia.domain.costFunctions;

import ar.edu.itba.sia.domain.Piece;
import ar.edu.itba.sia.gps.api.CostFunction;

public class DummyCostFunction implements CostFunction {

	@Override
	public Integer getCost(Piece piece) {
		return 1;
	}
}
