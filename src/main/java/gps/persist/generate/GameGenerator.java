package gps.persist.generate;

import gps.persist.GameXML;

import java.awt.Point;
import java.util.HashMap;

public class GameGenerator {
	public static void main(final String[] args) throws Exception {
		GameXML xml = new GameXML();
		xml.gameSize = 3;
		xml.numberOfColors = 1;
		xml.nodes = new HashMap<Point, GameXML.GameNode>();
		/***

		 * -------------------
		 * |  0  |  0  |  0  |
		 * | 0 1 | 1 1 | 1 0 |
		 * |  1  |  1  |  1  |
		 * ------------------|
		 * |  1  |  1  |  1  |
		 * | 0 1 | 1 1 | 1 0 |
		 * |  1  |  1  |  1  |
		 * ------------------|
		 * |  1  |  1  |  1  |
		 * | 0 1 | 1 1 | 1 0 |
		 * |  0  |  0  |  0  |
		 * -------------------
		 */

		xml.nodes.put(new Point(0, 0), new GameXML.GameNode(0, 1, 0, 1));
		xml.nodes.put(new Point(0, 1), new GameXML.GameNode(0, 1, 1, 1));
		xml.nodes.put(new Point(0, 2), new GameXML.GameNode(0, 1, 1, 0));
		xml.nodes.put(new Point(1, 0), new GameXML.GameNode(1, 1, 0, 0));
		xml.nodes.put(new Point(1, 1), new GameXML.GameNode(1, 1, 1, 1));
		xml.nodes.put(new Point(1, 2), new GameXML.GameNode(1, 1, 1, 0));
		xml.nodes.put(new Point(2, 0), new GameXML.GameNode(1, 0, 0, 0));
		xml.nodes.put(new Point(2, 1), new GameXML.GameNode(1, 0, 1, 1));
		xml.nodes.put(new Point(2, 2), new GameXML.GameNode(1, 0, 1, 0));
		xml.toFile("generated.xml");
	}
}
