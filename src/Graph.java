import java.io.IOException;
import java.util.ArrayList;

public class Graph {
    ArrayList<Node> nodes;

    public Graph() {
        this.nodes = new ArrayList<>();
    }

    public Graph(ArrayList<Node> nodes) {
        this.nodes = new ArrayList<>();
    }

    /**
     * ªÒ»°
     * @return graph
     */
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    /**
     * …Ë÷√
     * @param nodes
     */
    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }
    public void createGraph(File file) throws IOException {

        for (int i = 0; i < file.GetNodedata().size(); i++) {
            Node node = new Node(file.GetNodedata().get(i));

            nodes.add(node);
        }
        for (int i = 0; i < nodes.size()-1; i++) {

            nodes.get(i).neighbors.add(nodes.get(i+1));
        }
        for(int i = 0; i < nodes.size(); i++){
            for(int j = 0; j < i; j++){
                if(nodes.get(i).data.contains(nodes.get(j).data)){
                    for (int k = 0; k< nodes.get(i).neighbors.size(); k++) {
                        nodes.get(j).neighbors.add(nodes.get(i).neighbors.get(k));
                    }
                    nodes.remove(i);
                }
            }
        }
    }
    public void printGraph() {
        for (int i = 0; i < nodes.size(); i++) {
            System.out.print(nodes.get(i).data+" ");
            nodes.get(i).PrintNeighbour();
            System.out.println();
        }
    }
}
