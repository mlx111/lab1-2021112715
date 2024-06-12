import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ArcNodeTest {
    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:WhitespaceAround"})
    @Test
    void queryBridgeWords() throws IOException {
        MyFile file = new MyFile();
        file.setFname("1.txt");
        ArcNode graph = new ArcNode();
        ArrayList<String> fileData;
        fileData=file.GetNodedata();
        graph.creatGraph(fileData);
        String reslut = graph.queryBridgeWords("become", "initative");
        assertEquals("No becomeand initative in the graph!", reslut);
    }
}