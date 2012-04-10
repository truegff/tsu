import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * SIMPLE GRAPH GENERATOR
 *
 * @author Alexander Dolidze <alexander.dolidze@gmail.com>
 */
public class GraphGenerator {

    private static final String inputFileName = "input.txt";
    private static final String outputFileName = "output.txt";
    private static Scanner scanner = null;

    private static int N = 0;
    private static int M = 0;
    private static int maxM = 0;

    private static long salt = new Date().getTime();
    private static long sugar = 0;
    private static long divModule = 0;

    private static ArrayList<Integer> unfilled = new ArrayList<Integer>();

    private static HashSet<String> hashSet = new HashSet<String>();

    public static void main(String[] args) {

        PrintWriter out = null;
        try {
            out = new PrintWriter(new File(outputFileName));
        } catch (FileNotFoundException e) {
            System.out.println("Can't open output file.");
            return;
        }

        try {
            scanner = new Scanner(new File(inputFileName));
        } catch (FileNotFoundException e) {
            out.write("Input file cannot be found.");
            out.flush();
            return;
        }

        N = scanner.nextInt();
        M = scanner.nextInt();
        scanner.close();

        if (N <= 0) {
            out.write("Why would someone need an empty graph? I wonder.");
            out.flush();
            return;
        }

        maxM = (N * (N - 1)) / 2;
        if (M > maxM) {
            out.write("Number of edges can't exceed " + maxM + " while number of vertices is " + N + ".");
            out.flush();
            return;
        }

        VertexFactory.init(N, M);

        sugar = new Date().getTime();
        int currentIndex = 0;
        int nextIndex = 0;

        while (Vertex.friendships > 0) {

            currentIndex = unfilled.get(new Double(Math.random() * salt % divModule).intValue());
/**
 * Comment previous and uncomment next line of code if you want to get connected graph with 100% chance :)
 * Helpful with relatively small M values.
 **/
//            if (unfilled.contains(new Integer(nextIndex)))
//                currentIndex = nextIndex;
//            else
//                currentIndex = unfilled.get(new Double(Math.random() * salt % divModule).intValue());

            nextIndex = unfilled.get(new Double(Math.random() * sugar % divModule).intValue());

            //Always start from Vertex[0].
            if (Vertex.friendships == M) currentIndex = 0;

            if (currentIndex != nextIndex)

                if (!hashSet.contains(Integer.toString(Math.min(currentIndex, nextIndex)) + "." + Integer.toString(Math.max(currentIndex, nextIndex)))) {
                    VertexFactory.getVertex(currentIndex).makeFriends(VertexFactory.getVertex(nextIndex));
                    hashSet.add(Integer.toString(Math.min(currentIndex, nextIndex)) + "." + Integer.toString(Math.max(currentIndex, nextIndex)));
                }

        }

        out.println(N);
        for (int i = 0; i < N; i++) {
            out.print(VertexFactory.getVertex(i).friends.size());
            for (int j = 0; j < VertexFactory.getVertex(i).friends.size(); j++) {
                out.print(" " + (VertexFactory.getVertex(i).friends.get(j).index + 1));
            }
            out.println();
        }

        out.flush();
        out.close();
    }


    static class Vertex {
        public LinkedList<Vertex> friends = new LinkedList<Vertex>();
        public int index;
        public static int friendships;

        public Vertex(int i) {
            this.index = i;
        }

        public void makeFriends(Vertex vertex) {
            friends.add(vertex);
            if (friends.size() == (N - 1)) {
                unfilled.remove(new Integer(index));
                divModule--;
            }
            vertex.friends.add(this);
            if (vertex.friends.size() == (N - 1)) {
                unfilled.remove(new Integer(vertex.index));
                divModule--;
            }

            friendships -= 1;

//            System.out.println(friendships + " " + divModule);
        }
    }

    static class VertexFactory {
        public static Vertex vertices[];

        public static void init(int amt, int friendships) {
            vertices = new Vertex[amt];
            Vertex.friendships = friendships;

            if (friendships > 0) {
                for (int i = 0; i < amt; i++)
                    unfilled.add(i);
                divModule = unfilled.size();
            }
        }

        public static Vertex getVertex(int i) {
            if (vertices[i] == null) vertices[i] = new Vertex(i);
            return vertices[i];
        }
    }

}

