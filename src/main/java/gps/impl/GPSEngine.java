package gps.impl;

import gps.api.*;
import gps.exception.NotAppliableException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class GPSEngine {

	private List<GPSNode> open = new LinkedList<GPSNode>();

	private List<GPSNode> closed = new ArrayList<GPSNode>();

	private GPSProblem problem;
	
	private StatsHolder stats;
	
	// Use this variable in the addNode implementation
	private SearchStrategy strategy;

    public GPSEngine(GPSProblem problem, SearchStrategy strategy) {
		super();
		this.problem = problem;
		this.strategy = strategy;
	}


	public boolean engine(GPSProblem myProblem, SearchStrategy myStrategy, StatsHolder holder) {
		this.stats = holder;
		problem = myProblem;
		strategy = myStrategy;

		GPSNode rootNode = new GPSNode(problem.getInitState(), 0);
		stats.addState();
		boolean finished = false;
		boolean failed = false;

		open.add(rootNode);
		stats.startSimulation();
		while (!failed && !finished) {
			if (open.size() <= 0) {
				failed = true;
			} else {
				GPSNode currentNode = open.get(0);
				closed.add(currentNode);
				open.remove(0);
				if (isGoal(currentNode)) {
					stats.stopSimulation();
					finished = true;
                    System.out.println("Showing a solution");
					System.out.println(currentNode.getSolution());
					stats.setSolutionDepth(currentNode.getDepth());
				} else {
					stats.addExplodedNode();
					explode(currentNode);
				}
			}
		}

		if (finished) {
			System.out.println("OK! solution found!");
			return true;
		} else if (failed) {
			System.err.println("FAILED! solution not found!");
			return false;
		}
		return false;
	}

	private  boolean isGoal(GPSNode currentNode) {
		return currentNode.getState() != null
				&& problem.checkGoalState(currentNode.getState());
	}

	protected boolean explode(GPSNode node) {
		if(problem.getRules() == null){
			System.err.println("No rules!");
			return false;
		}
		
		boolean addedChild = false;
		for (GPSRule rule : problem.getRules()) {
			GPSState newState = null;
			try {
				newState = rule.evalRule(node.getState());
			} catch (NotAppliableException e) {
				// Do nothing
			}
			if (newState != null
					&& !checkBranch(node, newState)
					&& !checkOpenAndClosed(node.getCost() + rule.getCost(),
							newState)) {
				addedChild = true;
				stats.addState();
				GPSNode newNode = new GPSNode(newState, node.getCost()
						+ rule.getCost());
				newNode.setParent(node);
				addNode(newNode);
			}
		}
		if(!addedChild) {
			stats.addLeafNode();
		}
		return true;
	}

	private  boolean checkOpenAndClosed(Integer cost, GPSState state) {
		for (GPSNode openNode : open) {
			if (openNode.getState().compare(state) && openNode.getCost() < cost) {
				return true;
			}
		}
		for (GPSNode closedNode : closed) {
			if (closedNode.getState().compare(state)
					&& closedNode.getCost() < cost) {
				return true;
			}
		}
		return false;
	}

	private  boolean checkBranch(GPSNode parent, GPSState state) {
		if (parent == null) {
			return false;
		}
		return checkBranch(parent.getParent(), state)
				|| state.compare(parent.getState());
	}

	public abstract  void addNode(GPSNode node);
	
	public List<GPSNode> getOpen() {
		return open;
	}
	
}
