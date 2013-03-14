package gps;

import gps.api.GPSProblem;
import gps.api.Piece;
import gps.impl.GPSEngine;
import gps.impl.GPSProblemImpl;
import gps.impl.PieceImpl;
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
			Piece piece = new PieceImpl(node.up, node.right, node.down, node.left);
			pieces.add(piece);
		}
		GPSProblem problem = new GPSProblemImpl(game.gameSize, game.gameSize, pieces);
	}
	
}
