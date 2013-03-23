package ar.edu.itba.sia;

import ar.edu.itba.sia.domain.Board;
import ar.edu.itba.sia.domain.BoardImpl;
import ar.edu.itba.sia.domain.Piece;
import ar.edu.itba.sia.domain.StatsHolderImpl;
import ar.edu.itba.sia.domain.heuristics.CenterHeuristic;
import ar.edu.itba.sia.domain.heuristics.OrderHeuristic;
import ar.edu.itba.sia.domain.persist.GameXML;
import ar.edu.itba.sia.domain.persist.GameXML.GameNode;
import ar.edu.itba.sia.domain.renderer.BoardRenderer;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.api.StatsHolder;
import ar.edu.itba.sia.gps.impl.GPSEngine;
import ar.edu.itba.sia.gps.impl.GPSProblemImpl;
import com.google.common.collect.Lists;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import static ar.edu.itba.sia.CLIParser.getAppConfig;

public class App {

    public static AtomicBoolean isOver = new AtomicBoolean(false);

    public static void main(String[] args) throws Exception {
        final AppConfig config;

        config = getAppConfig(args);

        if (config == null) {
            return;
        }

        shutDownHook();

        GameXML game = GameXML.fromXml("random.4.xml");
        Map<Point, GameNode> map =  game.nodes;
		List<Piece> pieces = Lists.newArrayList();
        List<Point> points = Lists.newArrayList();
        List<Heuristic> heuristics = Lists.newArrayList();
        heuristics.add(new CenterHeuristic());
        heuristics.add(new OrderHeuristic());
//        heuristics.add(new ColorHeuristic());
        points.addAll(map.keySet());
        Collections.reverse(points);
		for(Point p: points) {
			GameNode node = map.get(p);
            pieces.add(node.toPiece());
		}
//        sufflePieces(pieces);
        System.in.read();
        Board board = BoardImpl.withPieces(game.gameSize, game.gameSize, map);
        System.out.println("Showing the start level...");
        new BoardRenderer(board).render();
        final StatsHolder holder = new StatsHolderImpl();
		GPSProblem problem = new GPSProblemImpl(game.gameSize, game.gameSize, pieces, game.numberOfColors, config);
        GPSEngine engine = config.getEngine(problem);
		engine.engine(problem, holder);
		System.out.println("-------------------------------------------");
		System.out.println("Simulation time: " + holder.getSimulationTime()/(double)1000 + " seconds");
		System.out.println("Exploded nodes: " + holder.getExplodedNodes());
		System.out.println("Leaf nodes: " + holder.getLeafNodesNumber());
		System.out.println("Solution depth: " + holder.getSolutionDepth());
		System.out.println("Generated states: " + holder.getStatesNumber());
    }




    private static void shutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                if (App.isOver.get()) {
                    return;
                }
                App.isOver.set(true);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                        e.printStackTrace();
                }
            }
        });
    }

    private static void sufflePieces(List<Piece> pieces) {
        Random random = new Random();
		for (int i = 0; i < 50000; i++) {
			Piece auxA = null;
			int a = random.nextInt(pieces.size());
			auxA = pieces.get(a);
			pieces.remove(a);
			pieces.add(0, auxA);
		}
	}
	
}
