package ar.edu.itba.sia.gps.impl;

import ar.edu.itba.sia.App;
import ar.edu.itba.sia.domain.Board;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.gps.api.StatsHolder;
import ar.edu.itba.sia.gps.exception.NotAppliableException;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Random;

public abstract class GPSEngine {

	private HashSet<Board> closedBoards = Sets.newHashSet();

	private GPSProblem problem;

	private StatsHolder stats;

	public GPSEngine(GPSProblem problem) {
		super();
		this.problem = problem;
	}

	private Random r = new Random();

	public boolean engine(GPSProblem myProblem, StatsHolder holder) {
		visitedBoards.clear();
		closedBoards.clear();
		resetOpen();
		this.stats = holder;
		problem = myProblem;

		GPSNode rootNode = new GPSNode(problem.getInitState(), 0);
		stats.addState();
		boolean finished = false;
		boolean failed = false;
		boolean valid = false;

		addNode(rootNode);
		startSim(holder);
		while (!failed && !finished) {
			if (getOpenSize() <= 0) {
				failed = true;
			} else {
				GPSNode currentNode = getNext();
				closedBoards.add(currentNode.getState().getBoard());
				removeNode(currentNode);

				if (problem.getPrintInterval() <= r.nextDouble()) {
					System.out.println("Currently exploring...");
					System.out.println("HValue: " + problem.getHValue(currentNode.getState()));
					System.out.println(currentNode.getState());
				}

				if (isGoal(currentNode) || App.isOver.get()) {
					finished = true;
					stopSim(holder);
					holder.setSolutionDepth(currentNode.getDepth());
					if (isGoal(currentNode)) {
						valid = true;
						System.out.println("Showing a solution");
					} else {
						System.out.println("Task has been cut!");
					}
					System.out.println(currentNode.getSolution());
					App.isOver.set(false);
				} else {
					stats.addExplodedNode();
					explode(currentNode);
				}
			}
		}

		if (finished) {
			if (valid) {
				System.out.println("OK! solution found!");
			}
			stats.setLeafNodes(getOpenSize());
			return true;
		} else if (failed) {
			System.err.println("FAILED! solution not found!");
			return false;
		}
		return false;
	}

	private boolean isGoal(GPSNode currentNode) {
		return currentNode.getState() != null
				&& problem.checkGoalState(currentNode.getState());
	}

	protected boolean explode(GPSNode node) {
		if (problem.getRules() == null) {
			return false;
		}

		for (GPSRule rule : problem.getRules()) {
			GPSState newState = null;
			try {
				newState = rule.evalRule(node.getState());
			} catch (NotAppliableException e) {
				// Do nothing
			}
			if (newState != null
					&& !checkBranch(node, newState)
					&& !checkOpenAndClosed(node.getCost() + rule.getCost(newState),
							newState)) {
				stats.addState();
				GPSNode newNode = new GPSNode(newState, node.getCost()
						+ rule.getCost(newState));
				newNode.setParent(node);
				addNode(newNode);
			}
		}
		return true;
	}

	private boolean checkOpenAndClosed(Integer cost, GPSState state) {

		if (!GPSProblemImpl.checkSymmetry()) {
			return false;
		}

		if (visitedBoards.contains(state.getBoard())) {
			return true;
		}

		for (int i = 1; i <= 3; i++) {
			Board b = state.getBoard().rotateBoard();
			boolean eq = visitedBoards.contains(b);
			if (eq) {
				return true;
			}

		}

		if (closedBoards.contains(state.getBoard())) {
			return true;
		}

		for (int i = 1; i <= 3; i++) {
			Board b = state.getBoard().rotateBoard();
			boolean eq = closedBoards.contains(b);
			if (eq) {
				return true;
			}

		}

		return false;
	}

	private boolean checkBranch(GPSNode parent, GPSState state) {
		if (parent == null) {
			return false;
		}
		return checkBranch(parent.getParent(), state)
				|| state.compare(parent.getState());
	}

	private HashSet<Board> visitedBoards = Sets.newHashSet();

	public void addNode(GPSNode node) {
		node.getState().getBoard().clean();
		visitedBoards.add(node.getState().getBoard());
	}

	protected abstract GPSNode getNext();

	protected abstract void removeNode(GPSNode node);

	protected abstract int getOpenSize();

	public GPSProblem getProblem() {
		return problem;
	}

	protected abstract void resetOpen();

	protected void startSim(StatsHolder holder) {
		holder.startSimulation();
	}

	protected void stopSim(StatsHolder holder) {
		holder.stopSimulation();
	}

}
