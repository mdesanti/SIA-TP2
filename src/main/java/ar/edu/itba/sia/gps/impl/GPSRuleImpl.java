package ar.edu.itba.sia.gps.impl;

import ar.edu.itba.sia.domain.Board;
import ar.edu.itba.sia.gps.api.GPSRule;
import ar.edu.itba.sia.gps.api.GPSState;
import ar.edu.itba.sia.domain.Piece;
import ar.edu.itba.sia.gps.exception.NotAppliableException;

import java.awt.*;

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
    	Point pieceLocation = state.getBoard().getPieceLocation();
    	
        if (pieceLocation == null) {
            return x == 0 && y == 0;
        }

        if (pieceLocation.x + 1 == board.getWidth() 
        		&& pieceLocation.y + 1 == board.getHeight()) {
            return false;
        }

        if (pieceLocation.x + 1 == board.getWidth()) {
            return pieceLocation.y + 1 == y && x == 0;
        } else {
            return pieceLocation.x + 1 == x && pieceLocation.y == y;
        }
    }

    private boolean isValidColorCount(Board board) {
        if (piece.getDownColor() != 0 &&
                board.getColorCountFor(Board.Direction.UP, piece.getDownColor()) < 0) {
            return false;
        }
        if (piece.getUpColor() != 0 &&
                board.getColorCountFor(Board.Direction.DOWN, piece.getUpColor()) < 0) {
            return false;
        }
        if (piece.getRightColor() != 0 &&
                board.getColorCountFor(Board.Direction.LEFT, piece.getRightColor()) < 0) {
            return false;
        }
        if (piece.getLeftColor() != 0 &&
                board.getColorCountFor(Board.Direction.RIGHT, piece.getLeftColor()) < 0) {
            return false;
        }
        return true;
    }

    public GPSState evalRule(GPSState state) throws NotAppliableException {
        Board board = state.getBoard();

        if (!isValidEvalLocation(state, board)) {
            return null;
        }

        if (cannotPutPieceOnBoard(board)) {
            return null;
        }

        if (!isValidColorCount(board)) {
            return null;
        }

        if (board.containsPiece(piece)) {
            return null;
        }

        if (!board.getPieceIn(x, y).isEmpty()) {
            return null;
        }

        return GPSStateImpl.fromParent(state, new Point(x, y), piece);
    }

    private boolean cannotPutPieceOnBoard(Board board) {
        Piece up, right, down, left;
        up = getUpPiece(board);
        right = getRightPiece(board);
        down = getDownPiece(board);
        left = getLeftPiece(board);

        if (up == null) {
            if (piece.getUpColor() != 0) {
                return true;
            }
        } else {
            if (up.getDownColor() != -1 && piece.getUpColor() != up.getDownColor()) {
                return true;
            } else if (up.getDownColor() == -1 && piece.getUpColor() == 0) {
                return true;
            }
        }
        if (right == null) {
            if (piece.getRightColor() != 0) {
                return true;
            }
        } else {
            if (right.getLeftColor() != -1 && piece.getRightColor() != right.getLeftColor()) {
                return true;
            } else if (right.getLeftColor() == -1 && piece.getRightColor() == 0) {
                return true;
            }
        }
        if (left == null) {
            if (piece.getLeftColor() != 0) {
                return true;
            }
        } else {
            if (left.getRightColor() != -1 && piece.getLeftColor() != left.getRightColor()) {
                return true;
            } else if (left.getRightColor() == -1 && piece.getLeftColor() == 0) {
                return true;
            }
        }
        if (down == null) {
            if (piece.getDownColor() != 0) {
                return true;
            }
        } else {
            if (down.getUpColor() != -1 && piece.getDownColor() != down.getUpColor()) {
                return true;
            } else if (down.getUpColor() == -1 && piece.getDownColor() == 0) {
                return true;
            }
        }
        return false;
    }

    private Piece getUpPiece(Board board) {
        if (y - 1 < 0) {
            return null;
        }
        return board.getPieceIn(x, y - 1);
    }

    private Piece getLeftPiece(Board board) {
        if (x - 1 < 0) {
            return null;
        }
        return board.getPieceIn(x - 1, y);
    }

    private Piece getRightPiece(Board board) {
        if (x + 1 >= board.getWidth()) {
            return null;
        }
        return board.getPieceIn(x + 1, y);
    }

    private Piece getDownPiece(Board board) {
        if (y + 1 >= board.getHeight()) {
            return null;
        }
        return board.getPieceIn(x, y + 1);
    }

}
