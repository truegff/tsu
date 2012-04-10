package ge.tsu.optim.salesman;

import java.util.HashMap;

public class EdgeFactory {


    private static HashMap<String, Edge> edges = new HashMap<String, Edge>();


    public static HashMap<String, Edge> getEdges() {
        return edges;
    }

    public static void init(int N) {

    }
}
