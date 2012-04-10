public class VertexFactory {
    private Vertex[] nodes;

    public void init(int amt) {
        nodes = new Vertex[amt];
    }

    public Vertex getVertex(int n) {
        if (nodes[n] == null) nodes[n] = new Vertex(n);
        return nodes[n];
    }

    public Vertex[] getVertices() {
        return nodes;
    }
}
