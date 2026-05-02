package structures.graph;

public class Vertex implements Comparable<Vertex>{
    private String name;
    private Vertex next;
    private Edge edgeList;
    private int inDegree;
    private int outDegree;
    private int sumWeight;
    private boolean visited;
    private double distance;
    private Vertex prev;
    private boolean statement;
    public Vertex(String name) {
        this.name = name;
        this.next = null;
        this.edgeList = null;
        this.sumWeight = 0;
        this.inDegree = 0;
        this.outDegree = 0;
        this.visited = false;
        this.distance = Double.MAX_VALUE;
        this.prev = null;

        this.statement = false;
    }

    //getters
    public String getName() {
        return name;
    }

    public Vertex getNext() {
        return next;
    }

    public Edge getEdgeList() {
        return edgeList;
    }

    public int getInDegree() {
        return inDegree;
    }

    public int getOutDegree() {
        return outDegree;
    }

    public int getSumWeight(){
        return sumWeight;
    }

    public boolean isVisited() {
        return visited;
    }

    public double getDistance(){
        return distance;
    }
    
    public Vertex getPrev() {
        return prev;
    }

    public boolean isTrue(){
        return statement;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setNext(Vertex next) {
        this.next = next;
    }

    public void setEdgeList(Edge edgeList) {
        this.edgeList = edgeList;
    }

    public void setInDegree(int inDegree) {
        this.inDegree = inDegree;
    }

    public void setOutDegree(int outDegree) {
        this.outDegree = outDegree;
    }
    
    public void setSumWeight(int w) {
        this.sumWeight = w;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setDistance(double distance){
        this.distance = distance;
    }

    public void setPrev(Vertex prev) {
        this.prev = prev;
    }  

    public void setStatement(boolean statement){
        this.statement = statement;
    }

    @Override
    public int compareTo(Vertex other) {
        return this.name.compareTo(other.name);
    }

}
