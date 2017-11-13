package isasim.physical;

import java.util.ArrayList;
import java.util.BitSet;

public class Memory {
    ArrayList<BitSet> memory  = new ArrayList<BitSet>() ;
    public Memory(int size){
        for (int i = 0 ; i < size ; i++ ){
            memory.add(new BitSet(32)) ;
        }
    }
    public ArrayList<BitSet> getMemory () {
        return memory ;
    }
}
