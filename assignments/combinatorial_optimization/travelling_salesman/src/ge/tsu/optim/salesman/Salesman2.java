package ge.tsu.optim.salesman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

//input:
//
//3
//0 1 2
//6 0 3
//5 4 0
//1 2 3
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
public class Salesman2 {

    public static PrintWriter printWriter = new PrintWriter(System.out);
    public static LinkedList<Vertex> permutation = new LinkedList<Vertex>();

    public static int startingVertex = 1;
    public static int nVertices;
    public static int nArea = 2;
    public static int Z;

    public static long Zopt = 0;

    public static LinkedList<Vertex> givenOptPath = new LinkedList<Vertex>();
    public static Vertex[] givenOptPathArr;

    public static LinkedList<Vertex> optPath = new LinkedList<Vertex>();

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


        nVertices = Integer.parseInt(bufferedReader.readLine());
        VertexFactory.init(nVertices);

        String[] tokens;
        for (int i = 0; i < nVertices; i++) {
            tokens = bufferedReader.readLine().split("[ ]+");
            for (int j = 0; j < nVertices; j++) {
                if (i != j) {
                    Vertex destination = VertexFactory.getVertex(j);
                    int distance = Integer.parseInt(tokens[j]);
                    Zopt += distance;
                    VertexFactory.getVertex(i).addNeighbour(destination, distance);
                }

            }
        }

        tokens = bufferedReader.readLine().split("[ ]+");
        for (int i = 0; i < nVertices; i++) {
            if (i == 0) startingVertex = Integer.parseInt(tokens[i]);
            givenOptPath.addLast(VertexFactory.getVertex(Integer.parseInt(tokens[i]) - 1));
        }
        givenOptPathArr = new Vertex[givenOptPath.size()];
        givenOptPathArr = givenOptPath.toArray(givenOptPathArr);

        printWriter.println("მოცემული გზა: ");
        printPermutation(givenOptPath);
        printWriter.println();

        findAlternatives();

        printWriter.println();
        printWriter.println("ნაპოვნი ოპტიმალური გზა (Nx="+nArea+"): ");
        printPermutation(optPath);

        printWriter.flush();
    }


    public static void findAlternatives() {
        Integer[] index = new Integer[givenOptPathArr.length];
        for (int i = 1; i < givenOptPathArr.length-1; i++) {
            for (int k = i+1; k < givenOptPathArr.length; k++) {

                for (int j = 0; j < index.length; j++) index[j] = new Integer(j);

                Arrays.sort(index, i, k+1, Collections.reverseOrder());

                permutation = new LinkedList<Vertex>();
                for (int j = 0; j < index.length; j++) permutation.addLast(givenOptPathArr[index[j]]);
                printPermutation(permutation);
            }
        }
    }

//    public static void findPermutations(Vertex current, int step) {
//        permutation.addLast(current);
//        current.setVisited(true);
//        for (Edge edge : current.getNeighbours()) {
//            if (!edge.getDestination().isVisited()) {
//                Z += edge.getWeight();
//                findPermutations(edge.getDestination(), step + 1);
//                Z -= edge.getWeight();
//            }
//        }
//
//        if (step == nVertices) {
//            String key = Integer.toString(current.getIndex()) + "." + Integer.toString(startingVertex - 1);
//            int weightToHome = EdgeFactory.getEdges().get(key).getWeight();
//            Z += weightToHome;
//            printPermutation(permutation);
//            Z -= weightToHome;
//        }
//        current.setVisited(false);
//        permutation.pollLast();
//    }

    public static void printPermutation(LinkedList<Vertex> path) {

        Vertex[] vertices = new Vertex[path.size()];
        vertices = path.toArray(vertices);

        Z = 0;
        for (int i = 0; i < nVertices; i++) {
            if (i < nVertices - 1) {
                String key = (vertices[i].getIndex()) + "." + (vertices[i + 1].getIndex());
                Z += EdgeFactory.getEdges().get(key).getWeight();
            }
            if (i == nVertices - 1) {
                String key = (vertices[i].getIndex()) + "." + (vertices[startingVertex-1].getIndex());
                Z += EdgeFactory.getEdges().get(key).getWeight();
            }
        }

        if (Z < Zopt) {
            Zopt = Z;
            optPath = new LinkedList<Vertex>();

            for (Vertex v : path) {
                printWriter.print((v.getIndex() + 1) + " ");
                optPath.addLast(v);
            }
        } else
            for (Vertex v : path) {
                printWriter.print((v.getIndex() + 1) + " ");
            }
        printWriter.println("  Z = " + Z);
    }
}
