package structures.tree;

public class NameAVL {
    private NameNode root;

    private int height(NameNode n) {
        return n == null ? 0 : n.height;
    }

    private int getBalance(NameNode n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    private NameNode rotateRight(NameNode y) {
        NameNode x = y.left;
        NameNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    private NameNode rotateLeft(NameNode x) {
        NameNode y = x.right;
        NameNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

    public void insert(String name, String pos) {
        root = insertRec(root, name, pos);
    }

    private NameNode insertRec(NameNode node, String name, String pos) {
        if (node == null)
            return new NameNode(name, pos);

        int cmp = name.compareTo(node.name);

        if (cmp < 0)
            node.left = insertRec(node.left, name, pos);
        else if (cmp > 0)
            node.right = insertRec(node.right, name, pos);
        else {
            // name ซ้ำ → เพิ่ม pos
            node.positions.add(pos);
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // rotations
        if (balance > 1 && name.compareTo(node.left.name) < 0)
            return rotateRight(node);

        if (balance < -1 && name.compareTo(node.right.name) > 0)
            return rotateLeft(node);

        if (balance > 1 && name.compareTo(node.left.name) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && name.compareTo(node.right.name) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // print amd search methods
    public void printTree() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(NameNode node) {
        if (node != null) {
            printInOrder(node.left);
            System.out.println(node.name + " -> " + node.positions);
            printInOrder(node.right);
        }
    }

    public NameNode search(String name) {
        return searchRec(root, name);
    }

    private NameNode searchRec(NameNode node, String name) {
        if (node == null) return null;

        int cmp = name.compareTo(node.name);

        if (cmp == 0) return node;
        else if (cmp < 0) return searchRec(node.left, name);
        else return searchRec(node.right, name);
    }
}
