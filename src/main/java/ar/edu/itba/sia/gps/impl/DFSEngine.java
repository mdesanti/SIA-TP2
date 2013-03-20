package ar.edu.itba.sia.gps.impl;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.SearchStrategy;

public class DFSEngine extends GPSEngine {

	
	public DFSEngine(GPSProblem problem, SearchStrategy strategy) {
		super(problem, strategy);
	}

	@Override
	public void addNode(GPSNode node) {
		getOpen().add(0, node);
	}

}
