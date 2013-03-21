package ar.edu.itba.sia.gps.impl;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.SearchStrategy;
import ar.edu.itba.sia.gps.api.StatsHolder;
import ar.edu.itba.sia.gps.exception.NotAppliableException;

public abstract class GPSEngine {

//	private List<GPSNode> open = new LinkedList<GPSNode>();
//
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

		addNode(rootNode);
		stats.startSimulation();
		while (!failed && !finished) {
			if (getOpenSize() <= 0) {
                failed = true;
            } else {
				GPSNode currentNode = getNext();
				closed.add(currentNode);
				removeNode(currentNode);
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
		for (GPSNode openNode : getOpenNodes()) {
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

	public abstract void addNode(GPSNode node);
	
	protected abstract Iterable<GPSNode> getOpenNodes();
	
	protected abstract GPSNode getNext();
	
	protected abstract void removeNode(GPSNode node);
	
	protected abstract int getOpenSize();
	
	public GPSProblem getProblem() {
		return problem;
	}
	
}
