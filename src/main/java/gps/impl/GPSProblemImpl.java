package gps.impl;

import com.google.common.collect.Lists;
import gps.api.*;

import java.util.List;

public class GPSProblemImpl implements GPSProblem {
	private int height, width;
	private List<Piece> all = Lists.newArrayList();
	private List<GPSRule> rules = Lists.newArrayList();
	private GPSState initState;
	private static int id = 0;

	public GPSProblemImpl(int height, int width, List<Piece> allPieces) {
		this.height = height;
		this.width = width;
		all.addAll(allPieces);
		generateRules();
		this.initState = GPSStateImpl.initialState(height, width, all);
	}

	private void generateRules() {
		for(Piece piece: all) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					rules.add(new GPSRuleImpl(piece, j, i));
					Piece rotated = piece.rotate();
					for (int k = 0; k < 3; k++) {
						rules.add(new GPSRuleImpl(rotated, j ,i));
						rotated = rotated.rotate();
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
		// TODO Auto-generated method stub
		return null;
	}

	public boolean checkGoalState(GPSState state) {
		Board board = state.getBoard();
        return board.getPieceCount() == board.getWidth() * board.getHeight();
    }
	
	public static int nextId() {
		return id++;
	}

}
