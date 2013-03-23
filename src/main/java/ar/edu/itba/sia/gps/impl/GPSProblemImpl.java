package ar.edu.itba.sia.gps.impl;

import ar.edu.itba.sia.domain.heuristics.ColorHeuristic;
import com.google.common.collect.Lists;
import ar.edu.itba.sia.domain.Board;
import ar.edu.itba.sia.domain.Piece;
import ar.edu.itba.sia.domain.heuristics.OrderHeuristic;
import ar.edu.itba.sia.gps.api.*;

import java.util.List;

public class GPSProblemImpl implements GPSProblem {
	private int height, width;
	private List<Piece> all = Lists.newArrayList();
	private List<GPSRule> rules = Lists.newArrayList();
	private GPSState initState;
	private static int id = 0;
	private Heuristic manhattan = new ColorHeuristic();

	public GPSProblemImpl(int height, int width, List<Piece> allPieces, int colorCount) {
		this.height = height;
		this.width = width;
		all.addAll(allPieces);
		generateRules();
		this.initState = GPSStateImpl.initialState(height, width, all, colorCount);
	}

	private void generateRules() {
		for(Piece piece: all) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					rules.add(new GPSRuleImpl(piece, j, i));

                    Piece rotated = piece.rotate(1);
                    for (int k = 0; k < 3; k++) {
                        rules.add(new GPSRuleImpl(rotated, j ,i));
                        rotated = rotated.rotate(1);
                    }
				}
			}
		}
	}

	public GPSState getInitState() {
		return initState;
	}

	public List<GPSRule> getRules() {
		return rules;
	}

	public Integer getHValue(GPSState state) {
		return manhattan.apply(state);
	}

	public boolean checkGoalState(GPSState state) {
		Board board = state.getBoard();
        return board.getPieceCount() == board.getWidth() * board.getHeight() && state.getBoard().isValid();
    }
	
	public static int nextId() {
		return id++;
	}

}
