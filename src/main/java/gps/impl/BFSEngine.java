package gps.impl;

import gps.api.GPSProblem;
import gps.api.SearchStrategy;

public class BFSEngine extends GPSEngine {

	
	public BFSEngine(GPSProblem problem, SearchStrategy strategy) {
		super(problem, strategy);
	}

	@Override
	public void addNode(GPSNode node) {
		getOpen().add(node);
	}
}
