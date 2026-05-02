package structures.graphEx;

public interface GraphEx {
    
    public void init(int n);

    public int getNumEdge();

    public void setEdge(int i, int j, int w);

    public boolean isEdge(int i, int j);

    public int getWeight(int i, int j);

}
