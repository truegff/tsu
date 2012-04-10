package ge.tsu.optim.salesman;

public class VertexFactory {
    private static Vertex[] vertices;

    VertexFactory() {

    }

    public static void init(int N) {
        vertices = new Vertex[N];
    }

    public static Vertex getVertex(int n) {
        if (vertices[n] == null) vertices[n] = new Vertex(n);
        return vertices[n];
    }

    public static Vertex[] getVertices() {
        return vertices;
    }
}
