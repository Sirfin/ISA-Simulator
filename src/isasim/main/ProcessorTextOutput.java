package isasim.main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by ftoet on 17.01.2018.
 */
public class ProcessorTextOutput {
    public static void DumpProcessor(Processor processor){
        Path file = Paths.get("Dump.txt") ;
        List<String> lines = new ArrayList<>() ;
        lines.add("Register:") ;
        for (int i = 0 ; i < processor.Registerbank.size() ; i++){
            lines.add(i + ":" + processor.Registerbank.get(i).load()) ;
        }
        lines.add("RAM:") ;
        for (int i = 0 ; i < processor.ram.getMemory().size() ; i++){
            lines.add(i + ":" + processor.ram.load(i)) ;
        }
    }
}
