import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入文件名称:");
        String fileName = scanner.nextLine();
        file.setFname(fileName);
        Graph graph = new Graph();
        graph.createGraph(file);
        graph.printGraph();
        //System.out.println(graph.nodes.get(1).data);
    }
}