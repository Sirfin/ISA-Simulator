package isasim.physical;

import isasim.helper.BitSetHelper;

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

    public BitSet load(int Address){
        return memory.get(Address) ;
    }
    public void store(int Address,int value){
        memory.set(Address,BitSet.valueOf(new long[]{value})) ;
    }
}
