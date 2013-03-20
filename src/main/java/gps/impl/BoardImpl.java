package gps.impl;

import com.google.common.collect.Maps;
import gps.api.Board;
import gps.api.GPSState;
import gps.api.Piece;
import gps.persist.GameXML;

import java.awt.*;
import java.util.Collection;
import java.util.Map;

public class BoardImpl implements Board {

	private Map<Point, Piece> board = Maps.newHashMap();
    private Map<Piece, Boolean> pieceCache = Maps.newHashMap();

    private static double cacheFactor = 0.33;

	private int height;
	private int width;
	private GPSState state;
	private Board parent;
	
    private Map<BoardImpl.Direction, short[]> availableColors = Maps.newHashMapWithExpectedSize(4);
	private Point pieceLocation;
	private Piece piece;
    private int depth;
    private int colorCount;

    private BoardImpl() {}
    
    public static Board initialBoard(int height, int width, GPSState state, Collection<Piece> piecesInProblem, int colorCount) {
    	BoardImpl board = new BoardImpl();
    	board.height = height;
    	board.width = width;
    	board.state = state;    	
    	board.depth = 0;
        board.colorCount = colorCount;
        board.buildColorCountMap(piecesInProblem);
        return board;
    }
    
    public static Board fromParent(GPSState state,
			Point pieceLocation, Piece toAdd) {
    	BoardImpl board = new BoardImpl();
    	board.height = state.getParent().getBoard().getHeight();
    	board.width = state.getParent().getBoard().getWidth();
    	board.state = state;
        board.colorCount = state.getParent().getBoard().getColorCount();
    	board.pieceLocation = pieceLocation;
    	board.piece = toAdd;
        board.depth = state.getParent().getBoard().getDepth() + 1;
        board.parent = state.getParent().getBoard();
    	board.decrementColorCount(toAdd, state.getParent().getBoard());
        board.setPieceIn(pieceLocation.x, pieceLocation.y, toAdd);
		return board;
	}
    


	private void buildColorCountMap(Collection<Piece> pieces) {
        getAvailableColors().put(Board.Direction.UP, new short[this.colorCount]);
        getAvailableColors().put(Board.Direction.DOWN, new short[this.colorCount]);
        getAvailableColors().put(Board.Direction.LEFT, new short[this.colorCount]);
        getAvailableColors().put(Board.Direction.RIGHT, new short[this.colorCount]);
        short[] up, down, left, right;
        up    = getAvailableColors().get(Board.Direction.UP);
        down  = getAvailableColors().get(Board.Direction.DOWN);
        left  = getAvailableColors().get(Board.Direction.LEFT);
        right = getAvailableColors().get(Board.Direction.RIGHT);
        for(Piece p : pieces) {
            if (p.getUpColor() > 0) {
                up[p.getUpColor() - 1]++;
            }
            if (p.getDownColor() > 0) {
                down[p.getDownColor() - 1]++;
            }
            if (p.getLeftColor() > 0) {
                left[p.getLeftColor() - 1]++;
            }
            if (p.getRightColor() > 0) {
                right[p.getRightColor() - 1]++;
            }
        }
    }

    private void decrementColorCount(Piece addedPiece, Board parent) {
        for(Board.Direction d : parent.getAvailableColors().keySet()) {
            getAvailableColors().put(d, parent.getAvailableColors().get(d).clone());
        }

        if (addedPiece.getDownColor() > 0) {
            short[] downCount = getAvailableColors().get(Board.Direction.DOWN);
            downCount[addedPiece.getDownColor() - 1]--;
        }
        if (addedPiece.getUpColor() > 0) {
            short[] upCount = getAvailableColors().get(Board.Direction.UP);
            upCount[addedPiece.getUpColor() - 1]--;
        }
        if (addedPiece.getLeftColor() > 0) {
            short[] leftCount = getAvailableColors().get(Board.Direction.LEFT);
            leftCount[addedPiece.getLeftColor() - 1]--;
        }
        if (addedPiece.getRightColor() > 0) {
            short[] rightCount = getAvailableColors().get(Board.Direction.RIGHT);
            rightCount[addedPiece.getRightColor() - 1]--;
        }
    }



	public Board rotateBoard() {
		// Board rotated = new BoardImpl(height, width);
		// int ii = 0;
		// int jj = 0;
		// for (int i = 0; i < width; i++) {
		// for (int j = height - 1; j >= 0; j--) {
		// rotated.setPieceIn(ii, jj, this.getPieceIn(j, i).rotate());
		// jj++;
		// }
		// ii++;
		// jj = 0;
		// }
		// return rotated;
		return null;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getPieceCount() {
        return depth;
	}



	public void setPieceIn(int x, int y, Piece piece) {
		board.put(new Point(x, y), piece);
	}

	@Override
	public boolean equals(Object obj) {
		BoardImpl board2 = (BoardImpl) obj;
		if (board2.depth == this.depth) {
            return board2.board.equals(this.board);
        }  else {
            return false;
        }
	}

	public static Board withPieces(int width, int height,
			Map<Point, GameXML.GameNode> map) {
		BoardImpl b = new BoardImpl(); 
		b.width = width;
		b.height = height;
		for (Point point : map.keySet()) {
			b.board.put(new Point(point.x, point.y), map.get(point).toPiece());
		}
		return b;
	}

    private boolean cacheableBoard() {
        return depth <= 12;

    }

	public boolean containsPiece(Piece piece) {
        boolean result;
        if (piece != null && cacheableBoard() && (pieceCache.containsKey(piece))) {
            return pieceCache.get(piece);
        }

		if (this.piece != null && this.piece.hasSameIdWith(piece)) {
            result = true;
		} else {
			if (parent == null) {
				result = false;
			} else {
				result = parent.containsPiece(piece);
			}
            if (cacheableBoard()) {
                pieceCache.put(piece, result);
            }
		}
        return result;
	}

    @Override
    public int getDepth() {
        return depth;
    }

    @Override
    public int getColorCount() {
        return colorCount;
    }

    @Override
    public int getColorCountFor(Direction up, int color) {
        return availableColors.get(up)[color - 1];
    }

    @Override
    public Piece getPiece() {
        return piece;
    }

    @Override
	public int getChecksum() {
		return 0;
	}

	@Override
	public Piece getPieceIn(Point point) {
		Piece p = board.get(point);
		if (p == null) {
			if (parent != null) {
				p = parent.getPieceIn(point);
			} else {
				p = PieceImpl.empty();
			}
            if (cacheableBoard()) {
			    board.put(point, p);
            }
		}
		return p;
	}

    public Piece getPieceIn(int x, int y) {
        return getPieceIn(new Point(x, y));
    }



	public Map<BoardImpl.Direction, short[]> getAvailableColors() {
		return availableColors;
	}

	@Override
	public Point getPieceLocation() {
		return pieceLocation;
	}

	
}
