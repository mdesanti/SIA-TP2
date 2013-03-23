package ar.edu.itba.sia;

import java.awt.Point;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import ar.edu.itba.sia.domain.Board;
import ar.edu.itba.sia.domain.BoardImpl;
import ar.edu.itba.sia.domain.Piece;
import ar.edu.itba.sia.domain.StatsHolderImpl;
import ar.edu.itba.sia.domain.costFunctions.DummyCostFunction;
import ar.edu.itba.sia.domain.heuristics.CenterHeuristic;
import ar.edu.itba.sia.domain.persist.GameXML;
import ar.edu.itba.sia.domain.persist.GameXML.GameNode;
import ar.edu.itba.sia.domain.renderer.BoardRenderer;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.api.StatsHolder;
import ar.edu.itba.sia.gps.impl.DFSEngine;
import ar.edu.itba.sia.gps.impl.GPSEngine;
import ar.edu.itba.sia.gps.impl.GPSProblemImpl;

import com.google.common.collect.Lists;

public class App {

    @SuppressWarnings("static-access")
    private static Options getInputOptions() {
        final Options opts = new Options();


        final Option shownodes = OptionBuilder
                .withLongOpt("shownodes")
                .hasArg()
                .withDescription(
                        "The percentage of evaluated nodes to show, accepted value: a double from 0 to 1")
                .create("shownodes");

        final Option cachedepth = OptionBuilder
                .withLongOpt("cachedepth")
                .hasArg()
                .withDescription(
                        "The depth of the cache size for accesing board elements, a low value has a negative impact on CPU but uses few RAM, but the higher the value is, the more RAM it consumes")
                .create("cachedepth");

        final Option method = OptionBuilder
                .withLongOpt("method")
                .hasArg()
                .withDescription(
                        "The method to explore, options are: ID, DFS, BFS, AStar, Greedy")
                .create("method");

        final Option heuristic = OptionBuilder.withLongOpt("heuristic")
                .withDescription("The heuristic to use (only with AStar/Greedy methods), accepted values are: center, order")
                .hasArg().create("heuristic");

        final Option filename = OptionBuilder
                .withLongOpt("filename")
                .hasArg()
                .withDescription(
                        "The filename of the board to load, must be a valid XML")
                .create("filename");


        final Option boardsize = OptionBuilder
                .withLongOpt("boardsize")
                .hasArg()
                .withDescription(
                        "If no filename is passed, generates a random board with the size given.")
                .create("boardsize");

        final Option help = OptionBuilder.withLongOpt("help")
                .withDescription("Shows this help").create("help");

        opts.addOption(help);

        opts.addOption(method);
        opts.addOption(heuristic);
        opts.addOption(filename);
        opts.addOption(shownodes);
        opts.addOption(cachedepth);
        opts.addOption(boardsize);

        return opts;
    }


    public static AtomicBoolean isOver = new AtomicBoolean(false);

    public static void main(String[] args) throws Exception {
        shutDownHook();

        GameXML game = GameXML.fromXml("random.3.xml");
        Map<Point, GameNode> map =  game.nodes;
		List<Piece> pieces = Lists.newArrayList();
        List<Point> points = Lists.newArrayList();
        List<Heuristic> heuristics = Lists.newArrayList();
        heuristics.add(new CenterHeuristic());
//        heuristics.add(new OrderHeuristic());
//        heuristics.add(new ColorHeuristic());
        points.addAll(map.keySet());
        Collections.reverse(points);
		for(Point p: points) {
			GameNode node = map.get(p);
            pieces.add(node.toPiece());
		}
        sufflePieces(pieces);
        System.in.read();
        Board board = BoardImpl.withPieces(game.gameSize, game.gameSize, map);
        System.out.println("Showing the start level...");
        new BoardRenderer(board).render();
        final StatsHolder holder = new StatsHolderImpl();
		GPSProblem problem = new GPSProblemImpl(game.gameSize, game.gameSize, pieces, game.numberOfColors, heuristics, new DummyCostFunction());
		GPSEngine engine = new DFSEngine(problem, null);
		engine.engine(problem, null, holder);
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
