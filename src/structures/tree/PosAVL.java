package structures.tree;

public class PosAVL {
    private PosNode root;

    private int height(PosNode n) {
        return n == null ? 0 :n.height;
    }

    private int getBalance(PosNode n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    private PosNode rotateRight(PosNode y) {
        PosNode x = y.left;
        PosNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private PosNode rotateLeft(PosNode x) {
        PosNode y = x.right;
        PosNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    public void insert(String pos, String name) {
        root = insertRec(root, pos, name);
    }

   private PosNode insertRec(PosNode node, String pos, String name) {
        if (node == null)
            return new PosNode(pos, name);

        int cmp = pos.compareTo(node.pos);

        if (cmp < 0)
            node.left = insertRec(node.left, pos, name);
        else if (cmp > 0)
            node.right = insertRec(node.right, pos, name);
        else
            return node; // ซ้ำ ไม่ต้องเพิ่ม

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && pos.compareTo(node.left.pos) < 0)
            return rotateRight(node);

        if (balance < -1 && pos.compareTo(node.right.pos) > 0)
            return rotateLeft(node);

        if (balance > 1 && pos.compareTo(node.left.pos) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && pos.compareTo(node.right.pos) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    } 

    public boolean exists(String pos) {
        return search(root, pos) != null;
    }

    private PosNode search(PosNode node, String pos) {
        if (node == null) return null;

        int cmp = pos.compareTo(node.pos);

        if (cmp == 0) return node;
        else if (cmp < 0) return search(node.left, pos);
        else return search(node.right, pos);
    }

    public PosNode searchByPosition(String pos) {
        return search(root, pos);
    }

    public void delete(String pos) {
        root = deleteRec(root, pos);
    }

    private PosNode deleteRec(PosNode node, String pos) {
        if (node == null) return null;

        int cmp = pos.compareTo(node.pos);
        if (cmp < 0)
            node.left = deleteRec(node.left, pos);
        else if (cmp > 0)
            node.right = deleteRec(node.right, pos);
        else {
            if ((node.left == null) || (node.right == null)) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                PosNode temp = minValueNode(node.right);
                node.pos = temp.pos;
                node.name = temp.name;
                node.right = deleteRec(node.right, temp.pos);
            }
        }

        if (node == null) return null;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0) return rotateRight(node);
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) return rotateLeft(node);
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    private PosNode minValueNode(PosNode node) {
        PosNode current = node;
        while (current.left != null) current = current.left;
        return current;
    }
}
