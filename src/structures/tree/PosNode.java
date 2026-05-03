package structures.tree;

public class PosNode {
    public String pos;
    public String name;

    public PosNode left, right;
    public int height;

    public PosNode(String pos, String name) {
        this.pos = pos;
        this.name = name;
        this.height = 1;
    }
}
