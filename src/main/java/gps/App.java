package gps;

import com.google.common.collect.Lists;
import gps.api.Board;
import gps.api.Piece;
import gps.impl.BoardImpl;
import gps.persist.GameXML;
import gps.persist.GameXML.GameNode;
import gps.renderer.BoardRenderer;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class App {

	public static void main(String[] args) throws Exception {
		GameXML game = GameXML.fromXml("generated.xml");
		Map<Point, GameNode> map =  game.nodes;
		List<Piece> pieces = Lists.newArrayList();
		for(Point p: map.keySet()) {
			GameNode node = map.get(p);
            pieces.add(node.toPiece());
		}
        Board board = BoardImpl.withPieces(game.gameSize, game.gameSize, map);
//		GPSProblem problem = new GPSProblemImpl(game.gameSize, game.gameSize, pieces);
        new BoardRenderer(board).render();
//		GPSEngine engine = new DFSEngine(problem, null);
//		engine.engine(problem, null);
	}
	
}
