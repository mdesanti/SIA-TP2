package gps.impl;

import gps.api.Board;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.api.Piece;
import gps.exception.NotAppliableException;

public class GPSRuleImpl implements GPSRule {
	
	private Piece piece;
	private int y;
	private int x;


	public GPSRuleImpl(Piece piece, int y, int x) {
		this.piece = piece;
		this.y = y;
		this.x = x;
	}

	public Integer getCost() {
		return 0;
	}

	public String getName() {
		return "Put " + piece.toString() + " in " + y + " " + x;
	}
	
	private boolean isValidEvalLocation(GPSState state, Board board) {

		
		if (state.getX() == -1 || state.getY() == -1) {
			return x == 0 && y == 0;
		}
		
		if (state.getX() + 1 == board.getWidth() && state.getY() + 1 == board.getWidth()) {
			return false;
		}
		
		if (state.getX() + 1 == board.getWidth()) {
			return state.getY() + 1 == y && x == 0; 
		} else {
			return state.getX() + 1 == x && state.getY() == y;
		}
	}

    public GPSState evalRule(GPSState state) throws NotAppliableException {
		Board board = state.getBoard();
		if(board.containsPiece(piece)) {
			return null;
		}
		if (!isValidEvalLocation(state, board)) {
			return null;
		}
		if(!board.getPieceIn(y, x).isEmpty()) {
			return null;
		}
        if (cannotPutPieceOnBoard(board)) { return null; }

        return new GPSStateImpl(piece,y, x, board.getHeight(), board.getWidth(), state);
		
	}

    private boolean cannotPutPieceOnBoard(Board board) {
        Piece up, right, down, left;
        up = getUpPiece(board);
        right = getRightPiece(board);
        down = getDownPiece(board);
        left = getLeftPiece(board);

        if(up == null) {
            if(piece.getUpColor() != 0) {
                return true;
            }
        } else {
            if(up.getDownColor() != -1 && piece.getUpColor() != up.getDownColor()) {
                return true;
            } else if(up.getDownColor() == -1 && piece.getUpColor() == 0) {
                return true;
            }
        }
        if(right == null) {
            if(piece.getRightColor() != 0) {
                return true;
            }
        } else {
            if(right.getLeftColor() != -1 && piece.getRightColor() != right.getLeftColor()) {
                return true;
            } else if(right.getLeftColor() == -1 && piece.getRightColor() == 0) {
                return true;
            }
        }
        if(left == null) {
            if(piece.getLeftColor() != 0) {
                return true;
            }
        } else {
            if(left.getRightColor() != -1 && piece.getLeftColor() != left.getRightColor()) {
                return true;
            } else if(left.getRightColor() == -1 && piece.getLeftColor() == 0) {
                return true;
            }
        }
        if(down == null) {
            if(piece.getDownColor() != 0) {
                return true;
            }
        } else {
            if(down.getUpColor() != -1 && piece.getDownColor() != down.getUpColor()) {
                return true;
            } else if(down.getUpColor() == -1 && piece.getDownColor() == 0) {
                return true;
            }
        }
        return false;
    }

    private Piece getUpPiece(Board board) {
		if(y-1 < 0) {
			return null;
		}
		return board.getPieceIn(y-1, x);
	}
	
	private Piece getLeftPiece(Board board) {
		if(x-1 < 0) {
			return null;
		}
		return board.getPieceIn(y, x-1);
	}
	
	private Piece getRightPiece(Board board) {
		if(x+1 >= board.getWidth()) {
			return null;
		}
		return board.getPieceIn(y, x+1);
	}
	
	private Piece getDownPiece(Board board) {
		if(y+1 >= board.getHeight()) {
			return null;
		}
		return board.getPieceIn(y+1, x);
	}

}
