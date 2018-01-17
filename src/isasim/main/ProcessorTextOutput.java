package isasim.main;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by ftoet on 17.01.2018.
 */
public class ProcessorTextOutput {
    public static void DumpProcessor(Processor processor){
        Path file = Paths.get("Dump.txt") ;
        System.out.println("Dumping File");
        List<String> lines = new ArrayList<>() ;
        lines.add("Register:") ;
        for (int i = 0 ; i < processor.Registerbank.size() ; i++){
            lines.add(i + ":" + processor.Registerbank.get(i).load()) ;
        }
        lines.add("RAM:") ;
        for (int i = 0 ; i < processor.ram.getMemory().size() ; i++){
            lines.add(i + ":" + processor.ram.load(i)) ;
        }
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
