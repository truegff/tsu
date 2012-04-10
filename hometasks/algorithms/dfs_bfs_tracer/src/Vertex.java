import java.util.ArrayList;

public class Vertex {
    private int index;
    private ArrayList<Vertex> neighbors;
    private boolean discovered = false;
    private boolean finished = false;

    public Vertex(int index) {
        this.index = index;
        this.neighbors = new ArrayList<Vertex>();
    }

    public ArrayList<Vertex> getNeighbors() {
        return neighbors;
    }

    public int getIndex() {
        return index;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
//        System.out.println("setDiscovered(" + (index + 1) + ", " + discovered + ");");
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
//        System.out.println("setFinished(" + (index + 1) + ", " + finished + ");");
    }
}
