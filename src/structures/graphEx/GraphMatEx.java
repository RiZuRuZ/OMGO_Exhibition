package structures.graphEx;

public class GraphMatEx implements GraphEx {
    private int[][] matrix;
    private int numEdge;

    public GraphMatEx() {} //default constructor
    
    public GraphMatEx(int n) { //overload constructor
        init(n);
    }

    @Override
    public void init(int n) {
        matrix = new int[n][n];
        numEdge = 0;
        //throw new UnsupportedOperationException("Unimplemented method 'init'");
    }

    @Override
    public int getNumEdge() {
        return numEdge;
        //throw new UnsupportedOperationException("Unimplemented method 'getNumEdge'");
    }

    @Override
    public void setEdge(int i, int j, int w) {
        // Check w!=0
        assert w != 0 : "Cannot set weight to 0";
        if(matrix[i][j] == 0) {
            numEdge++;
        }
        matrix[i][j] = w;
        //throw new UnsupportedOperationException("Unimplemented method 'setEdge'");
    }

    @Override
    public boolean isEdge(int i, int j) {
        return matrix[i][j] != 0;
        //throw new UnsupportedOperationException("Unimplemented method 'isEdge'");
    }

    @Override
    public int getWeight(int i, int j) { //Override method of Graph interface
        return matrix[i][j];
        //throw new UnsupportedOperationException("Unimplemented method 'getWeight'");
    }

    public String toString(){ //Override method of Object class
        StringBuilder out = new StringBuilder();
        for(int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[i].length; j++) {
                out.append(getWeight(i, j) + " ");
            }
            out.append("\n");
        }        
        return out.toString();
    }
    
}
