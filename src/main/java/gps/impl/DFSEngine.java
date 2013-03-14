package gps.impl;

import gps.api.GPSProblem;
import gps.api.SearchStrategy;

public class DFSEngine extends GPSEngine {

	
	public DFSEngine(GPSProblem problem, SearchStrategy strategy) {
		super(problem, strategy);
	}

	@Override
	public void addNode(GPSNode node) {
		getOpen().add(0, node);
	}

}
