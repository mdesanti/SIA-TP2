package gps;

import gps.api.GPSProblem;
import gps.api.Piece;
import gps.impl.DFSEngine;
import gps.impl.GPSEngine;
import gps.impl.GPSProblemImpl;
import gps.persist.GameXML;
import gps.persist.GameXML.GameNode;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class App {

	public static void main(String[] args) throws Exception {
		GameXML game = GameXML.fromXml("generated.xml");
		Map<Point, GameNode> map =  game.nodes;
		List<Piece> pieces = Lists.newArrayList();
		for(Point p: map.keySet()) {
			GameNode node = map.get(p);
            pieces.add(node.toPiece());
		}
//        Board board = BoardImpl.withPieces(game.gameSize, game.gameSize, map);
		GPSProblem problem = new GPSProblemImpl(game.gameSize, game.gameSize, pieces);
		GPSEngine engine = new DFSEngine(problem, null);
		engine.engine(problem, null);
	}
	
}
