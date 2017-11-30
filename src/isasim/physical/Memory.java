package isasim.physical;

import isasim.helper.BitSetHelper;

import java.util.ArrayList;
import java.util.BitSet;

public class Memory {
    ArrayList<Integer> memory  = new ArrayList<>() ;
    public Memory(int size){
        for (int i = 0 ; i < size ; i++ ){
            memory.add(0) ;
        }
    }
    public ArrayList<Integer> getMemory () {
        return memory ;
    }

    public int load(int Address){
        return memory.get(Address) ;
    }
    public void store(int Address,int value){
        if (memory.size() > Address) {
            memory.set(Address, value);
        }
    }
}
