package structures.tree;

import java.util.ArrayList;

public class NameNode {
    public String name;
    public ArrayList<String> positions;

    public NameNode left, right;
    public int height;

    public NameNode(String name, String pos) {
        this.name = name;
        this.positions = new ArrayList<>();
        this.positions.add(pos);
        this.height = 1;
    }

}
