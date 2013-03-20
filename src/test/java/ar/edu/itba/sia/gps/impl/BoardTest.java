package ar.edu.itba.sia.gps.impl;

import ar.edu.itba.sia.domain.Board;
import ar.edu.itba.sia.domain.BoardImpl;
import ar.edu.itba.sia.domain.Piece;
import ar.edu.itba.sia.gps.persist.GameXML;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.awt.*;
import java.util.Collections;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cris
 * Date: 20/03/13
 * Time: 19:48
 */
public class BoardTest {
    @Test
    public void testRotation() throws Exception {
        GameXML game = GameXML.fromXml("test.xml");
        Map<Point, GameXML.GameNode> map =  game.nodes;
        java.util.List<Piece> pieces = Lists.newArrayList();
        java.util.List<Point> points = Lists.newArrayList();
        points.addAll(map.keySet());
        Collections.reverse(points);
        for(Point p: points) {
            GameXML.GameNode node = map.get(p);
            pieces.add(node.toPiece());
        }

        Board board = BoardImpl.withPieces(game.gameSize, game.gameSize, map);
        board.rotateBoard();
    }
}
