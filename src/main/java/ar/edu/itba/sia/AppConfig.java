package ar.edu.itba.sia;

import ar.edu.itba.sia.domain.costFunctions.ColorBasedCostFunction;
import ar.edu.itba.sia.domain.costFunctions.DummyCostFunction;
import ar.edu.itba.sia.domain.costFunctions.RotationCostFunction;
import ar.edu.itba.sia.domain.heuristics.CenterHeuristic;
import ar.edu.itba.sia.domain.heuristics.ColorHeuristic;
import ar.edu.itba.sia.domain.heuristics.OrderHeuristic;
import ar.edu.itba.sia.gps.api.CostFunction;
import ar.edu.itba.sia.gps.api.GPSProblem;
import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.impl.*;
import com.google.common.collect.Sets;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class AppConfig {
    private String filePath;
    private int boardSize;
    private double nodePrintFactor;
    Set<Heuristic> heuristics = Sets.newHashSet();
    private Class<? extends GPSEngine> method;
    private int cachedepth;
    private CostFunction costfunction;
    private int timeoutSeconds;


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setNodePrintFactor(double nodePrintFactor) {
        this.nodePrintFactor = nodePrintFactor;
    }

    public void setHeuristic(String heuristic) {
        if (heuristic.contains("center")) {
            heuristics.add(new CenterHeuristic());
        }
        if (heuristic.contains("order")) {
            heuristics.add(new OrderHeuristic());
        }
        if (heuristic.contains("color")) {
            heuristics.add(new ColorHeuristic());
        }
    }

    public void setMethod(String method) {
        if (method.equals("DFS")) {
            this.method = DFSEngine.class;
        } else if (method.equals("BFS")) {
            this.method = BFSEngine.class;
        } else if (method.equals("ID")) {
            this.method = IDEngine.class;
        } else if (method.equals("AStar")) {
            this.method = AStarEngine.class;
        } else if (method.equals("Greedy")) {
            this.method = GreedyEngine.class;
        }     else {
            throw new RuntimeException("Invalid method passed!");
        }

    }

    public void setCachDepth(int cachedepth) {
        this.cachedepth = cachedepth;
    }

    public void setCostFunction(String costfunction) {
        if (costfunction.equals("dummy")) {
            this.costfunction = new DummyCostFunction();
        } else if (costfunction.equals("colors")) {
            this.costfunction = new ColorBasedCostFunction();
        } else if (costfunction.equals("rotation")) {
            this.costfunction = new RotationCostFunction();
        } else {
            throw new RuntimeException("Invalid cost function passed!");
        }
    }

    public void setTimeoutSeconds(int timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
    }

    public GPSEngine getEngine(GPSProblem problem) {
        try {
            System.out.println(this.method);
            return this.method.getConstructor(GPSProblem.class).newInstance(problem);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CostFunction getCostFunction() {
        return costfunction;
    }

    public Set<Heuristic> getHeuristics() {
        return heuristics;
    }

    public double getNodePrintFactor() {
        return nodePrintFactor;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int cacheDepthSize() {
        return this.cachedepth;
    }

    public int getTimeoutSeconds() {
        return timeoutSeconds;
    }
}