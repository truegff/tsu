import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * DFS & BFS Tracer
 *
 * @author Alexander Dolidze <alexander.dolidze@gmail.com>
 */
public class DfsBfsTracer {

    private static String inputFilename = "input.txt";
    private static String outputFilename = "output.txt";
    private static PrintWriter out = null;

    private static VertexFactory vertexFactory = new VertexFactory();
    private static LinkedList<Vertex> queue = null;

    private static int N = 0;

    public static void main(String[] args) {
        try {
            out = new PrintWriter(new File(outputFilename));
        } catch (FileNotFoundException e) {
            System.out.println("Can't open output file.");
            return;
        }
        try {
            readDataFromFile();
        } catch (FileNotFoundException e) {
            out.write("Input file cannot be found.");
            out.flush();
            return;
        } catch (CorruptInputFileException e) {
            out.write("Input file is corrupt.");
            out.flush();
            return;
        }

        out.write("BFS ordering:\n");
        initQueue();
        while (queue.size() != 0) printBFSTrace();
        out.write("\n");

        for (Vertex v : vertexFactory.getVertices()) {
            v.setDiscovered(false);
            v.setFinished(false);
        }

        out.write("DFS ordering:\n");
        initQueue();
        while (queue.size() != 0) printDFSTrace();

        out.flush();
        out.close();
    }

    private static void initQueue() {
        queue = new LinkedList<Vertex>();
        queue.add(vertexFactory.getVertex(0));
    }

    private static void printDFSTrace() {
        if (!queue.isEmpty()) {
            Vertex current = queue.pop();

            if (!current.isDiscovered()) {
                current.setDiscovered(true);
                out.write(Integer.toString(current.getIndex() + 1) + " ");

                int i = 0;
                for (Vertex n : current.getNeighbors()) {
                    if (!n.isDiscovered()) {
                        queue.add(i++, n);
                    }
                }

                current.setFinished(true);
            }
        }
    }

    private static void printBFSTrace() {
        if (!queue.isEmpty()) {
            Vertex current = queue.pop();

            if (!current.isFinished()) {
                if (!current.isDiscovered()) {
                    current.setDiscovered(true);
                    out.write(Integer.toString(current.getIndex() + 1) + " ");
                }

                for (Vertex n : current.getNeighbors()) {
                    if (!n.isDiscovered()) {
                        queue.add(n);
                        n.setDiscovered(true);
                        out.write(Integer.toString(n.getIndex() + 1) + " ");
                    }
                }
                current.setFinished(true);
            }
        }
    }

    private static void readDataFromFile() throws FileNotFoundException, CorruptInputFileException {
        Scanner scanner = new Scanner(new File(inputFilename));

        try {
            String firstLine = scanner.nextLine();

            N = new Integer(firstLine);

            vertexFactory.init(N);

            for (int x = 0; x < N; x++) {
                String line;

                line = scanner.nextLine();

                String[] tokens = line.split("[ ]+");

                Vertex currentVertex = vertexFactory.getVertex(x);

                if (tokens[0].equalsIgnoreCase("0")) {
                    //this vertex has no neighbours
                } else {
                    for (int y = 1; y <= new Integer(tokens[0]).intValue(); y++) {
                        int value = Integer.valueOf(tokens[y]).intValue();
                        currentVertex.getNeighbors().add(vertexFactory.getVertex(value - 1));
                    }
                }
            }
        } catch (Exception e) {
            throw new CorruptInputFileException();
        }
    }
//    private static void printNeighbours() {
//        System.out.println("Total vertices: " + vertexFactory.getVertices().length);
//        for (Vertex v : vertexFactory.getVertices()) {
//            System.out.print((v.getIndex() + 1) + ": ");
//            for (Vertex n : v.getNeighbors())
//                System.out.print((n.getIndex() + 1) + " ");
//            System.out.println();
//        }
//    }
//
//    private static void printVertexStates() {
//        System.out.println();
//        for (Vertex v : vertexFactory.getVertices()) {
//            System.out.print((v.getIndex() + 1) + ": ");
//            if (v.isDiscovered()) System.out.print("Discovered ");
//            else System.out.print("           ");
//            if (v.isFinished()) System.out.print("Finished ");
//            else System.out.print("           ");
//            System.out.println();
//        }
//        System.out.println();
//    }
//
//    private static void printQueue() {
//        System.out.print("\nQueue: ");
//        ListIterator<Vertex> iterator = queue.listIterator();
//        while (iterator.hasNext()) {
//            System.out.print((iterator.next().getIndex() + 1) + " ");
//        }
//        System.out.println("\n");
//    }

    private static class CorruptInputFileException extends Throwable {
    }
}
