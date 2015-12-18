package dameo.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wim
 */
public class Output {
    
    public static final List<Integer> nodesExpanded = new ArrayList<>();
    
    public static long sysTime = System.currentTimeMillis();
    
    public static void writeNodesExpanded() throws IOException {
        File nodeFile = new File(String.format("node%d.txt", sysTime));
        FileWriter writer = new FileWriter(nodeFile.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(writer);
        for (Integer i : nodesExpanded) {
            bw.write(String.format("%d\n", i));
        }
        bw.close();
        
    }
    
}
