package gps.impl;

import gps.api.Board;
import gps.api.GPSState;
import gps.api.Piece;
import gps.persist.GameXML;

import java.awt.Point;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class BoardImpl implements Board {

	private Map<Point, Piece> board = Maps.newHashMap();
	private Set<Piece> pieces = Sets.newHashSet();
	private Map<Piece, Boolean> hasPieceCache = Maps.newHashMap();
	
	private int height;
	private int width;
	private GPSState state;
	private Board parent;
	
    private Map<BoardImpl.Direction, short[]> availableColors = Maps.newHashMapWithExpectedSize(4);
	private Point pieceLocation;
	private Piece piece;
    
    private BoardImpl() {}
    
    public static Board initialBoard(int height, int width, GPSState state, Collection<Piece> piecesInProblem) {
    	BoardImpl board = new BoardImpl();
    	board.height = height;
    	board.width = width;
    	board.state = state;    	
    	board.buildColorCountMap(piecesInProblem);
    	return board;
    }
    
    public static Board fromParent(GPSState state,
			Point pieceLocation, Piece toAdd) {
    	BoardImpl board = new BoardImpl();
    	board.height = state.getParent().getBoard().getHeight();
    	board.width = state.getParent().getBoard().getWidth();
    	board.state = state;
    	board.pieceLocation = pieceLocation;
    	board.piece = toAdd;
    	board.decrementColorCount(toAdd, state.getParent().getBoard());
		return board;
	}
    


	private void buildColorCountMap(Collection<Piece> pieces) {
        getAvailableColors().put(Board.Direction.UP, new short[this.width]);
        getAvailableColors().put(Board.Direction.DOWN, new short[this.width]);
        getAvailableColors().put(Board.Direction.LEFT, new short[this.width]);
        getAvailableColors().put(Board.Direction.RIGHT, new short[this.width]);
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

	public Piece getPieceIn(int y, int x) {
		Point p = new Point(x, y);
		if (board.get(p) == null) {
			if (state == null) {
				board.put(p, PieceImpl.empty());
			} else {
				Piece piece = state.getPieceIn(p);
				board.put(p, piece);
			}
		}
		return board.get(p);
	}

	private int generateCheckSum() {
		Integer checksum = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Piece piece = this.getPieceIn(i, j);
				if (piece == null) {
					checksum += -4;
				} else {
					checksum += piece.getDownColor();
					checksum += piece.getLeftColor();
					checksum += piece.getRightColor();
					checksum += piece.getUpColor();
				}
			}
		}
		return checksum;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getPieceCount() {
		int count = 0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(!this.getPieceIn(i, j).isEmpty()) {
					count++;
				}
			}
		}
		return count;
	}



	public void setPieceIn(int y, int x, Piece piece) {
		board.put(new Point(x, y), piece);
	}

	@Override
	public boolean equals(Object obj) {
		Board board2 = (Board) obj;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (!this.getPieceIn(i, j).equalsNoId(board2.getPieceIn(i, j))) {
					return false;
				}
			}
		}
		return true;
	}

	public static Board withPieces(int width, int height,
			Map<Point, GameXML.GameNode> map) {
		BoardImpl b = new BoardImpl(); 
		b.width = width;
		b.height = height;
		for (Point point : map.keySet()) {
			b.board.put(new Point(point.y, point.x), map.get(point).toPiece());
		}
		return b;
	}

	public boolean containsPiece(Piece piece) {
		if (hasPieceCache.containsKey(piece)) {
			return hasPieceCache.get(piece);
		}
		boolean result;
		if (pieces.contains(piece)) {
			result = true;			
		} else {
			if (parent == null) {
				result = false;
			} else {
				result = parent.containsPiece(piece);
			}
		}
		hasPieceCache.put(piece, result);
		return result;
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
			board.put(point, p);
		}
		return p;
	}

	public Set<Piece> getPieces() {
		return pieces;
	}

	public Map<BoardImpl.Direction, short[]> getAvailableColors() {
		return availableColors;
	}

	@Override
	public Point getPieceLocation() {
		return pieceLocation;
	}

	
}
