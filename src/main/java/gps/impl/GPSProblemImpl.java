package gps.impl;

import gps.api.Board;
import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.api.Piece;

import java.util.List;

import com.google.common.collect.Lists;

public class GPSProblemImpl implements GPSProblem {

	private int height, width;
	private List<Piece> all = Lists.newArrayList();
	private List<GPSRule> rules = Lists.newArrayList();

	public GPSProblemImpl(int height, int width, List<Piece> allPieces) {
		this.height = height;
		this.width = width;
		all.addAll(allPieces);
		generateRules();
	}

	private void generateRules() {
		for(Piece piece: all) {
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width; j++) {
					rules.add(new GPSRuleImpl(piece, i, j));
				}
			}
		}
	}

	public GPSState getInitState() {
		return new GPSStateImpl(new BoardImpl(height, width));
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
		for (int i = 0; i < board.getHeight(); i++) {
			for(int j = 0; j < board.getWidth(); j++) {
				if(board.getPieceIn(i, j).isEmtpy()) {
					return false;
				}
			}
		}
		return true;
	}

}
