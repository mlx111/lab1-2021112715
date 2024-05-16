import java.awt.List;
import java.io.*;
import java.util.*;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkImages;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.graphstream.stream.file.FileSinkImages.*;

class  GraphViz {
    private final static String osName = System.getProperty("os.name").replaceAll("\\s","");

    private final static String cfgProp = "D:\\IdeaProjects\\shiyan1\\config.properties";
    private final static Properties configFile = new Properties() {
        private final static long serialVersionUID = 1L; {
            try {
                load(new FileInputStream(cfgProp));
            } catch (Exception e) {}
        }
    };

    private static String TEMP_DIR = "D:\\IdeaProjects\\shiyan1\\src";

    private static String DOT = configFile.getProperty("dotFor" + osName);

    private int[] dpiSizes = {46, 51, 57, 63, 70, 78, 86, 96, 106, 116, 128, 141, 155, 170, 187, 206, 226, 249};

    private int currentDpiPos = 7;

    public void increaseDpi() {
        if ( this.currentDpiPos < (this.dpiSizes.length - 1) ) {
            ++this.currentDpiPos;
        }
    }

    public void decreaseDpi() {
        if (this.currentDpiPos > 0) {
            --this.currentDpiPos;
        }
    }

    public int getImageDpi() {
        return this.dpiSizes[this.currentDpiPos];
    }

    private StringBuilder graph = new StringBuilder();

    public GraphViz() {
    }


    public String getDotSource() {
        return this.graph.toString();
    }


    public void add(String line) {
        this.graph.append(line);
    }


    public void addln(String line) {
        this.graph.append(line + "\n");
    }


    public void addln() {
        this.graph.append('\n');
    }

    public void clearGraph(){
        this.graph = new StringBuilder();
    }


    public byte[] getGraph(String dot_source, String type)
    {
        File dot;
        byte[] img_stream = null;

        try {
            dot = writeDotSourceToFile(dot_source);
            if (dot != null)
            {
                img_stream = get_img_stream(dot, type);
                if (dot.delete() == false)
                    System.err.println("Warning: " + dot.getAbsolutePath() + " could not be deleted!");
                return img_stream;
            }
            return null;
        } catch (java.io.IOException ioe) { return null; }
    }

    public int writeGraphToFile(byte[] img, String file)
    {
        File to = new File(file);
        return writeGraphToFile(img, to);
    }

    public int writeGraphToFile(byte[] img, File to)
    {
        try {
            FileOutputStream fos = new FileOutputStream(to);
            fos.write(img);
            fos.close();
        } catch (java.io.IOException ioe) { return -1; }
        return 1;
    }

    private byte[] get_img_stream(File dot, String type)
    {
        File img;
        byte[] img_stream = null;

        try {
            img = File.createTempFile("graph_", "."+type, new File(TEMP_DIR));
            Runtime rt = Runtime.getRuntime();

            // patch by Mike Chenault
            String[] args = {DOT, "-T"+type, "-Gdpi="+dpiSizes[this.currentDpiPos], dot.getAbsolutePath(), "-o", img.getAbsolutePath()};
            Process p = rt.exec(args);

            p.waitFor();

            FileInputStream in = new FileInputStream(img.getAbsolutePath());
            img_stream = new byte[in.available()];
            in.read(img_stream);
            // Close it if we need to
            if( in != null ) in.close();

            if (img.delete() == false)
                System.err.println("Warning: " + img.getAbsolutePath() + " could not be deleted!");
        }
        catch (java.io.IOException ioe) {
            System.err.println("Error:    in I/O processing of tempfile in dir " + TEMP_DIR+"\n");
            System.err.println("       or in calling external command");
            ioe.printStackTrace();
        }
        catch (java.lang.InterruptedException ie) {
            System.err.println("Error: the execution of the external program was interrupted");
            ie.printStackTrace();
        }

        return img_stream;
    }

    private File writeDotSourceToFile(String str) throws java.io.IOException
    {
        File temp;
        try {
            temp = File.createTempFile("dorrr",".dot", new File(TEMP_DIR));
            FileWriter fout = new FileWriter(temp);
            fout.write(str);
            BufferedWriter br=new BufferedWriter(new FileWriter("dotsource.dot"));
            br.write(str);
            br.flush();
            br.close();
            fout.close();
        }
        catch (Exception e) {
            System.err.println("Error: I/O error while writing the dot source to temp file!");
            return null;
        }
        return temp;
    }

    public String start_graph() {
        return "digraph G {";
    }

    public String end_graph() {
        return "}";
    }

    public String start_subgraph(int clusterid) {
        return "subgraph cluster_" + clusterid + " {";
    }

    public String end_subgraph() {
        return "}";
    }

    public void readSource(String input)
    {
        StringBuilder sb = new StringBuilder();

        try
        {
            FileInputStream fis = new FileInputStream(input);
            DataInputStream dis = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            dis.close();
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        this.graph = sb;
    }

}
class MyFile {
    String fname;

    public MyFile() {
    }

    public MyFile(String fname) {
        this.fname = fname;
    }

    /**
     * 获取
     * @return fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * 设置
     * @param fname
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    public String GetFileData() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(this.fname);
        int ch;
        StringBuilder sb = new StringBuilder();
        while ((ch = fileInputStream.read())!=-1){
            char currentchar = (char)ch;
            if(currentchar == '\r'){
                char nextchar = (char)fileInputStream.read();
                if(nextchar == '\n'){
                    sb.append(" ");

                }
            } else if (!Character.isLetterOrDigit(currentchar)&&!Character.isWhitespace(currentchar)) {
                sb.append(" ");
            }
            else {
                sb.append(currentchar);
            }

        }
        fileInputStream.close();
        return sb.toString().replaceAll("\\s+", " ");

    }
    //获取用于构建图的节点元素
    public ArrayList<String> GetNodedata() throws IOException {
        ArrayList<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.GetFileData().length(); i++) {
            if(Character.isWhitespace(this.GetFileData().charAt(i))){
                result.add(sb.toString());
                sb = new StringBuilder();
            }else{
                sb.append(this.GetFileData().charAt(i));
            }
        }
        return result;
    }
}
//邻接表
class ArcNode {
    HashMap<String, HashMap<String,Integer>> graph;

    public ArcNode() {
        this.graph=new HashMap<>();
    }

    public ArcNode( HashMap<String, HashMap<String,Integer>> graph) {
        this.graph = graph;
    }

    /**
     * 获取
     *
     * @return graph
     */
    public  HashMap<String, HashMap<String,Integer>> getGraph() {
        return graph;
    }

    /**
     * 设置
     *
     * @param graph
     */
    public void setGraph( HashMap<String, HashMap<String,Integer>> graph) {
        this.graph = graph;
    }


    public void addVertex(String vertex) {
        graph.putIfAbsent(vertex, new HashMap<>());
    }

    public void addEdge(String from, String to) {
        graph.putIfAbsent(from, new HashMap<>());
        graph.putIfAbsent(to, new HashMap<>());
        int count=1;
        if(graph.get(from).containsKey(to)){
            count = graph.get(from).get(to)+1;
            graph.get(from).put(to,count);
        }
        graph.get(from).put(to,count);
    }

    // 获取顶点的邻接表
    public HashMap<String,Integer> getAdjVertices(String vertex) {
        return graph.getOrDefault(vertex, new HashMap<>());
    }

    // 删除一个顶点
    public void removeVertex(String vertex) {
        graph.values().forEach(e -> e.remove(String.valueOf(vertex)));
        graph.remove(vertex);
    }

    // 删除一条有向边
    public void removeEdge(String fromVertex, String toVertex) {
        HashMap<String,Integer> adjVertices = graph.get(fromVertex);
        if (adjVertices != null) {
            adjVertices.remove(String.valueOf(toVertex));
        }
    }

    // 打印图
    public void printGraph() {
        for (Map.Entry<String, HashMap<String, Integer>> stringHashMapEntry : graph.entrySet()) {

        }
        for (Map.Entry<String, HashMap<String,Integer>> entry : graph.entrySet()) {
            System.out.print("Vertex " + entry.getKey() + ":");
            entry.getValue().entrySet().forEach(entry1 -> System.out.print(" " + entry1.getKey() + " " + entry1.getValue()));
            System.out.println();
        }
    }
    public void creatGraph(MyFile file) throws IOException {
        //添加边
        for (int i = 0; i < file.GetNodedata().size()-1; i++) {
            addEdge(file.GetNodedata().get(i), file.GetNodedata().get(i+1));
        }
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        MyFile file = new MyFile();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入文件名称:");
        String fileName = scanner.nextLine();
        file.setFname(fileName);
        ArcNode graph = new ArcNode();
        graph.creatGraph(file);
        graph.printGraph();
        /*String dotFormat="1->2;1->3;1->4;4->5;4->6;6->7;5->7;3->8;3->6;8->7;2->8;2->5;";
        createDotGraph(dotFormat, "DotGraph");*/
    }
    public static void createDotGraph(String dotFormat,String fileName)
    {
        GraphViz gv=new GraphViz();
        gv.addln(gv.start_graph());
        gv.add(dotFormat);
        gv.addln(gv.end_graph());
        String type = "png";
        gv.decreaseDpi();
        gv.decreaseDpi();
        File out = new File(fileName+"."+ type);
        gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out );
    }

}