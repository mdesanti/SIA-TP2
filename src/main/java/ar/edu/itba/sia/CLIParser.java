package ar.edu.itba.sia;

import org.apache.commons.cli.*;

import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: cris
 * Date: 23/03/13
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 */
public class CLIParser {
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
                .withDescription("The heuristic to use (only with AStar/Greedy methods), accepted values are: center, order, color")
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

        final Option costfunction = OptionBuilder
                .withLongOpt("costfunction")
                .hasArg()
                .withDescription(
                        "The percentage of evaluated nodes to show, accepted value: a double from 0 to 1")
                .create("costfunction");

        final Option timeoutseconds = OptionBuilder
                .withLongOpt("timeoutseconds")
                .hasArg()
                .withDescription(
                        "The percentage of evaluated nodes to show, accepted value: a double from 0 to 1")
                .create("timeoutseconds");

        opts.addOption(help);
        opts.addOption(method);
        opts.addOption(heuristic);
        opts.addOption(filename);
        opts.addOption(shownodes);
        opts.addOption(cachedepth);
        opts.addOption(boardsize);
        opts.addOption(costfunction);
        opts.addOption(timeoutseconds);

        return opts;
    }


    private static AppConfig parseOptions(final Options opts,
                                          final CommandLine line) throws FileNotFoundException,
            ParseException {

        if (line.hasOption("help")) {
            return null;
        } else {
            AppConfig config = new AppConfig();
            if (!line.hasOption("filename") && !line.hasOption("boardSize")) {
                return null;
            } else {
                if (line.hasOption("filename")) {
                    config.setFilePath(line.getOptionValue("filename"));
                } else {
                    try {
                        config.setBoardSize(Integer.parseInt(line.getOptionValue("boardSize")));
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
            }

            try {
                config.setCachedepth(Integer.parseInt(line.getOptionValue("cachedepth", "12")));
            } catch (NumberFormatException e) {
                return null;
            }

            try {
                config.setTimeoutseconds(Integer.parseInt(line.getOptionValue("timeoutseconds", "-1")));
            } catch (NumberFormatException e) {
                return null;
            }


            config.setCostfunction(line.getOptionValue("costfunction", "dummy"));
            config.setMethod(line.getOptionValue("method", "DFS"));

            try {
                config.setNodePrintFactor(Double.parseDouble(line.getOptionValue("shownodes", "-1.0")));
            } catch (NumberFormatException e) {
                return null;
            }

            config.setHeuristic(line.getOptionValue("heuristic", "center"));

            return config;
        }
    }

    private static void printHelp(Options opts) {
        new HelpFormatter().printHelp("java -jar tp1-sia.jar", opts);
    }

    public static AppConfig getAppConfig(String[] args) throws ParseException, FileNotFoundException {
        AppConfig config;
        final CommandLineParser parser = new GnuParser();
        final Options opts = getInputOptions();
        final CommandLine line = parser.parse(opts, args);
        config = parseOptions(opts, line);
        if (config == null) {

            printHelp(opts);
        }
        return config;

    }
}
