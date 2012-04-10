package ge.tsu.optim.salesman;

import java.util.ArrayList;

public class Vertex {
    private int index;
    private ArrayList<Edge> neighbours = new ArrayList<Edge>();
    private boolean visited = false;

    public Vertex(int index) {
        this.index = index;
    }

    public void addNeighbour(Vertex destination, int distance) {
        Edge e = new Edge(this, destination, distance);
        EdgeFactory.getEdges().put(Integer.toString(index) + "." + Integer.toString(destination.getIndex()), e);
        getNeighbours().add(e);
    }

    public int getIndex() {
        return index;
    }

    public ArrayList<Edge> getNeighbours() {
        return neighbours;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

}
