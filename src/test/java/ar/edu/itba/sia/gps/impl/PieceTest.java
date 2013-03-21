package ar.edu.itba.sia.gps.impl;

import ar.edu.itba.sia.domain.Piece;
import ar.edu.itba.sia.domain.PieceImpl;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: cris
 * Date: 20/03/13
 * Time: 19:02
 */
public class PieceTest {
    @Test
    public void testRotation() {
        Piece p = new PieceImpl(1,2,3,4);
        assertTrue(p.rotate(1).hasSameColors(new PieceImpl(4,1,2,3)));
        assertTrue(p.rotate(2).hasSameColors(new PieceImpl(3,4,1,2)));
        assertTrue(p.rotate(3).hasSameColors(new PieceImpl(2,3,4,1)));
        assertTrue(p.rotate(4).hasSameColors(p));
    }
}
