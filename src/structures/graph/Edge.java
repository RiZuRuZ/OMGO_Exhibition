package structures.graph;

public class Edge {
    private Vertex source;
    private Vertex destination;
    private int weight;
    private double f_weight;
    private Edge next;

    public Edge(Vertex source, Vertex destination, int weight, double f_weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.next = null;
        this.f_weight = f_weight;
    }

    //getters
    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public Edge getNext() {
        return next;
    }

    public double getF_weight() {
        return f_weight;
    }

    //setters
    public void setSource(Vertex source) {
        this.source = source;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setNext(Edge next) {
        this.next = next;
    }

    public void setF_weight(double f_weight) {
        this.f_weight = f_weight;
    }
}
