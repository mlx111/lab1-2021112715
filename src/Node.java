import java.util.ArrayList;

public class Node {
    String data;
    ArrayList<Node> neighbors;


    public Node() {
    }

    public Node(String data) {
        this.data = data;
        this.neighbors = new ArrayList<>();
    }

    /**
     * 获取
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * 设置
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * 获取
     * @return neighbors
     */
    public ArrayList<Node> getNeighbors() {
        return neighbors;
    }

    /**
     * 设置
     * @param neighbors
     */
    public void setNeighbors(ArrayList<Node> neighbors) {
        this.neighbors = neighbors;
    }

    public String toString() {
        return "Node{data = " + data + ", neighbors = " + neighbors + "}";
    }
    public void PrintNeighbour(){
        if(!neighbors.isEmpty()){
            for (int i = 0; i < neighbors.size(); i++) {
                System.out.print(neighbors.get(i).data+" ");
            }
        }
    }
}
