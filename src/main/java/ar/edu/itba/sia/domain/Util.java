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
}
