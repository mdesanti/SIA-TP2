package gps.persist.generate;

import gps.persist.GameXML;

import java.awt.*;
import java.util.HashMap;

public class GameGenerator {
	public static void main(final String[] args) throws Exception {
		GameXML xml = new GameXML();
		xml.gameSize = 4;
		xml.numberOfColors = 6;
		xml.nodes = new HashMap<Point, GameXML.GameNode>();
		/***

		 * -------------------------
		 * |  0  |  0  |  0  |  0  |
		 * | 0 2 | 2 3 | 3 4 | 4 0 |
		 * |  1  |  1  |  1  |  6  |
		 * ------------------|------
		 * |  1  |  1  |  1  |  6  |
		 * | 0 1 | 1 1 | 1 2 | 2 0 |
		 * |  1  |  1  |  1  |  3  |
		 * ------------------|------
		 * |  1  |  1  |  1  |  3  |
		 * | 0 5 | 5 4 | 4 2 | 2 0 |
		 * |  1  |  2  |  3  |  4  |
		 * -------------------------
		 * |  1  |  2  |  3  |  4  |
		 * | 0 4 | 4 3 | 3 2 | 2 1 |
		 * |  0  |  0  |  0  |  0
		 * -------------------------
		 */

        xml.nodes.put(new Point(0, 0), new GameXML.GameNode(0, 2, 1, 0));
        xml.nodes.put(new Point(1, 0), new GameXML.GameNode(0, 3, 1, 2));
        xml.nodes.put(new Point(2, 0), new GameXML.GameNode(0, 4, 1, 3));
        xml.nodes.put(new Point(3, 0), new GameXML.GameNode(0, 0, 6, 4));
        xml.nodes.put(new Point(0, 1), new GameXML.GameNode(1, 1, 1, 0));
        xml.nodes.put(new Point(1, 1), new GameXML.GameNode(1, 1, 1, 1));
        xml.nodes.put(new Point(2, 1), new GameXML.GameNode(1, 2, 1, 1));
        xml.nodes.put(new Point(3, 1), new GameXML.GameNode(6, 0, 3, 2));
        xml.nodes.put(new Point(0, 2), new GameXML.GameNode(1, 5, 1, 0));
        xml.nodes.put(new Point(1, 2), new GameXML.GameNode(1, 4, 2, 5));
        xml.nodes.put(new Point(2, 2), new GameXML.GameNode(1, 2, 3, 4));
        xml.nodes.put(new Point(3, 2), new GameXML.GameNode(3, 0, 4, 2));
        xml.nodes.put(new Point(0, 3), new GameXML.GameNode(1, 4, 0, 0));
        xml.nodes.put(new Point(1, 3), new GameXML.GameNode(2, 3, 0, 4));
        xml.nodes.put(new Point(2, 3), new GameXML.GameNode(3, 2, 0, 3));
        xml.nodes.put(new Point(3, 3), new GameXML.GameNode(4, 0, 0, 2));
        xml.toFile("generated.xml");
	}
}
