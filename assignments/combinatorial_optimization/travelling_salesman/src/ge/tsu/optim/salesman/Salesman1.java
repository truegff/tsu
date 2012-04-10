package ge.tsu.optim.salesman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;

//input:
//
//3
//0 1 2
//6 0 3
//5 4 0
//
//output:
//
//1 2 3   Z = 9
//1 3 2   Z = 12
//
//ZOptimal: 9
//1 2 3

/**
 * @author Alexander Dolidze <alexander.dolidze@gmail.com>
 */
public class Salesman1 {

    public static PrintWriter printWriter = new PrintWriter(System.out);
    public static LinkedList<Vertex> permutation = new LinkedList<Vertex>();

    public static int startingVertex = 1;
    public static int N;
    public static int Z;

    public static long Zopt = 0;
    public static LinkedList<Vertex> optPath = new LinkedList<Vertex>();

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


        N = Integer.parseInt(bufferedReader.readLine());
        VertexFactory.init(N);

        String[] tokens;
        for (int i = 0; i < N; i++) {
            tokens = bufferedReader.readLine().split("[ ]+");
            for (int j = 0; j < N; j++) {
                if (i != j) {
                    Vertex destination = VertexFactory.getVertex(j);
                    int distance = Integer.parseInt(tokens[j]);
                    Zopt += distance;
                    VertexFactory.getVertex(i).addNeighbour(destination, distance);
                }

            }
        }

        Z = 0;
        findPermutations(VertexFactory.getVertex(startingVertex-1), 1);

        printWriter.println();
        printWriter.println("ZOptimal: " + Zopt);
        for (Vertex v : optPath) {
            printWriter.print((v.getIndex() + 1) + " ");
        }
        printWriter.println();

        printWriter.flush();

    }

    public static void findPermutations(Vertex current, int step) {
        permutation.addLast(current);
        current.setVisited(true);
        for (Edge edge : current.getNeighbours()) {
            if (!edge.getDestination().isVisited()) {
                Z += edge.getWeight();
                findPermutations(edge.getDestination(), step + 1);
                Z -= edge.getWeight();
            }
        }

        if (step == N) {
            String key = Integer.toString(current.getIndex()) + "." + Integer.toString(startingVertex-1);
            int weightToHome = EdgeFactory.getEdges().get(key).getWeight();
            Z += weightToHome;
            printPermutation();
            Z -= weightToHome;
        }
        current.setVisited(false);
        permutation.pollLast();
    }

    public static void printPermutation() {
        if (Z < Zopt) {
            Zopt = Z;
            optPath = new LinkedList<Vertex>();

            for (Vertex v : permutation) {
                printWriter.print((v.getIndex() + 1) + " ");
                optPath.addLast(v);
            }
        } else
            for (Vertex v : permutation) {
                printWriter.print((v.getIndex() + 1) + " ");
            }
        printWriter.println("  Z = " + Z);
    }
}
