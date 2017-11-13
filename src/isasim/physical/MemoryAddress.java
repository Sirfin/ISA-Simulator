package isasim.physical;

import isasim.helper.BitSetHelper;

import java.util.BitSet;

public class MemoryAddress {
    private Memory mem ;
    private int Address ;
    public MemoryAddress(int value , Memory mem){
        this.mem = mem ;
        this.Address = value ;
    }
    public int load(){
        return BitSetHelper.BitSetToInt(mem.memory.get(Address)) ;
    }
    public void store(int value){
        mem.memory.set(Address,BitSet.valueOf(new long[]{value})) ;
    }
}
