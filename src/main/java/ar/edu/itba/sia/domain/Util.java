package ar.edu.itba.sia.domain;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: cris
 * Date: 20/03/13
 * Time: 18:22
 */
public class Util {
    public static Point rotate(Point p, int times, int N) {
        return rotate(p.x, p.y, times, N);
    }

    public static Point rotate(int x, int y, int times, int N) {
        times = times % 4;
        if (times == 0) {
            return new Point(x, y);
        }
        return rotate(y, N - 1 - x, times - 1, N);
    }

    public static boolean canPutPieceOnBoard(Piece piece, Board board, int x, int y) {
    	if(piece.isEmpty())
    		return true;
        Piece up, right, down, left;
        up = getUpPiece(board, x, y);
        right = getRightPiece(board, x, y);
        down = getDownPiece(board, x, y);
        left = getLeftPiece(board, x, y);

        if (up == null) {
            if (piece.getUpColor() != 0) {
                return false;
            }
        } else {
            if (up.getDownColor() != -1 && piece.getUpColor() != up.getDownColor()) {
                return false;
            } else if (up.getDownColor() == -1 && piece.getUpColor() == 0) {
                return false;
            }
        }
        if (right == null) {
            if (piece.getRightColor() != 0) {
                return false;
            }
        } else {
            if (right.getLeftColor() != -1 && piece.getRightColor() != right.getLeftColor()) {
                return false;
            } else if (right.getLeftColor() == -1 && piece.getRightColor() == 0) {
                return false;
            }
        }
        if (left == null) {
            if (piece.getLeftColor() != 0) {
                return false;
            }
        } else {
            if (left.getRightColor() != -1 && piece.getLeftColor() != left.getRightColor()) {
                return false;
            } else if (left.getRightColor() == -1 && piece.getLeftColor() == 0) {
                return false;
            }
        }
        if (down == null) {
            if (piece.getDownColor() != 0) {
                return false;
            }
        } else {
            if (down.getUpColor() != -1 && piece.getDownColor() != down.getUpColor()) {
                return false;
            } else if (down.getUpColor() == -1 && piece.getDownColor() == 0) {
                return false;
            }
        }
        return true;
    }

    private static Piece getUpPiece(Board board, int x, int y) {
        if (y - 1 < 0) {
            return null;
        }
        return board.getPieceIn(x, y - 1);
    }

    private static Piece getLeftPiece(Board board, int x, int y) {
        if (x - 1 < 0) {
            return null;
        }
        return board.getPieceIn(x - 1, y);
    }

    private static Piece getRightPiece(Board board, int x, int y) {
        if (x + 1 >= board.getWidth()) {
            return null;
        }
        return board.getPieceIn(x + 1, y);
    }

    private static Piece getDownPiece(Board board, int x, int y) {
        if (y + 1 >= board.getHeight()) {
            return null;
        }
        return board.getPieceIn(x, y + 1);
    }
}
