package gps.impl;

import gps.api.StatsHolder;

public class StatsHolderImpl implements StatsHolder {
	
	private long start = 0 ;
	private long end = 0;
	private long states = 0;
	private long depth = 0;
	private long explotions = 0;
	private long leafNodes = 0;

	public void addState() {
		states++;
	}

	public long getStatesNumber() {
		return states;
	}

	public void setSolutionDepth(long depth) {
		this.depth = depth;
	}

	public long getSolutionDepth() {
		return depth;
	}

	public void startSimulation() {
		this.start = System.currentTimeMillis();
	}

	public void stopSimulation() {
		this.end = System.currentTimeMillis();
	}

	public long getSimulationTime() {
		return end - start;
	}

	public void addExplodedNode() {
		explotions++;
	}

	public long getExplodedNodes() {
		return explotions;
	}

	public void addLeafNode() {
		leafNodes++;
	}

	public long getLeafNodesNumber() {
		return leafNodes;
	}

}
