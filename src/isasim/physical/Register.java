package isasim.physical;


import isasim.helper.BitSetHelper;

import java.util.BitSet;

public class Register {
    private BitSet memory ;

    private RegisterAddress address ;
    public Register(int size,int adress){
        memory = new BitSet(size) ;
        this.address = new RegisterAddress(adress) ;
    }

    public RegisterAddress getAddress() {
        return address;
    }


    public void save(int ToSave){
        this.memory = BitSet.valueOf(new long[]{ToSave}) ;
    }
    public int load(){
        return BitSetHelper.BitSetToInt(this.memory) ;
    }

    public BitSet getMemory(){
        return memory ;
    }



}
